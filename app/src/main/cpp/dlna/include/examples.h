//
// Created by Administrator on 2017/8/14.
//

#ifndef CYBERLINK2ANDROID_EXAMPLES_H
#define CYBERLINK2ANDROID_EXAMPLES_H
#ifdef __cplusplus
extern "C" {
#endif
#include <mupnp/upnp.h>
#define UPNPAVDUMP_DMS_DEVICETYPE "urn:schemas-upnp-org:device:MediaServer:1"
#define UPNPAVDUMP_DMS_CONTENTDIR_SERVICETYPE "urn:schemas-upnp-org:service:ContentDirectory:1"
#define UPNPAVDUMP_DMS_BROWSE_ACTIONNAME "Browse"


void PrintContentDirectory(mUpnpAction *browseAction, int indent, char *objectId);

void PrintDMSInfo(mUpnpDevice *dev, int dmsNum);


void PrintDMSInfos(mUpnpControlPoint *ctrlPoint);

#ifdef __cplusplus
}
#endif
#endif //CYBERLINK2ANDROID_EXAMPLES_H
