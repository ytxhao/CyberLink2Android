package com.ytx.cyberlink2android.base;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.scorpio.framework.utils.PreferenceUtil;
import com.ytx.cyberlink2android.activity.BaseActivity;
import com.ytx.cyberlink2android.base.fragment.SimpleDialogFragment;
import com.ytx.cyberlink2android.base.listener.SimpleDialogClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Activity辅助类，一些常用的方法 继承BaseActivity或BaseFragment后，可以通过getHelper()获取
 *
 * @author chenyc
 */

public class ActivityHelper {

    private BaseActivity mActivity;

    public ActivityHelper(BaseActivity context) {
        this.mActivity = context;
    }

    public void showDialog(int messageId) {
        showSingleButtonDialog(messageId, null);
    }


    public void showSingleButtonDialog(String title, int messageId, int gravity, boolean cancelable, SimpleDialogClickListener listener){
        showSingleButtonDialog(title, mActivity.getText(messageId), gravity, null, cancelable, listener);
    }

    public void showSingleButtonDialog(int messageId, SimpleDialogClickListener listener) {
        showSingleButtonDialog(messageId, false, listener);
    }

    public void showSingleButtonDialog(int messageId, int singleTextId, SimpleDialogClickListener listener) {
        showSingleButtonDialog(messageId, singleTextId, false, listener);
    }

    public void showSingleButtonDialog(String message, int singleTextId, SimpleDialogClickListener listener) {
        showSingleButtonDialog(null, message, 0, mActivity.getString(singleTextId), false, listener);
    }

    public void showSingleButtonDialog(int messageId, boolean cancelable, SimpleDialogClickListener listener) {
        showSingleButtonDialog(null, mActivity.getText(messageId),0, null, cancelable, listener);
    }

    public void showSingleButtonDialog(int messageId, int singleTextId, boolean cancelable, SimpleDialogClickListener listener) {
        showSingleButtonDialog(null, mActivity.getText(messageId), 0, mActivity.getString(singleTextId), cancelable, listener);
    }

    public void showSingleButtonDialog(String title, CharSequence message, int gravity, String singleText, boolean cancelable, SimpleDialogClickListener listener) {
        if(!mActivity.isForegroundRunning)
            return;

        SimpleDialogFragment.newInstance()
                .setTitle(title)
                .setMessage(message)
                .setContentGravity(gravity)
                .setRightButtonText(singleText)
                .singleButton()
                .cancelable(cancelable)
                .setDialogClickListener(listener)
                .show(mActivity.getSupportFragmentManager());
    }

    public void showDialog(int messageId, SimpleDialogClickListener listener) {
        showDialog(mActivity.getText(messageId), listener);
    }

    public void showDialog(CharSequence message, SimpleDialogClickListener listener) {
        showDialog(message, null, null, listener);
    }

    public void showDialog(int messageId, int leftTextId, int rightTextId, SimpleDialogClickListener listener) {
        showDialog(messageId, leftTextId, rightTextId, false, listener);
    }

    public void showDialog(int messageId, int leftTextId, int rightTextId, boolean cancelable, SimpleDialogClickListener listener) {
        showDialog(mActivity.getText(messageId),mActivity.getString(leftTextId),mActivity.getString(rightTextId), cancelable,listener);
    }

    public void showDialog(CharSequence message, String leftText, String rightText, SimpleDialogClickListener listener) {
        showDialog(message, leftText, rightText, false, listener);
    }

    public void showDialog(CharSequence message, String leftText, String rightText, boolean cancelable, SimpleDialogClickListener listener) {
        if(!mActivity.isForegroundRunning)
            return;
        SimpleDialogFragment.newInstance(listener)
                .setMessage(message)
                .setLeftButtonText(leftText)
                .setRightButtonText(rightText)
                .cancelable(cancelable)
                .show(mActivity.getSupportFragmentManager());
    }

    public void showDialog(int titleId,int messageId, int leftTextId, int rightTextId, SimpleDialogClickListener listener) {
        showDialog(titleId, messageId, leftTextId, rightTextId, false, listener);
    }

    public void showDialog(int titleId,int messageId, int leftTextId, int rightTextId, boolean cancelable, SimpleDialogClickListener listener) {
        showDialog(mActivity.getString(titleId), mActivity.getText(messageId), mActivity.getString(leftTextId), mActivity.getString(rightTextId), cancelable, listener);
    }

    public void showDialog(String title, CharSequence message, String leftText, String rightText, boolean cancelable, SimpleDialogClickListener listener) {
        if(!mActivity.isForegroundRunning)
            return;
        SimpleDialogFragment.newInstance(listener)
                .setTitle(title)
                .setMessage(message)
                .setLeftButtonText(leftText)
                .setRightButtonText(rightText)
                .cancelable(cancelable)
                .show(mActivity.getSupportFragmentManager());
    }

    public void showDialog(View contentView, SimpleDialogClickListener listener){
        showDialog(contentView, null , null , true, false, false, listener,null);
    }

