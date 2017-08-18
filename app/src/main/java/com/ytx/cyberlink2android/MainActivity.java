package com.ytx.cyberlink2android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ytx.cyberlink2android.activity.BaseActivity;
import com.ytx.cyberlink2android.activity.SettingActivity;
import com.ytx.cyberlink2android.utils.PreferenceUtil;

public class MainActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        PreferenceUtil.initInstance(getApplicationContext(), PreferenceUtil.MODE_ENCRYPT_ALL);
        Button bt = (Button) findViewById(R.id.bt_setting);
        final Intent mIntent = new Intent(this, SettingActivity.class);
        //tv.setText(stringFromJNI());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        stringFromJNI();
//                    }
//                }).start();

                startActivity(mIntent);
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
