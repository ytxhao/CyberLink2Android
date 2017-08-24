package com.ytx.cyberlink2android.base.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ytx.cyberlink2android.R;
import com.ytx.cyberlink2android.base.listener.SimpleDialogClickListener;


public class SimpleDialogFragment extends DialogFragment {
    public static final String TAG = "SimpleDialogFragment";

	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
    
	private int dialogID = 0;
	private SimpleDialogClickListener mListener;
	private View contentView;
	private View customerButtonRight = null;
	private View customerButtonLeft = null;
	private SimpleDialogClickListener mCustomerListener;
	private String title, leftButtonText, rightButtonText;
	private int leftButtonTextColor,rightButtonTextColor;
	private CharSequence message;
	private boolean isSingleButton = false;
	private boolean isDismissDialog = true;
	private int gravity = LEFT;
	
	private OnClickListener btnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(isDismissDialog) {
				dismiss();
			}
            if(mListener == null){
				return;
			}
			if(v.getId() == R.id.btnAntsDialogLeft){
				mListener.onDialogLeftBtnClick(SimpleDialogFragment.this);
			}else if(v.getId() == R.id.btnAntsDialogRight){
				mListener.onDialogRightBtnClick(SimpleDialogFragment.this);
			}
		}
	};
	
	public static SimpleDialogFragment newInstance() {
		return newInstance(null);
	}
    
	public static SimpleDialogFragment newInstance(SimpleDialogClickListener listener) {
		SimpleDialogFragment dialog = new SimpleDialogFragment();
        dialog.setDialogClickListener(listener);
		dialog.setRetainInstance(true);
		return dialog;
	}

	@Override
	// 在onCreate中设置对话框的风格、属性等
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int style = DialogFragment.STYLE_NO_TITLE;
		setStyle(style, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View mView = inflater.inflate(R.layout.fragment_simple_dialog, container);

		TextView tvAntsDialogContent = (TextView) mView.findViewById(R.id.tvAntsDialogContent);
		tvAntsDialogContent.setMovementMethod(LinkMovementMethod.getInstance());
		if (message instanceof Spannable) {
			// support text as href or html
			int end = message.length();
			Spannable sp = (Spannable) message;
			URLSpan[] urls = sp.getSpans(16, end, URLSpan.class);
			SpannableStringBuilder style = new SpannableStringBuilder(message);
			style.clearSpans();
			for (URLSpan url : urls) {
				MyURLSpan myURLSpan = new MyURLSpan(url.getURL(), getActivity());
				style.setSpan(myURLSpan, sp.getSpanStart(url),
						sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			tvAntsDialogContent.setText(style);
		}
		tvAntsDialogContent.setText(message);

		Button btnAntsDialogLeft = (Button) mView.findViewById(R.id.btnAntsDialogLeft);
		if(leftButtonText == null){
			leftButtonText = getString(R.string.cancel);
		}
		if(leftButtonTextColor!=0){
			btnAntsDialogLeft.setTextColor(getResources().getColor(leftButtonTextColor));
		}
        btnAntsDialogLeft.setText(leftButtonText);
        btnAntsDialogLeft.setOnClickListener(btnClickListener);
		Button btnAntsDialogRight = (Button) mView.findViewById(R.id.btnAntsDialogRight);
		if(rightButtonText == null){
			rightButtonText = getString(R.string.ok);
		}
		if(rightButtonTextColor!=0){
			btnAntsDialogRight.setTextColor(getResources().getColor(rightButtonTextColor));
		}
        btnAntsDialogRight.setText(rightButtonText);
        btnAntsDialogRight.setOnClickListener(btnClickListener);
        if(contentView != null){
            ScrollView svAntsDialogContent = (ScrollView) mView.findViewById(R.id.svAntsDialogContent);
            svAntsDialogContent.removeAllViews();
            svAntsDialogContent.addView(contentView);
        }
		if(isSingleButton || mListener == null){
			mView.findViewById(R.id.lineAntsDialog).setVisibility(View.GONE);
            btnAntsDialogLeft.setVisibility(View.GONE);
            btnAntsDialogRight.setBackgroundResource(R.drawable.btn_simple_dialog_bottom);
        }
        if(mListener == null && mCustomerListener == null){
            cancelable(true);
        }
		if(title != null){
			TextView tv = (TextView)mView.findViewById(R.id.tvAntsDialogTitle);
			tv.setVisibility(View.VISIBLE);
			tv.setText(title);
			tvAntsDialogContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			if(gravity == CENTER){
				tvAntsDialogContent.setGravity(Gravity.CENTER);
			}else if(gravity == RIGHT){
				tvAntsDialogContent.setGravity(Gravity.RIGHT);
			}else {
				tvAntsDialogContent.setGravity(Gravity.LEFT);
			}
			tvAntsDialogContent.setTextColor(getResources().getColor(R.color.black70));
		}

		if(customerButtonRight != null || customerButtonLeft != null){
			mView.findViewById(R.id.lineAntsDialog).setVisibility(View.GONE);
			mView.findViewById(R.id.ivVerticalLine).setVisibility(View.GONE);
			btnAntsDialogLeft.setVisibility(View.GONE);
			btnAntsDialogRight.setVisibility(View.GONE);
		}


		if(mCustomerListener != null){
			if(customerButtonRight != null){
				customerButtonRight.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mCustomerListener.onDialogRightBtnClick(SimpleDialogFragment.this);
					}
				});
			}

			if(customerButtonLeft != null) {
				customerButtonLeft.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mCustomerListener.onDialogLeftBtnClick(SimpleDialogFragment.this);
					}
				});
			}
		}
		return mView;
	}
    
    public void show(FragmentManager manager){
        show(manager,TAG);
    }
    
	
    public void show(FragmentManager manager, SimpleDialogClickListener listener){
        setDialogClickListener(listener);
        show(manager,TAG);
    }
    
	@Override
	public void show(FragmentManager manager, String tag) {
		show(manager.beginTransaction(), tag);
	}
	
	@Override
	public int show(FragmentTransaction transaction, String tag) {
		int i = super.show(transaction, tag);
		getFragmentManager().executePendingTransactions();
		return i;
	}

	public SimpleDialogFragment setDismissDialog(boolean isDismissDialog){
		this.isDismissDialog = isDismissDialog;
		return this;
	}

	/**设置标题文字*/
	public SimpleDialogFragment setTitle(String title){
		this.title = title;
		return this;
	}
	/**设置左边按钮文字*/
	public SimpleDialogFragment setLeftButtonText(String text){
		leftButtonText = text;
		return this;
	}

	/**设置左边按钮文字yanse*/
	public SimpleDialogFragment setLeftButtonTextColor(int textColor){
		leftButtonTextColor = textColor;
		return this;
	}
    
	/**设置右边按钮文字*/
	public SimpleDialogFragment setRightButtonText(String text){
		rightButtonText = text;
		return this;
	}

	/**设置左边按钮文字yanse*/
	public SimpleDialogFragment setRightButtonTextColor(int textColor){
		rightButtonTextColor = textColor;
		return this;
	}
    
    public SimpleDialogFragment cancelable(boolean cancelable){
        setCancelable(cancelable);
        return this;
    }
    
	/**获取对话框标识ID，用来区分后续操作*/
	public int getDialogID() {
		return dialogID;
	}
	/**设置对话标识ID*/
	public SimpleDialogFragment setDialogID(int dialogID) {
		this.dialogID = dialogID;
		return this;
	}
	
	/**设置要显示的文字内容*/
	public SimpleDialogFragment setMessage(CharSequence message){
		this.message = message;
		return this;
	}

	/**设置文字内容显示位置 left center right 默认 left**/
	public SimpleDialogFragment setContentGravity(int gravity){
		this.gravity = gravity;
		return this;
	}
    
	/**设置按钮点击监听*/
	public SimpleDialogFragment setDialogClickListener(SimpleDialogClickListener mListener) {
		this.mListener = mListener;
		return this;
	}
	/**获取自定义的内容视图*/
	public View getContentView() {
		return contentView;
	}
	/**设置自定义视图*/
	public SimpleDialogFragment setContentView(View contentView) {
		this.contentView = contentView;
		return this;
	}

	public SimpleDialogFragment setUseCustomerButtonRight(View customerButtonRight){
		this.customerButtonRight = customerButtonRight;
		return this;
	}

	public SimpleDialogFragment setUseCustomerButtonLeft(View customerButtonLeft){
		this.customerButtonLeft = customerButtonLeft;
		return this;
	}

	public SimpleDialogFragment setUseCustomerListener(SimpleDialogClickListener mCustomerListener){
		this.mCustomerListener = mCustomerListener;
		return this;

	}


	/**
	 * 调用此方法后只显示一个按钮
	 * <p>显示单个按钮（右边按钮），点击后回调mListener的onDialogRightBtnClick()
	 */
	public SimpleDialogFragment singleButton() {
		isSingleButton = true;
		return this;
	}

	public SimpleDialogFragment setSingleButton(boolean isSingleButton){
		this.isSingleButton = isSingleButton;
		return this;
	}


	private static class MyURLSpan extends ClickableSpan {
		private String mUrl;
		private Context context;
		MyURLSpan(String url, Context context) {
			mUrl = url;
			this.context = context;
		}

		@Override
		public void onClick(View widget) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "" + mUrl, Toast.LENGTH_SHORT).show();
		}
	}
    
}
