#include <jni.h>
#include <string>
#include <android/log.h>
#include "examples.h"


#define J4A_LOG_TAG "J4A"
#define J4A_ALOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE,    J4A_LOG_TAG, __VA_ARGS__)
#define J4A_ALOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,      J4A_LOG_TAG, __VA_ARGS__)
#define J4A_ALOGI(...)  __android_log_print(ANDROID_LOG_INFO,       J4A_LOG_TAG, __VA_ARGS__)
#define J4A_ALOGW(...)  __android_log_print(ANDROID_LOG_WARN,       J4A_LOG_TAG, __VA_ARGS__)
#define J4A_ALOGE(...)  __android_log_print(ANDROID_LOG_ERROR,      J4A_LOG_TAG, __VA_ARGS__)
/////////////////////////////////////////////////////////////////////////////////
// PrintContentDirectory
/////////////////////////////////////////////////////////////////////////////////

void PrintContentDirectory(mUpnpAction *browseAction, int indent, char *objectId)
{
    int n;
    char indentStr[128];
    char *resultXml;
    mUpnpXmlParser *xmlParser;
    mUpnpXmlNodeList *rootNode;
    mUpnpXmlNode *didlNode;
    mUpnpXmlNode *cnode;
    char *id;
    char *title;
    char *url;

    for (n=0; n<indent && n<(sizeof(indentStr)-1); n++)
        indentStr[n] = ' ';
    indentStr[n] = '\0';

    mupnp_action_setargumentvaluebyname(browseAction, "ObjectID", objectId);
    mupnp_action_setargumentvaluebyname(browseAction, "BrowseFlag", "BrowseDirectChildren");
    mupnp_action_setargumentvaluebyname(browseAction, "Filter", "*");
    mupnp_action_setargumentvaluebyname(browseAction, "StartingIndex", "0");
    mupnp_action_setargumentvaluebyname(browseAction, "RequestedCount", "0");
    mupnp_action_setargumentvaluebyname(browseAction, "SortCriteria", "");

    if (!mupnp_action_post(browseAction))
        return;

    resultXml = mupnp_action_getargumentvaluebyname(browseAction, "Result");
    if (mupnp_strlen(resultXml) <= 0)
        return;
    J4A_ALOGD("resultXml=%s",resultXml);
    rootNode = mupnp_xml_nodelist_new();
    xmlParser = mupnp_xml_parser_new();
    if (mupnp_xml_parse(xmlParser, rootNode, resultXml, mupnp_strlen(resultXml))) {
        didlNode = mupnp_xml_nodelist_getbyname(rootNode, "DIDL-Lite");
        if (didlNode) {
            for (cnode=mupnp_xml_node_getchildnodes(didlNode); cnode; cnode=mupnp_xml_node_next(cnode)) {
                id = (char *) mupnp_xml_node_getattributevalue(cnode, "id");
                title = (char *) mupnp_xml_node_getchildnodevalue(cnode, "dc:title");
                if (mupnp_xml_node_isname(cnode, "container")) {
                    J4A_ALOGD(" %s[%s]%s\n",
                           indentStr,
                           id,
                           ((0 < mupnp_strlen(title)) ? title : ""));
                    PrintContentDirectory(browseAction, (indent+1), id);
                }
                else {
                    url = (char *) mupnp_xml_node_getchildnodevalue(cnode, "res");
                    J4A_ALOGD(" %s[%s]%s (%s)\n",
                           indentStr,
                           id,
                           ((0 < mupnp_strlen(title)) ? title : ""),
                           ((0 < mupnp_strlen(url)) ? url: ""));
                }
            }
        }
    }
    mupnp_xml_nodelist_delete(rootNode);
    mupnp_xml_parser_delete(xmlParser);
}

/////////////////////////////////////////////////////////////////////////////////
// PrintDMSInfo
/////////////////////////////////////////////////////////////////////////////////

void PrintDMSInfo(mUpnpDevice *dev, int dmsNum)
{
    mUpnpService *conDirService;
    mUpnpAction *browseAction;
    mUpnpStateVariable *searchCap;
    mUpnpStateVariable *sorpCap;

    if (!mupnp_device_isdevicetype(dev, UPNPAVDUMP_DMS_DEVICETYPE))
        return;

    J4A_ALOGD("[%d] : %s\n", dmsNum, mupnp_device_getfriendlyname(dev));

    conDirService = mupnp_device_getservicebytype(dev, UPNPAVDUMP_DMS_CONTENTDIR_SERVICETYPE);
    if (!conDirService)
        return;

    searchCap = mupnp_service_getstatevariablebyname(conDirService, "SearchCapabilities");
    if (searchCap) {
        if (mupnp_statevariable_post(searchCap))
            J4A_ALOGD(" SearchCapabilities = %s\n", mupnp_statevariable_getvalue(searchCap));
    }

    sorpCap = mupnp_service_getstatevariablebyname(conDirService, "SortCapabilities");
    if (sorpCap) {
        if (mupnp_statevariable_post(sorpCap))
            J4A_ALOGD(" SortCapabilities = %s\n", mupnp_statevariable_getvalue(sorpCap));
    }

    browseAction = mupnp_service_getactionbyname(conDirService, UPNPAVDUMP_DMS_BROWSE_ACTIONNAME);
    if (!browseAction)
        return;

    PrintContentDirectory(browseAction, 0, "0");
}

/////////////////////////////////////////////////////////////////////////////////
// PrintDMSInfos
/////////////////////////////////////////////////////////////////////////////////

void PrintDMSInfos(mUpnpControlPoint *ctrlPoint)
{
    mUpnpDevice *dev;
    int dmsNum;

    dmsNum = 0;
    for (dev = mupnp_controlpoint_getdevices(ctrlPoint); dev != NULL; dev = mupnp_device_next(dev)) {
        if (mupnp_device_isdevicetype(dev, UPNPAVDUMP_DMS_DEVICETYPE))
            PrintDMSInfo(dev, ++dmsNum);
    }

    if (dmsNum <= 0)
        J4A_ALOGD("Media Server is not found !!\n");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_ytx_cyberlink2android_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    bool ret;

    mUpnpControlPoint *ctrlPoint;

    ctrlPoint = mupnp_controlpoint_new();
    ret = mupnp_controlpoint_start(ctrlPoint);
    if (ret == false) {
        J4A_ALOGD("Couldn't start this control point !!");
        exit(1);
    }

    mupnp_controlpoint_search(ctrlPoint, MUPNP_ST_ROOT_DEVICE);

    mupnp_sleep(mupnp_controlpoint_getssdpsearchmx(ctrlPoint) * 1000);

    PrintDMSInfos(ctrlPoint);

    mupnp_controlpoint_stop(ctrlPoint);
    mupnp_controlpoint_delete(ctrlPoint);


    J4A_ALOGD("test end");
    return env->NewStringUTF(hello.c_str());
}
