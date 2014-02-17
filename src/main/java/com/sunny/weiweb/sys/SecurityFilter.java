/**
 * 
 */
package com.sunny.weiweb.sys;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 安全登陆过滤器
 * 
 * Create on 2014-2-17 上午10:28:22
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class SecurityFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String reqUri = ((HttpServletRequest) request).getRequestURI();
		Object user = ((HttpServletRequest) request).getSession().getAttribute("user");
		String cp = ((HttpServletRequest) request).getContextPath();
		if ((user != null && !StringUtils.isEmpty(user.toString())) || reqUri.endsWith("Login.do")) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendRedirect(cp + "/login.jsp");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
