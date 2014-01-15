package com.sunny.weiweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@ResponseBody
	@RequestMapping(value = "/getString.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String getString(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) {
		return name;
	}

	@RequestMapping(value = "/toJsp.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String toJsp(HttpServletRequest request, HttpServletResponse response) {
		return "snoop.jsp";
	}
}
