/**
 * 
 */
package com.sunny.weiweb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pzoom.database.DBConnectionManager;
import com.pzoom.database.DataRow;
import com.pzoom.database.DataTable;
import com.pzoom.database.Database;
import com.pzoom.database.PrepareBatch;
import com.sunny.weiweb.sys.StringConstant;
import com.sunny.weiweb.utils.SysUtils;

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

	public static String menuName = "午餐";

	@RequestMapping(value = "/manager/Login.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if (userName == null || userName.equals("") || password == null || password.equals("")) {
			request.setAttribute("status", "用户名密码不能为空");
			return "login.jsp";
		}

		String cmd = "select UserName from Manager where UserName=? and Password=?";
		try {
			String pw = SysUtils.toMD5(password);
			DataTable dt = db.executeQuery(cmd, userName, pw);
			if (dt.getRowCount() == 0) {
				request.setAttribute("status", "用户名或密码不正确");
				return "login.jsp";
			}
		} catch (SQLException e) {
			logger.error("", e);
		}

		request.getSession().setAttribute("user", userName);
		return "redirect:main.html";
	}

	@RequestMapping(value = "/manager/Logout.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		request.getSession().removeAttribute("user");
		return "redirect:/login.jsp";
	}

	@RequestMapping(value = "/manager/Password.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String password(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		String oldpwd = request.getParameter("oldpassword");
		String newpwd = request.getParameter("newpassword");
		String newpwd2 = request.getParameter("newpassword2");
		if (StringUtils.isEmpty(oldpwd) || StringUtils.isEmpty(newpwd) || StringUtils.isEmpty(newpwd2)) {
			request.setAttribute("status", "密码不能为空");
			return "manager/password.jsp";
		}

		if (!newpwd.equals(newpwd2)) {
			request.setAttribute("status", "两次密码输入一致");
			return "manager/password.jsp";
		}

		String cmd = "update Manager set Password=? where UserName=? and Password=?";
		try {
			db.executeUpdate(cmd, SysUtils.toMD5(newpwd), request.getSession().getAttribute("user"), SysUtils.toMD5(oldpwd));
		} catch (SQLException e) {
			logger.error("", e);
		}
		request.setAttribute("status", "修改成功");
		return "manager/password.jsp";
	}

	@RequestMapping(value = "/manager/SetMenu.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String setMenu(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		String menu = request.getParameter("menu");
		if (StringUtils.isEmpty(menu)) {
			request.setAttribute("status", "菜单内容不能为空");
			return "manager/menu.jsp";
		}

		String menuCmd = "update Menus set CreatedTime = now() where MenuName = ?";
		try {
			db.getKeyOnExecuted(menuCmd, menuName);
		} catch (SQLException e) {
			logger.error("", e);
			request.setAttribute("status", "更新失败");
			return "manager/menu.jsp";
		}

		String rmcmd = "delete from MenuItems";
		try {
			db.executeUpdate(rmcmd);
		} catch (SQLException e) {
			logger.error("", e);
			request.setAttribute("status", "清理菜单失败");
			return "manager/menu.jsp";
		}

		StringReader sr = new StringReader(menu);
		BufferedReader br = new BufferedReader(sr);
		try {
			PrepareBatch pb = db.createPrepareBatch("insert into MenuItems (MenuName,ItemName,Price) values (?,?,?)");
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] items = line.split(" ");
				pb.addParams(menuName, items[0], items[1]);
			}
			pb.executeUpdate();
		} catch (IOException e) {
			logger.error("", e);
			request.setAttribute("status", "解析菜单内容失败");
			return "manager/menu.jsp";
		} catch (SQLException e) {
			logger.error("", e);
			request.setAttribute("status", "解析菜单内容失败");
			return "manager/menu.jsp";
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("", e);
				request.setAttribute("status", "IO异常");
				return "manager/menu.jsp";
			}
		}

		request.setAttribute("status", "成功");
		request.setAttribute("menu", menu);
		return "manager/menu.jsp";
	}

	@RequestMapping(value = "/manager/GetMenu.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String getMenu(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		String content = "";
		try {
			DataTable dt = null;
			dt = db.executeQuery("select ItemName,Price from MenuItems where MenuName = ?", ManagerCoreController.menuName);

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataRow dr = dt.getRow(i + 1);
				String itemName = dr.getString("ItemName");
				String price = dr.getString("Price");
				sb.append(itemName + " " + price + "\r\n");
			}
			content = sb.toString();
		} catch (SQLException e) {
			logger.error("", e);
			request.setAttribute("status", "查询菜单信息错误");
		}

		request.setAttribute("menu", content);
		return "manager/menu.jsp";
	}

	@RequestMapping(value = "/manager/GetTodayOrder.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTodayOrder(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		StringBuffer usercmd = new StringBuffer();
		// usercmd.append("SELECT ");
		// usercmd.append("Users.Alias, ");
		// usercmd.append("group_concat(OrderItems.MenuItemName) Menus, ");
		// usercmd.append("SUM(MenuItems.Price) Price, ");
		// usercmd.append("Orders.CreatedTime, ");
		// usercmd.append("Orders.`Comment` ");
		// usercmd.append("FROM ");
		// usercmd.append("Orders ");
		// usercmd.append("LEFT JOIN OrderItems ON Orders.ID = OrderItems.OrderID ");
		// usercmd.append("LEFT JOIN Users ON Orders.OpenID = Users.OpenID ");
		// usercmd.append("LEFT JOIN MenuItems ON OrderItems.MenuItemName = MenuItems.ItemName ");
		// usercmd.append("WHERE to_days(Orders.CreatedTime) = to_days(now()) ");
		// usercmd.append("GROUP BY Users.Alias ");

		usercmd.append("SELECT ");
		usercmd.append("Users.Alias, ");
		usercmd.append("group_concat(MenuItems.ItemName) AS Menus, ");
		usercmd.append("Sum(MenuItems.Price) AS Price, ");
		usercmd.append("Orders.CreatedTime, ");
		usercmd.append("Orders.`Comment` ");
		usercmd.append("FROM ");
		usercmd.append("Orders ");
		usercmd.append("LEFT JOIN OrderItems ON Orders.ID = OrderItems.OrderID ");
		usercmd.append("LEFT JOIN Users ON Orders.OpenID = Users.OpenID ");
		usercmd.append("LEFT JOIN MenuItems ON OrderItems.MenuItemID = MenuItems.ID ");
		usercmd.append("WHERE to_days(Orders.CreatedTime) = to_days(now()) ");
		usercmd.append("GROUP BY Users.Alias ");

		// 统计菜品数量
		// String usercmd = "SELECT " + "Users.Alias, " + "	MenuItems.ItemName AS Menus, " + "COUNT(MenuItems.ItemName) AS count, " + "	Sum(MenuItems.Price)AS Price, " + "Orders.CreatedTime, " + "	Orders.`Comment` " + "FROM " + "Orders "
		// + "INNER JOIN OrderItems ON Orders.ID = OrderItems.OrderID " + "LEFT JOIN Users ON Orders.OpenID = Users.OpenID " + "LEFT JOIN MenuItems ON OrderItems.MenuItemID = MenuItems.ID " + "WHERE " + "to_days(Orders.CreatedTime)= to_days(now()) " + "GROUP BY "
		// + "Users.Alias,MenuItems.ItemName ";

		try {
			DataTable userDataTable = db.executeQuery(usercmd.toString());
			request.setAttribute("userDataTable", userDataTable);
		} catch (SQLException e) {
			logger.error("", e);
			request.setAttribute("status", "查询订单信息错误");
			return "manager/orders.jsp";
		}

		StringBuffer menucmd = new StringBuffer();
		menucmd.append("SELECT ");
		menucmd.append("MenuItems.ItemName, ");
		menucmd.append("SUM(MenuItems.Price) Price ");
		menucmd.append("FROM ");
		menucmd.append("OrderItems ");
		menucmd.append("LEFT JOIN MenuItems ON OrderItems.MenuItemID = MenuItems.ID ");
		menucmd.append("LEFT JOIN Orders ON Orders.ID = OrderItems.OrderID ");
		menucmd.append("WHERE to_days(Orders.CreatedTime) = to_days(now()) ");
		menucmd.append("GROUP BY MenuItems.ItemName ");
		try {
			DataTable foodDataTable = db.executeQuery(menucmd.toString());
			request.setAttribute("foodDataTable", foodDataTable);
		} catch (SQLException e) {
			logger.error("", e);
			request.setAttribute("status", "查询订单信息错误");
			return "manager/orders.jsp";
		}

		return "manager/orders.jsp";
	}

}