    public void showDialog(View contentView, boolean dismissDialog, SimpleDialogClickListener listener){
        showDialog(contentView, null, null, dismissDialog, false, false, listener,null);
    }

    public void showDialog(View contentView, View customerButtonRight, View customerButtonLeft, SimpleDialogClickListener cListener){
        showDialog(contentView, customerButtonRight, customerButtonLeft, true, false, false, null, cListener);
    }

    public void showDialog(View contentView, View customerButtonRight, View customerButtonLeft, boolean dismissDialog, boolean cancelable, boolean isSingleButton , SimpleDialogClickListener listener , SimpleDialogClickListener cListener){
        if(!mActivity.isForegroundRunning)
            return;
        SimpleDialogFragment.newInstance(listener)
                .setSingleButton(isSingleButton)
                .setContentView(contentView)
                .setUseCustomerButtonRight(customerButtonRight)
                .setUseCustomerButtonLeft(customerButtonLeft)
                .setUseCustomerListener(cListener)
                .setDismissDialog(dismissDialog)
                .cancelable(cancelable)
                .show(mActivity.getSupportFragmentManager());
    }

    /**
     * save config value to sharepreference
     */
    public void saveConfig(String key, String value) {
        PreferenceUtil.getInstance().putString(key, value);
    }

    public void saveConfig(String key, int value) {
        PreferenceUtil.getInstance().putInt(key, value);
    }

    public void saveConfig(String key, long value) {
        PreferenceUtil.getInstance().putLong(key, value);
    }

    public void saveConfig(String key, boolean value) {
        PreferenceUtil.getInstance().putBoolean(key, value);

    }

    /**
     * get config value from sharepreference
     */
    public String getConfig(String key) {
        return PreferenceUtil.getInstance().getString(key);
    }

    public int getConfig(String key, int defValue) {
        return PreferenceUtil.getInstance().getInt(key, defValue);
    }

    public long getConfig(String key, long defValue) {
        return PreferenceUtil.getInstance().getLong(key, defValue);
    }

    public boolean getConfig(String key, boolean defValue) {
        return PreferenceUtil.getInstance().getBoolean(key, defValue);
    }

    public void removeConfig(String key) {
        PreferenceUtil.getInstance().remove(key);
    }


    public void showMessage(int id) {
        Toast.makeText(mActivity, id, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    public void showMessageLength(int id) {
        Toast.makeText(mActivity, id, Toast.LENGTH_LONG).show();
    }

    public void showMessageLength(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 检查是否是WiFi网络
     * @return
     */
    public boolean isWifiNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    public boolean isNonWifiNetwork(){
        ConnectivityManager connMgr = (ConnectivityManager)mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查网络是否可用
     * @return
     */
    public boolean isNetAvailable(){
        ConnectivityManager manager = (ConnectivityManager)mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSDCardValid() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public boolean isWeixinInstalled() {
        final String strWeixinPkgName = "com.tencent.mm";
        final PackageManager pkgManager = mActivity.getPackageManager();
        if(pkgManager == null){
            return false;
        }
        List<PackageInfo> lstPkgInfos = pkgManager.getInstalledPackages(0);
        if(lstPkgInfos == null){
            return false;
        }
        for (int nIndex = 0; nIndex < lstPkgInfos.size(); nIndex++) {
            String strPkgName = lstPkgInfos.get(nIndex).packageName;
            if (strPkgName.equals(strWeixinPkgName)){
                return true;
            }
        }
        return false;
    }


    /**
     * @brief: 设置屏幕背景透明度
     * @param fAlpha 0.0f~1.0f
     * @param addFlag
     */
    public void setBackgroundAlpha(float fAlpha, boolean addFlag){
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = fAlpha;
        mActivity.getWindow().setAttributes(lp);
        if (addFlag) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    /**
     * 设置是否静止背景音乐
     * @param context
     * @param bMute  true关闭，false打开
     * @return
     */
    public boolean muteAudioFocus(Context context, boolean bMute) {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int result;
        if(bMute){
            result = am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }else{
            result = am.abandonAudioFocus(null);
        }
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    /**
     * 修复PopupWindow在7.0设备上的
     *
     * @param popupWindow
     * @param anchorView
     */
    public void showPopUpWindow(PopupWindow popupWindow, View anchorView) {
        if (Build.VERSION.SDK_INT >= 24) {
            int[] locations = new int[2];
            anchorView.getLocationOnScreen(locations);

            popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, 0, locations[1] + anchorView.getHeight() + 1);
        } else {
            popupWindow.showAsDropDown(anchorView);
        }
    }

    public void putStringsToLocal(String key, String value) {
        String oldString = PreferenceUtil.getInstance().getString(key, "");
        StringBuilder stringBuilder = new StringBuilder();
        if (TextUtils.isEmpty(oldString)) {
            stringBuilder.append(value);
        } else {
            stringBuilder.append(oldString);
            stringBuilder.append(",");
            stringBuilder.append(value);
        }
        PreferenceUtil.getInstance().putString(key, stringBuilder.toString());
    }

}
