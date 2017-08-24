package com.ytx.cyberlink2android.base.listener;


import com.ytx.cyberlink2android.base.fragment.SimpleDialogFragment;

/**
 * 对话框按钮点击监听
 *
 */
public interface SimpleDialogClickListener {
    void onDialogLeftBtnClick(SimpleDialogFragment dialog);
    void onDialogRightBtnClick(SimpleDialogFragment dialog);
}