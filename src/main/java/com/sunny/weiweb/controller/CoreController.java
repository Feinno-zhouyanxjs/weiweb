package com.sunny.weiweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunny.weiweb.utils.SignUtil;

@Controller
public class CoreController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "/Core.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String getString(HttpServletRequest request, HttpServletResponse response) {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		logger.debug(signature + "-" + timestamp + "-" + nonce + "-" + echostr);
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			logger.debug("sign success....");
			return echostr;
		}
		return "";
	}

	@RequestMapping(value = "/toJsp.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String toJsp(HttpServletRequest request, HttpServletResponse response) {
		return "snoop.jsp";
	}
}
