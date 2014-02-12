package com.sunny.weiweb.command;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzoom.database.DataRow;
import com.pzoom.database.DataTable;
import com.pzoom.database.Database;
import com.sunny.weiweb.message.RequestText;
import com.sunny.weiweb.sys.StringConstant;

/**
 * 
 * list 用户订单信息
 * 
 * Create on 2014-2-12 下午1:54:45
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class BoughtListCommand implements Command {

	private Database db;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public String execute(String[] args, RequestText request) {
		StringBuffer usercmd = new StringBuffer();
		usercmd.append("SELECT ");
		usercmd.append("group_concat(OrderItems.MenuItemName) Menus, ");
		usercmd.append("SUM(MenuItems.Price) Price, ");
		// usercmd.append("Orders.CreatedTime, ");
		// usercmd.append("Orders.`Comment` ");
		usercmd.append("FROM ");
		usercmd.append("Orders ");
		usercmd.append("LEFT JOIN OrderItems ON Orders.ID = OrderItems.OrderID ");
		usercmd.append("LEFT JOIN Users ON Orders.OpenID = Users.OpenID ");
		usercmd.append("LEFT JOIN MenuItems ON OrderItems.MenuItemName = MenuItems.ItemName ");
		usercmd.append("WHERE to_days(Orders.CreatedTime) = to_days(now()) and Orders.OpenID=? ");

		try {
			DataTable userDataTable = db.executeQuery(usercmd.toString(), request.getFromUserName());
			StringBuffer sb = new StringBuffer();
			double sum = 0;
			for (int i = 0; i < userDataTable.getRowCount(); i++) {
				DataRow dr = userDataTable.getRow(i + 1);
				sb.append(dr.getString("Menus") + " " + dr.getString("Price") + "\r\n");
				sum += dr.getDouble("Price");
			}
			sb.append("合计：" + sum);
			return sb.toString();
		} catch (SQLException e) {
			logger.error("", e);
			return StringConstant.InternalError;
		}
	}

	@Override
	public void init(Database db) {
		this.db = db;

	}

}
