package com.ytx.cyberlink2android.scorpio.business.cfg;


import com.scorpio.framework.business.protocol.MessageDataKey;

/**
 * 
 * 任务相关标示定义类
 * 
 * 定义标准:
 * 
 * 1,ui自定义(个页面允许重合)所使用的标示在1-1000(十六进制)内
 * 
 * 2,1000-2000字段保留
 * 
 * 3,3000-10000为后台任务字段
 * 
 * 4,20000以上为ui事件提示字段
 * 
 */
public class MessageTypeID extends MessageDataKey {

	/*
	 * 后台任务定义
	 */
	/**
	 * 查询流量任务
	 */
	public static final int LIFE_CHECK_FLOW = 0x5001;
	public static final int LIFE_CHECK_FLOW_OK = 0x50011;
	public static final int LIFE_CHECK_FLOW_FAIL = 0x50012;
	
	/**
	 * 订阅流量接口
	 */
	public static final int LIFE_FLOW_ORDER_ACITON = 0x5003;
	/**
	 * 根据手机号码查询流量服务
	 */
	public static final int LIFE_FLOW_ORDER_SHOW_LIST = 0x5002;
	/**
	 * 不用手机号码查询流量服务
	 */
	public static final int LIFE_FLOW_ORDER_SHOW_LIST_NO_PHONE = 0x5004;
	/**
	 * 订阅流量发送验证码
	 */
	public static final int LIFE_FLOW_ORDER_CODE = 0x5005;
	
	/**
	 * 自动获取全局配置
	 * 
	 */
	public static final int AUTO_GET_SETTING = 0x5006;
	/*
	 * 登录模块
	 */
	/**
	 * 根据手机号及IMSI检查用户是否存在
	 */
	public static final int LOGIN_CHECK_ACCOUNT_EXIST_WITH_MDN = 0x4001;
	/**
	 * 拿手机号去注册
	 */
	public static final int LOGIN_REGEDIT_WITH_MDN = 0x4002;
	/**
	 * 根据imsi号从服务器获取mdn
	 */
	public static final int LOGIN_GET_MDN_WITH_IMSI = 0x4003;
	/**
	 * 登录前预处理任务
	 */
	public static final int LOGIN_CHECK_AUTO_LOGIN = 0x4009;
	/**
	 * 超级自动登录 (手机号 String key：msg 密码 String key：msg1)
	 */
	public static final int LOGIN_EVENT_SUPER_AUTO_LOGIN = 0x40091;
	/**
	 * 用户手动登录
	 * 
	 */
	public static final int LOGIN_EVENT_USER_GOTO_LOGIN = 0x40092;

	/**
	 * 登录前预处理任务
	 */
	public static final int LOGIN_ADD_ACCOUNT = 0x400a;
	/**
	 * 添加账号成功进入主界面
	 * 
	 */
	public static final int LOGIN_EVENT_ADD_ACCOUNT_GOTO_MAIN_PAGE = 0x400a1;
	/**
	 * 添加账号失败提示
	 * 
	 */
	public static final int LOGIN_EVENT_ADD_ACCOUNT_ERROR = 0x400a2;
	/*
	 * 注册相关
	 */
	/**
	 * 注册，获取验证码
	 */
	public static final int REG_GET_VERIFY_CODE = 0x4004;
	/**
	 * 重置密码，发验证码
	 */
	public static final int REG_SEND_SMS_VERIFY = 0x4005;
	/**
	 * 验证并注册
	 */
	public static final int REG_CHECK_VERIFY_CODE_AND_REG = 0x4006;
	/**
	 * 重置密码
	 */
	public static final int REG_RESET_PASSWORD = 0x4007;
	/**
	 * 登出及注销
	 */
	public static final int REG_LOG_OFF = 0x4008;

	/**
	 * 获取指定域名邮箱配置
	 * 
	 */
	public static final int AUTO_ACCOUNT_MAIL_SETTING_GET = 4501;

	/**
	 * 提交指定域名邮箱配置
	 * 
	 */
	public static final int AUTO_ACCOUNT_MAIL_SETTING_ADD = 4502;
	
	/*
	 * 获取Web端联系人
	 */
	public static final int GET_WEB_CONTACT = 0x6001;

	public static final int GET_CONTACT_OK = 0x6002;
	public static final int GET_LOCAL_CONTACT_OK = 0x6003;
	/**
	 * 本地联系人变化监听操作成功
	 */
	public static final int GET_LOCAL_CONTACT_CHANGE_OK = 0x6007;

