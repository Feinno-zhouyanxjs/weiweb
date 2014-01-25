package com.sunny.weiweb.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunny.weiweb.service.MessageService;
import com.sunny.weiweb.utils.SignUtil;

@Controller
public class CoreController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private MessageService msgService = new MessageService();

	@ResponseBody
	@RequestMapping(value = "/Core.do", method = RequestMethod.GET)
	public String sign(HttpServletRequest request, HttpServletResponse response) {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		logger.info(signature + "-" + timestamp + "-" + nonce + "-" + echostr);
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			logger.info("sign success....");
			return echostr;
		}
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/Core.do", method = RequestMethod.POST)
	public String message(HttpServletRequest request, HttpServletResponse response) {
		logger.info("received a message request.....");
		try {
			String res = msgService.process(request);
			logger.info(res);
			return res;
		} catch (IOException | DocumentException e) {
			logger.error("process message. ", e);
		}
		return "";
	}

	@RequestMapping(value = "/Snoop.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String snoop(HttpServletRequest request, HttpServletResponse response) {
		return "snoop.jsp";
	}
}
