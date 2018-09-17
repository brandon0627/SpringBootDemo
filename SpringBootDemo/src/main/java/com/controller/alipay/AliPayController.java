package com.controller.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.constans.AliPayParams;
import com.dao.user.UserDao;
import com.services.alipay.AliPayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/alipay")
public class AliPayController {

	@Autowired
	private AliPayService aliPayService;

	@Autowired
	private UserDao userDao;

	private Logger logger = Logger.getLogger(getClass());

	/**
	 * @Title: trade_app_pay
	 * @Description: 支付宝统一下单
	 * @param ucode
	 * @param order_no
	 * @return
	 * @throws AlipayApiException
	 * @return:String
	 * @author: brandon
	 * @date: 2018年9月7日
	 */
	@PostMapping(value = "/trade_app_pay")
	public String trade_app_pay(String ucode, String order_no) throws AlipayApiException {
		JSONObject resultData = new JSONObject();
		Map params = new HashMap<String, Object>();
		// 数据校验

		// 请求下单
		resultData = aliPayService.tradeAppPayRequest(params);
		// 将结果返回前端
		return resultData.toString();
	}

	/**
	 * @Title: verify
	 * @Description: 服务端验证支付信息 app端支付成功后支付宝的回调
	 * @param request
	 * @param response
	 * @return:void
	 * @author: brandon
	 * @throws AlipayApiException
	 * @date: 2018年6月24日
	 */
	@PostMapping(value = "/app_pay_verify")
	public void app_verify(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		logger.info("==支付成功异步通知==" + params);
		// System.out.println("==app_verify=="+params);
		boolean flag = AlipaySignature.rsaCheckV1(params, AliPayParams.ALIPAY_PUBLIC_KEY, AliPayParams.CHARSET,
				AliPayParams.SIGN_TYPE);
		/* System.out.println(flag); */
		logger.info("==支付成功异步通知结果==" + flag + "==" + params);

		if (!flag) {
			// 失败处理
			logger.info("==支付 异步通知校验失败--" );
		}

		aliPayService.tradeAppPayResponse(params);

	}


	public static void main(String[] args) throws AlipayApiException {
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayParams.DEFAULT_GATEWAY, AliPayParams.AppID,
				AliPayParams.APP_PRIVATE_KEY, AliPayParams.DATA_TYPE, AliPayParams.CHARSET,
				AliPayParams.ALIPAY_PUBLIC_KEY, AliPayParams.SIGN_TYPE);

		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		// 请保证OutTradeNo值每次保证唯一
		model.setOutTradeNo("2017090080001939235");
		model.setTimeoutExpress("30m");
		model.setTotalAmount("0.01");
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);

		request.setNotifyUrl("http://www.ttxhwlx.com/index.html");

		AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		System.out.println(response.getBody());

		// 调用成功，则处理业务逻辑
		if (response.isSuccess()) {
			//

		}
	}

	/**
	 * @Title: tradeWapPay
	 * @Description: T支付宝网页下单
	 * @param ucode
	 * @param order_no
	 * @param httpRequest
	 * @param httpResponse
	 * @throws ServletException
	 * @throws IOException
	 * @throws AlipayApiException
	 * @return:void
	 * @author: brandon
	 * @date:  2018年9月13日
	 */
//	@PostMapping(value = "/trade_wap_pay")
	public void tradeWapPay(String ucode, String order_no, HttpServletRequest httpRequest, HttpServletResponse httpResponse)
											throws ServletException, IOException, AlipayApiException {
		String eventId = new Date().getTime()+""; //唯一事件码
		Map params = new HashMap<String, Object>();
		logger.info(eventId + "====开始检查系统操作权限==订单微信支付====");

		//请求下单
		String form = aliPayService.tradeWapPayRequest(params);
		
		httpResponse.setContentType("text/html;charset=" + AliPayParams.CHARSET);
		httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
		httpResponse.getWriter().flush();
	}
}