	public static final int GET_CONTACT_FAILED = 0x6004;

	public static final int GET_CONTACT_NODATA = 0x6005;
	
	public static final int CONTACT_REFRESH = 0x6006;
	public static final int CONTACT_ADD = 0x6008;

	/*
	 * 代收邮件同步
	 * 
	 */
	
	/**
	 * 添加代收邮件，同步
	 * 
	 */
	public static final int COLLECT_ADD_MAIL = 0x6008;
	/**
	 * 查询代收邮箱
	 * 
	 */
	public static final int COLLECT_QUERY_MAIL = 0x6009;
	/**
	 * 查询/同步邮箱成功
	 */
	public static final int COLLECT_QUERY_MAIL_RESUILT_OK = 0x60091;
	/**
	 * 查询/同步邮箱失败
	 */
	public static final int COLLECT_QUERY_MAIL_RESUILT_FAIL = 0x60092;
	/**
	 * 查询/同步wo邮箱
	 */
	public static final int COLLECT_QUERY_WO_MAIL_RESUILT_OK = 0x60093;
	/**
	 * 修改代收邮箱配置
	 * 
	 */
	public static final int COLLECT_MOTIFY_MAIL = 0x600a;
	/**
	 * 删除代收邮箱
	 * 
	 */
	public static final int COLLECT_DEL_MAIL = 0x600b;
	
	/**
	 * 根据沃邮箱昵称获取手机号码
	 * 
	 */
	public static final int WOMAIL_GET_PHONE_FROM_NICK = 0x600c;
	/**
	 * 查询代收邮箱(加密)
	 * 
	 */
	public static final int COLLECT_QUERY_MAIL_2 = 0x600d;
	/*
	 * 邮件动作相关
	 * 
	 */
	/**
	 * 发送邮箱
	 * 
	 */
	public static final int MAIL_SEND_MESSAGE = 0x7001;
	
	/**
	 * 获取聚合邮件列表
	 */
	public static final int MAIL_GET_AD_LIST = 0x7002;
	/**
	 * 点击发送邮件,出现进度条
	 */
	public static final int MAIL_SEND_FINISH = 0x7003;
	/**
	 * 邮件发送成功
	 */
	public static final int MAIL_SEND_SUCCESS = 0x7004;
	/**
	 * 邮件发送失败,取消进度条
	 */
	public static final int MAIL_SEND_FAIL = 0x7005;
	
	/*
	 *获取内容列表 
	 * 
	 */
	
	public static final int DISCOVER_CONTEXT_EXPAND_GET_PAGES = 0x8001;

	/*
	 * 展示
	 */
	/**
	 * 消息列表展示
	 */
	public static final int MESSAGE_LIST_START = 0x4e21;// 20001
	public static final int MESSAGE_LIST_FAILED = 0x4e22;
	public static final int MESSAGE_LIST_FINISH = 0X4e23;
	public static final int MESSAGE_LIST_READ = 0x4e24;
	public static final int MESSAGE_LIST_FOLDER_LOADING = 0x4e2b;//正在加载时

	/**
	 * 标记已读、标星、删除
	 */
	public static final int MESSAGE_LIST_FLAGGED = 0x4e27;
	public static final int MESSAGE_LIST_READED = 0x4e26;
	public static final int MESSAGE_LIST_DELETED = 0x4e28;
	/**
	 * 返回键处理
	 */
	public static final int BACK_PRESS_CANCEL_SELECTED_MAIL = 0x4e25;// 返回键取消列表选中状态

	/**
	 * 邮件列表操作
	 */
	public static final int MESSAGE_LIST_SHOW_THREAD = 0x4e2a;// 原为0x4e26，与MESSAGE_LIST_FLAGGED冲突，by
																// zhangchi
																// change
	public static final int MESSAGE_LIST_GO_BACK = 0x4e29;// 原为0x4e27与MESSAGE_LIST_FLAGGED冲突。by
															// zhangchi change
	public static final int MESSAGE_LIST_SET_MENU_FALG = 0x4e30;// 修改底部菜单标星操作文字
	public static final int MESSAGE_LIST_SET_MENU_READED = 0x4e31;

	public static final int BOTTOM_MENU_CHANGE = 0x000001;
	
	public static final int MESSAGE_LIST_SEARCH = 0x4e32;
	public static final int MESSAGE_LIST_SEARCH_CANCEL = 0x4e35;
	public static final int MESSAGE_LIST_SEARCH_SHOW = 0x4e53;
	
