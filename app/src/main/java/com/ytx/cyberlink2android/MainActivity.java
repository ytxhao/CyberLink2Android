package com.ytx.cyberlink2android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scorpio.framework.config.CoreSchema;
import com.scorpio.framework.core.CoreApplication;
import com.scorpio.framework.core.CoreEngine;
import com.scorpio.framework.core.CoreService;
import com.scorpio.framework.utils.PreferenceUtil;
import com.scorpio.framework.utils.ScoLog;
import com.ytx.cyberlink2android.activity.BaseActivity;
import com.ytx.cyberlink2android.activity.SettingActivity;
import com.ytx.cyberlink2android.application.LinkApplication;
import com.ytx.cyberlink2android.scorpio.business.cfg.BusinessCfg;
import com.ytx.cyberlink2android.scorpio.business.cfg.MessageTypeID;
import com.ytx.cyberlink2android.scorpio.business.data.update.CheckUpdateRequestModel;
import com.ytx.cyberlink2android.scorpio.business.data.update.CheckUpdateResponseModel;
import com.ytx.cyberlink2android.scorpio.business.http.HttpConnectedCfg;
import com.ytx.cyberlink2android.scorpio.ui.uicenter.UICenter;
import com.ytx.cyberlink2android.utils.Msg;

public class MainActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            processMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startEngine();
        // Example of a call to a native method
        PreferenceUtil.initInstance(getApplicationContext(), PreferenceUtil.MODE_ENCRYPT_ALL);
        Button bt = (Button) findViewById(R.id.bt_setting);
        final Intent mIntent = new Intent(this, SettingActivity.class);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(mIntent);
                checkVersion();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        // 注册指定的界面事件
        UICenter.event_ad.registerHandler(mHandler);
        UICenter.event_login.registerHandler(mHandler);
        UICenter.event_account.registerHandler(mHandler);
        UICenter.event_message.registerHandler(mHandler);
        UICenter.event_setting.registerHandler(mHandler);
        UICenter.event_default.registerHandler(mHandler);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 注销指定的界面事件
        UICenter.event_ad.unRegisterHandler(mHandler);
        UICenter.event_setting.unRegisterHandler(mHandler);
        UICenter.event_account.unRegisterHandler(mHandler);
        UICenter.event_login.unRegisterHandler(mHandler);
        UICenter.event_message.unRegisterHandler(mHandler);
        UICenter.event_default.unRegisterHandler(mHandler);
    }

    /**
     * 启动引擎
     */

    private void startEngine(){
        if(!CoreService.isStarted()){
            CoreSchema.setIS_SHOW_CONTACT(true);
            CoreApplication.init(getApplication());
            BusinessCfg.init();
            HttpConnectedCfg.init();
            this.startService(new Intent(this, CoreService.class));
        }
        Msg.init(getApplicationContext());
    }

    private void processMessage(Message msg) {
        switch (msg.what){
            case MessageTypeID.CHECK_FORCE_UPDATE:

                processVersionUpgrade(msg);
                break;
        }
    }

    private void processVersionUpgrade(Message msg) {
        Bundle bundle = msg.getData();
        final CheckUpdateResponseModel entity = msg.obj instanceof CheckUpdateResponseModel ? (CheckUpdateResponseModel) msg.obj
                : new CheckUpdateResponseModel();
        ScoLog.d(TAG, "bundle.getInt(UICenter.KEYWORK_CODE_CODE)="+bundle.getInt(UICenter.KEYWORK_CODE_CODE));

        getHelper().showDialog(R.string.av_controller_title);

    }

    private void checkVersion() {

        CheckUpdateRequestModel curm = new CheckUpdateRequestModel("tencent", LinkApplication.getInstance().getVersionName(),2);

        CoreEngine.getInstance().getBusinessCenter()
                .sendBeginBusinessMessage(MessageTypeID.CHECK_FORCE_UPDATE, curm);
    }

}
