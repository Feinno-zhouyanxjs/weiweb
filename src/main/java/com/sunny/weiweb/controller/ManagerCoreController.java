/**
 * 
 */
package com.sunny.weiweb.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pzoom.database.DBConnectionManager;
import com.pzoom.database.DataTable;
import com.pzoom.database.Database;
import com.sunny.weiweb.sys.StringConstant;
import com.sunny.weiweb.utils.StringUtils;

/**
 * 
 * 管理界面控制器
 * 
 * Create on Feb 8, 2014 9:42:32 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
@Controller
public class ManagerCoreController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Database db = DBConnectionManager.getInstance().getDatabase(StringConstant.DB);

	@RequestMapping(value = "/Login.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if (userName.equals("") || password.equals("")) {
			request.setAttribute("status", "用户名密码不能为空");
			return "login.jsp";
		}

		String cmd = "select UserName from Manager where UserName=? and Password=?";
		try {
			String pw = StringUtils.toMD5(password);
			DataTable dt = db.executeQuery(cmd, userName, pw);
			if (dt.getRowCount() == 0) {
				request.setAttribute("status", "用户名或密码不正确");
				return "login.jsp";
			}
		} catch (SQLException e) {
			logger.error("", e);
		}

		request.getSession().setAttribute("user", userName);
		return "main.html";
	}

	@RequestMapping(value = "/Logout.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		request.getSession().removeAttribute("user");
		return "login.jsp";
	}

	@RequestMapping(value = "/Password.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String password(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		String oldpwd = request.getParameter("oldpassword");
		String newpwd = request.getParameter("newpassword");
		String newpwd2 = request.getParameter("newpassword2");
		if (oldpwd.equals("") || newpwd.equals("") || newpwd2.equals("")) {
			request.setAttribute("status", "密码不能为空");
			return "password.jsp";
		}

		if (!newpwd.equals(newpwd2)) {
			request.setAttribute("status", "两次密码输入一致");
			return "password.jsp";
		}

		String cmd = "update Manager set Password=? where UserName=? and Password=?";
		try {
			db.executeUpdate(cmd, StringUtils.toMD5(newpwd), request.getSession().getAttribute("user"), StringUtils.toMD5(oldpwd));
		} catch (SQLException e) {
			logger.error("", e);
		}
		request.setAttribute("status", "修改成功");
		return "password.jsp";
	}

	@RequestMapping(value = "/SetMenu.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String setMenu(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		return "";
	}

	@RequestMapping(value = "/GetTodayOrder.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTodayOrder(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		return "";
	}

}
