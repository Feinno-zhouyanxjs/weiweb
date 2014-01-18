package com.sunny.weiweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunny.weiweb.utils.SignUtil;

@Controller
public class CoreController {

	@ResponseBody
	@RequestMapping(value = "/Core.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String getString(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return "";
	}

	@RequestMapping(value = "/toJsp.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String toJsp(HttpServletRequest request, HttpServletResponse response) {
		return "snoop.jsp";
	}
}
