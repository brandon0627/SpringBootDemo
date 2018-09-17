package com.services.alipay;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;

/**
 * @author brandon
 *
 */
public interface AliPayService {
	
	/**
	 * @Title: getAlipayClient
	 * @Description: 获取支付宝实例化对象
	 * @return
	 * @return:AlipayClient
	 * @author: brandon
	 * @date:  2018年6月24日
	 */
	AlipayClient getAlipayClient();

	/**
	 * @Title: tradeAppPay
	 * @Description: APP生成订单
	 * @param params
	 * @return:Map
	 * @author: brandon
	 * @date:  2018年8月30日
	 */
	JSONObject tradeAppPayRequest(Map params) throws AlipayApiException; 
	
	/**
	 * @Title: tradeAppPayResponse
	 * @Description: 支付成功后的异步通知
	 * @param params
	 * @return:JSONObject
	 * @author: brandon
	 * @date:  2018年9月1日
	 */
	void tradeAppPayResponse(Map params);
	
	/**
	 * @Title: tradeWapPayRequest
	 * @Description: 网站H5支付
	 * @param params
	 * @return
	 * @throws AlipayApiException
	 * @return:JSONObject
	 * @author: brandon
	 * @date:  2018年9月13日
	 */
	String tradeWapPayRequest(Map params) throws AlipayApiException;
	
	/**
	 * @Title: tradeRefundRequest
	 * @Description: 退款请求
	 * @param params 订单号     out_trade_no
	 * 				  退款金额  refund_amount
	 * @return
	 * @throws AlipayApiException
	 * @return:JSONObject
	 * @author: brandon
	 * @date:  2018年9月14日
	 */
	JSONObject tradeRefundRequest(JSONObject params) throws AlipayApiException;
}
