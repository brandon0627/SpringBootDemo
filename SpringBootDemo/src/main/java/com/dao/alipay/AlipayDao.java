package com.dao.alipay;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.alipay.api.response.AlipayTradeRefundResponse;


/**
 * @author brandon
 *	支付宝支付
 */
@Mapper
public interface AlipayDao {
	/**
	 *  app支付宝下单请求(插入)
	 */
	 int insertAppRequest(com.alipay.api.domain.AlipayTradeAppPayModel appPayRequestModel);

	/**
	 *  支付宝支付异步通知(更新)
	 */
	 int insertAppResponse(Map params);

	
	 int insertWapRequest(com.alipay.api.domain.AlipayTradeWapPayModel appPayRequestModel);
	
	 /**
	  * 退款记录保存
	  * */
	 int insertRefundResponse(AlipayTradeRefundResponse response);
}
