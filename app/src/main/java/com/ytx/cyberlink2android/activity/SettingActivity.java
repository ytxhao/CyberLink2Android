package com.ytx.cyberlink2android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scorpio.framework.utils.ScoLog;
import com.ytx.cyberlink2android.R;




public class SettingActivity extends BaseActivity implements  View.OnClickListener {

    private static final String TAG = "SettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findView(R.id.llMirrorTV).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.llMirrorTV){

            ScoLog.d(TAG,"Click llMirrorTV");

            startActivity(new Intent(this,AVMediaControllerActivity.class));
        }
    }
}
