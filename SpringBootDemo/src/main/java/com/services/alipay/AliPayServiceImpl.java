package com.services.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.constans.AliPayParams;
import com.dao.alipay.AlipayDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Administrator
 * 支付宝 支付 service
 */
@Service("aliPayService")
public class AliPayServiceImpl implements AliPayService {

	@Autowired
	private AlipayDao alipayDao;

	//实例化客户端												
	AlipayClient alipayClient ;
	
	private Logger logger = Logger.getLogger(getClass());  
	
	/* (non-Javadoc)
	 * 			获取支付宝实例化对象
	 * @see service.alipay.AliPayService#getAlipayClient()
	 */
	public AlipayClient getAlipayClient() {
		if(null == alipayClient) {
			alipayClient =  new DefaultAlipayClient(AliPayParams.DEFAULT_GATEWAY,
													AliPayParams.AppID, 
													AliPayParams.APP_PRIVATE_KEY, 
													AliPayParams.DATA_TYPE,
													AliPayParams.CHARSET,
													AliPayParams.ALIPAY_PUBLIC_KEY, 
													AliPayParams.SIGN_TYPE);
		}
		return alipayClient;
	}

	/* (non-Javadoc)
	 * @see com.services.alipay.AliPayService#tradeAppPay(java.util.Map)
	 * 		参数：	
	 * 			body
	 * 			subject
	 * 			order_no
	 * 			order_amount
	 * APP端下单 
	 * 			1. 实例化 alipay工具
	 * 			2. 封装请求数据	 AlipayTradeAppPayModel
	 * 			3. 保存请求的数据
	 * 			4. 请求支付宝APP下单接口
	 */
	@Override
	public JSONObject tradeAppPayRequest(Map params) throws AlipayApiException {
		logger.info("==APP生成订单=tradeAppPayRequest="+params);
		JSONObject result = new JSONObject();
		//1. 实例化客户端
		AlipayClient alipayClient = getAlipayClient();
		
		//2. 封装请求数据	
		String order_no = (String)params.get("order_no");

		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest appPayRequest = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("这里是body数据");
		model.setSubject("这里是subject数据");
		model.setOutTradeNo(order_no);
		model.setTimeoutExpress("30m");
		model.setTotalAmount((String)params.get("order_amount"));
		model.setProductCode("QUICK_MSECURITY_PAY");
		appPayRequest.setBizModel(model);
		appPayRequest.setNotifyUrl("支付成功后的回调");
		
//		System.out.println("order=="+model.getOutTradeNo());
		logger.info("order=="+model.getOutTradeNo());

		//3. 存入请求的数据 
		boolean flag = alipayDao.insertAppRequest(model) == 1;
		if(!flag) {
			result.put("code", "4561");
			result.put("data", "网络异常");
			return result;
		}
		
		//4. 请求支付宝下单
        AlipayTradeAppPayResponse appPayResponse = alipayClient.sdkExecute(appPayRequest);
//        System.out.println(appPayResponse.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。

        result.put("code", "0");
		result.put("data", appPayResponse.getBody());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.services.alipay.AliPayService#tradeAppPayResponse(java.util.Map)
	 * 1. 修改订单状态，插入返回结果通知
	 */
	@Override
	@Transactional
	public void tradeAppPayResponse(Map tradeAppPayResponse) {
		logger.info("==APP订单支付异步通知=tradeAppPayResponse="+tradeAppPayResponse);
	}

	/* (non-Javadoc)
	 * @see com.services.alipay.AliPayService#tradeWapPayRequest(java.util.Map)
	 * 	支付宝H5支付
	 */
	@Override
	public String tradeWapPayRequest(Map params) throws AlipayApiException {
		logger.info("==WAP生成订单=tradeWapPayRequest="+params);
		//1. 实例化客户端
		AlipayClient alipayClient = getAlipayClient();
		
		//2. 封装请求数据	
		String order_no = (String)params.get("order_no");

		AlipayTradeWapPayRequest wappayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
//		alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
		wappayRequest.setNotifyUrl("支付成功后的回调");// 在公共参数中设置回跳和通知地址
//		alipayRequest.setBizContent("{" + ""
//				+ "    \"out_trade_no\":\"20180913010101002\"," + ""
//				+ "    \"total_amount\":0.01,"
//				+ "    \"subject\":\"Iphone6 16G\"," + ""
//				+ "    \"product_code\":\"QUICK_WAP_WAY\"" + "  }");// 填充业务参数
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setBody("这里是body数据");
		model.setSubject("这里是subject数据");
		model.setOutTradeNo(order_no);
		model.setTimeoutExpress("30m");
		model.setTotalAmount((String)params.get("order_amount"));
		model.setProductCode("QUICK_MSECURITY_PAY");
		wappayRequest.setBizModel(model);
		
		//3. 存入请求的数据 
		boolean flag = alipayDao.insertWapRequest(model) == 1;
		if(!flag) {
			return "网络异常";
		}
		
		//4. 请求支付宝下单
		String form = alipayClient.pageExecute(wappayRequest).getBody(); // 调用SDK生成表单
		return form;
	}
	
	/* (non-Javadoc)
	 * @see com.services.alipay.AliPayService#tradeAppPay(java.util.Map)
	 * 		参数：	
	 * 			out_trade_no
	 * 			refund_amount
	 * APP端下单 
	 * 			1. 实例化 alipay工具
	 * 			2. 封装请求数据	 AlipayTradeAppPayModel
	 * 			3. 保存请求的数据
	 * 			4. 请求支付宝退款接口
	 */
	@Override
	public JSONObject tradeRefundRequest(JSONObject params) throws AlipayApiException  {
		logger.info("==支付宝订单退款=tradeRefundRequest="+params);
		JSONObject result = new JSONObject();
		//1. 实例化客户端
		AlipayClient alipayClient = getAlipayClient();
		
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		String order_no = (String)params.get("out_trade_no");
		//2. 封装请求数据	
		request.setBizContent(params.toJSONString());
		
		//4. 请求支付宝退款接口 
		AlipayTradeRefundResponse response = alipayClient.execute(request);
		if(response.isSuccess()){
			logger.info(order_no+"---支付宝退款 调用成功");
			/** 保存数据  */
			alipayDao.insertRefundResponse(response);
			result.put("code", "0");
			result.put("data", response.getMsg());
			return result;
		} else {
			logger.info(order_no+"---支付宝退款 调用失败");
			result.put("code", "3369");
			result.put("data", response.getMsg());
			return result;
		}
	}
}
