package com.ytx.cyberlink2android.dlna.dmc;

/**
 * Created by Administrator on 2017/8/21.
 */

public class AVMediaControllerNative {
    private int mNativeContext;
    public native void native_mupnp_controlpoint_new();
    public native void native_setup();
    public native void native_mupnp_controlpoint_start();

    public native void native_mupnp_controlpoint_search();
}
