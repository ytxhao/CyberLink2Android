package com.ytx.cyberlink2android.dlna.dmc;

/**
 * Created by Administrator on 2017/8/18.
 */

public class AVMediaController {

   public void mupnpControlPointNew(){
        DmcCore.getInstance().mDmcCoreHandler.sendEmptyMessage(DmcCore.mupnp_controlpoint_new_id);
    }


    public void mupnpControlPointStup(){
        DmcCore.getInstance().mDmcCoreHandler.sendEmptyMessage(DmcCore.mupnp_controlpoint_setup);
    }

    public void mupnpControlPointSearch(){
        DmcCore.getInstance().mDmcCoreHandler.sendEmptyMessage(DmcCore.mupnp_controlpoint_search);
    }
}
