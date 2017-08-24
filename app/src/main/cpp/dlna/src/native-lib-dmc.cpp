#include <jni.h>
#include <string>
#include <android/log.h>
#include "native-lib-dmc.h"
#include "com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative.h"
#define J4A_LOG_TAG "J4A"
#define J4A_ALOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE,    J4A_LOG_TAG, __VA_ARGS__)
#define J4A_ALOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,      J4A_LOG_TAG, __VA_ARGS__)
#define J4A_ALOGI(...)  __android_log_print(ANDROID_LOG_INFO,       J4A_LOG_TAG, __VA_ARGS__)
#define J4A_ALOGW(...)  __android_log_print(ANDROID_LOG_WARN,       J4A_LOG_TAG, __VA_ARGS__)
#define J4A_ALOGE(...)  __android_log_print(ANDROID_LOG_ERROR,      J4A_LOG_TAG, __VA_ARGS__)

static mUpnpControlPoint* ctrlPoint;

/////////////////////////////////////////////////////////////////////////////////
// Notify Listener
/////////////////////////////////////////////////////////////////////////////////

void SSDPNotifyListner(mUpnpSSDPPacket* ssdpPkt)
{
    if (mupnp_ssdp_packet_isdiscover(ssdpPkt) == true) {
        printf("ssdp:discover : ST = %s\n",
               mupnp_ssdp_packet_getst(ssdpPkt));
    }
    else if (mupnp_ssdp_packet_isalive(ssdpPkt) == true) {
        printf("ssdp:alive : uuid = %s, NT = %s, location = %s\n",
               mupnp_ssdp_packet_getusn(ssdpPkt),
               mupnp_ssdp_packet_getnt(ssdpPkt),
               mupnp_ssdp_packet_getlocation(ssdpPkt));
    }
    else if (mupnp_ssdp_packet_isbyebye(ssdpPkt) == true) {
        printf("ssdp:byebye : uuid = %s, NT = %s\n",
               mupnp_ssdp_packet_getusn(ssdpPkt),
               mupnp_ssdp_packet_getnt(ssdpPkt));
    }
    mupnp_ssdp_packet_print(ssdpPkt);
}

/////////////////////////////////////////////////////////////////////////////////
// Event
/////////////////////////////////////////////////////////////////////////////////

void EventListener(mUpnpProperty* prop)
{
    J4A_ALOGD("Property Changed (%s) = %s\n",
           mupnp_property_getname(prop),
           mupnp_property_getvalue(prop));
}


mUpnpDevice* SelectDevice(mUpnpControlPoint* ctrlPoint)
{
    mUpnpDevice* dev;
    int n;
    char key;
    int devNum;

    n = 0;
    dev = mupnp_controlpoint_getdevices(ctrlPoint);


    while(dev != NULL){

        key = 'a' + n;
        if ('z' < key)
            break;
        J4A_ALOGD(" [%c] = %s\n dev=%#x", key, mupnp_device_getfriendlyname(dev),dev);
      //  sprintf(" [%c] = %s\n", key, mupnp_device_getfriendlyname(dev));

        n++;
        dev = mupnp_device_next(dev);
        J4A_ALOGD(" [%c] dev=%#x", key, dev);
    }

    return NULL;
//    for (; dev != NULL; dev = mupnp_device_next(dev)) {
//        key = 'a' + n;
//        if ('z' < key)
//            break;
//        J4A_ALOGD(" [%c] = %s\n dev=%#x", key, mupnp_device_getfriendlyname(dev),dev);
//        n++;
//    }
}

/*
 * Class:     com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative
 * Method:    native_mupnp_controlpoint_new
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative_native_1mupnp_1controlpoint_1new
        (JNIEnv *env, jobject obj)
{

}

/*
 * Class:     com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative
 * Method:    native_setup
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative_native_1setup
        (JNIEnv *env, jobject obj)
{

    ctrlPoint = mupnp_controlpoint_new();
    mupnp_controlpoint_setssdplistener(ctrlPoint, SSDPNotifyListner);
    mupnp_controlpoint_seteventlistener(ctrlPoint, EventListener);
    if (mupnp_controlpoint_start(ctrlPoint) == false) {
        J4A_ALOGD("Couldn't start this control point !!");
        exit(1);
    }
}

/*
 * Class:     com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative
 * Method:    native_mupnp_controlpoint_start
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative_native_1mupnp_1controlpoint_1start
        (JNIEnv *env, jobject obj)
{

}

/*
 * Class:     com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative
 * Method:    native_mupnp_controlpoint_search
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ytx_cyberlink2android_dlna_dmc_AVMediaControllerNative_native_1mupnp_1controlpoint_1search
        (JNIEnv *env, jobject obj)
{

    mupnp_controlpoint_search(ctrlPoint, MUPNP_ST_ROOT_DEVICE);
    mupnp_sleep(mupnp_controlpoint_getssdpsearchmx(ctrlPoint) * 1000);
    SelectDevice(ctrlPoint);
}