package com.constans;

public class AliPayParams {

	/***************************正式环境***************************************/
	/***************默认网关****************/
	public static final String DEFAULT_GATEWAY = "https://openapi.alipay.com/gateway.do";
	
	/***************APP ID****************/
	public static final String AppID = "";
	
	/**************APP_PRIVATE_KEY**私钥**************/
	public static final String APP_PRIVATE_KEY = "";
	/***************ALIPAY_PUBLIC_KEY*公钥***************/
	public static final String ALIPAY_PUBLIC_KEY = "";
	
	/***************数据类型****************/
	public static final String DATA_TYPE = "json";
	
	/***************字符类型****************/
	public static final String CHARSET = "utf-8";
	
	/***************加密方式****************/
	public static final String SIGN_TYPE = "RSA2";
	
	/***************支付成功的回调url****************/
	public static final String CALL_BACK_URL = "https://openapi.alipay.com/gateway.do";
	
	/***************APP支付成功之后的异步通知******************/

	public static final String TRADE_APP_PAY_NOTIFY_URL = "---/alipay/app_pay_verify";
	
}
