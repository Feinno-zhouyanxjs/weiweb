package com.sunny.weiweb.command;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
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
		String usercmd = "SELECT " + "MenuItems.ItemName Menus, " + "COUNT(MenuItems.ItemName) count, " + "	SUM(MenuItems.Price) Price " + "FROM " + "Orders " + "INNER JOIN OrderItems ON Orders.ID = OrderItems.OrderID " + "LEFT JOIN Users ON Orders.OpenID = Users.OpenID "
				+ "LEFT JOIN MenuItems ON OrderItems.MenuItemID = MenuItems.ID " + "WHERE " + "to_days(Orders.CreatedTime)= to_days(now()) " + "AND Orders.OpenID = ? " + "GROUP BY " + "MenuItems.ItemName ";

		try {
			DataTable userDataTable = db.executeQuery(usercmd.toString(), request.getFromUserName());
			if (userDataTable.getRowCount() > 0 && !StringUtils.isEmpty(userDataTable.getRow(1).getString("Menus"))) {
				StringBuffer sb = new StringBuffer();
				sb.append("当天订单:\r\n");
				double sum = 0;
				for (int i = 0; i < userDataTable.getRowCount(); i++) {
					DataRow dr = userDataTable.getRow(i + 1);
					sb.append(dr.getString("Menus") + "*" + dr.getString("count") + " " + dr.getString("Price") + "\r\n");
					sum += dr.getDouble("Price");
				}
				sb.append("合计：" + sum);
				return sb.toString();
			} else {
				return "当天暂无订单";
			}
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
