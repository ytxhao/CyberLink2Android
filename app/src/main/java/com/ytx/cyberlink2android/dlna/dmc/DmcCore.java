package com.ytx.cyberlink2android.dlna.dmc;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DmcCore extends Thread{

    public static final int mupnp_controlpoint_new_id = 1;
    public static final int mupnp_controlpoint_setup = 2;
    public static final int mupnp_controlpoint_search = 3;

    private static DmcCore instance = new DmcCore();

    private DmcCore (){}

    public static DmcCore getInstance() {
        return instance;
    }

    private Looper looper;
    public Handler mDmcCoreHandler;
    private AVMediaControllerNative mAVMediaControllerNative = new AVMediaControllerNative();
    @Override
    public void run() {
        super.run();
        Looper.prepare();
        looper = Looper.myLooper();
        mDmcCoreHandler = new Handler(this.looper){
            @Override
            public void handleMessage(Message msg) {
                handleMessageDmcCore(msg);
            }
        };
        Looper.loop();
    }

    private void handleMessageDmcCore(Message msg){

        switch (msg.what){
            case mupnp_controlpoint_new_id:
                mAVMediaControllerNative.native_mupnp_controlpoint_new();
                break;

            case mupnp_controlpoint_setup:
                mAVMediaControllerNative.native_setup();
                break;
            case mupnp_controlpoint_search:
                mAVMediaControllerNative.native_mupnp_controlpoint_search();
                break;
        }
    }
}