	public static final int PROGRESSBAR_SHOW = 0;
	public static final int PROGRESSBAR_HIDDEN = 1;
	
	/**
	 * 打开文件夹
	 */
	public static final int OPEN_FOLDER = 0x4e33;

	public static final int INVALIDATE_DRAWALAYOUT = 0x4e34;
	
	public static final int LEFT_MENU_TOOGLE = 0x4e36;
	
	/**
	 * 账户校验返回信息码
	 */
	public static final int Account_CHECK_SUCCESS = 0x4e37;
	public static final int Account_INFO_ERROR = 0x4e38;
	public static final int Account_CERTIFICATE_ERROR = 0x4e39;
	public static final int Account_INCOMING_CONNECT_ERROR = 0x4e40;
	public static final int Account_OUTGOING_CONNECT_ERROR = 0x4e41;
	public static final int Account_CONNECT_ERROR = 0x4e42;
	public static final int ACCOUNT_INPUT_ERROR = 0x4e43;
	public static final int ACCOUNT_ILLEGALARGUMENTEXCEPTION = 0x4e51;
	/**
	 * 账号信息发送变化
	 * 
	 */
	public static final int ACCOUNT_INFO_CHANGE_NOTIFY = 0x4e44;
	
	/**
	 * 全选actionbar 显示和消失
	 */
	public static final int ACTION_SELECT_BAR_SHOW = 0x4e45;
	public static final int ACTION_SELECT_BAR_HIDDEN = 0x4e46;
	public static final int ACTION_SELECT_ALL = 0x4e47;
	
	public static final int PUSH_BUSINESS_ID = 0x4e48;
	
	public static final int VERSION_UPGRADE = 0x4e49;
	
	public static final int FOLDER_REFRESH = 0x4e50;
	
	public static final int MARKET_MAIL_INDEX = 0x4e52;
	
	public static final int MARKET_MAIL_BACK = 0x4e54;
	
	/**
	 * 创建临时账号
	 */
	public static final int GET_TEMP_ACCOUNT = 0x4f00;
	/**
	 * 通知显示未读数
	 */
	public static final int DRAW_UNREAD_NUM = 0x5f00;
	/**
	 * 获取控件数据
	 */
	public static final int GET_VIEW_DATE = 0x5f01;
	/**
	 * push开关通知
	 */
	public static final int PUSH_OPEN_CLODE = 0x5e00;
	public static final int SEND_PUSH_TIME = 0x5e01;
	/**
	 * 是否可以下拉刷新通知
	 */
	public static final int IS_CAN_REFRESH = 0x6e00;
	/**
	 * 更改沃邮箱别名通知
	 */
	public static final int CALL_CHANGE_RENAME = 0x6e01;
	/**
	 * 欢迎邮件中别名跳转
	 */
	public static final int WELCOME_CHANGE_RENAME = 0x6e02;
	/**
	 * 欢迎邮件修改密码跳转
	 */
	public static final int WELCOME_CHANGE_PSW = 0x6e03;
	/**
	 * 判断程序退出后口袋和发现push
	 */
	public static final int EXIT_PUSH = 0x6e04;
	/**
	 * 桌面图标小红点数量显示
	 */
	public static final int ICON_NUMBER = 0x6e05;
	/**
	 * 手机号反查imsi失败跳到注册界面
	 */
	public static final int CHECK_FAIL_SWIGHT_REGISTER = 0x6e06;
	/**
	 * 多邮箱push更新actionbar左上角小红点
	 */
	public static final int PUSH_DRAW_RED = 0x6e07;
	
	/**
	 * 沃邮箱强制升级
	 */
	
	public static final int CHECK_FORCE_UPDATE = 0x6e08;
	

	
	/**
	 * 验证验证码是否正确
	 */
	public static final int CHECK_VERFY_CODE = 0x6e09;
	
	/**
	 * 启动动画是否有更新
	 */
	public static final int CHECK_START_ANIMATION_UPDATE = 0x6e0a;
	/**
	 * 是否显示红包页面
	 */
	public static final int SHOW_REG_BAG_PAGE = 0x6e0b;
	/**
	 * 沟通页中邮件列表为null时显示的图片
	 */
	public static final int SHOW_EMPTY_MAILBOX = 0x6e0c;
	
	/**
	 * 获取广告信息
	 *  
	 */
	
	public static final int CHECK_AD_INFORMATION = 0x6e0d;

}
