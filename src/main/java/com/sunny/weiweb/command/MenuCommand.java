/**
 * 
 */
package com.sunny.weiweb.command;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzoom.database.DataRow;
import com.pzoom.database.DataTable;
import com.pzoom.database.Database;
import com.sunny.weiweb.controller.ManagerCoreController;
import com.sunny.weiweb.message.RequestText;
import com.sunny.weiweb.sys.StringConstant;

/**
 * 
 * 菜单 menu
 * 
 * Create on Feb 8, 2014 10:39:44 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class MenuCommand implements Command {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Database db;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#execute(java.lang.String[], com.sunny.weiweb.message.RequestText)
	 */
	@Override
	public String execute(String[] args, RequestText request) {
		try {
			return getMenu(db);
		} catch (SQLException e) {
			logger.error("", e);
			return StringConstant.InternalError;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#init(com.pzoom.database.Database)
	 */
	@Override
	public void init(Database db) {
		this.db = db;

	}

	public static String getMenu(Database db) throws SQLException {
		DataTable dt = null;
		dt = db.executeQuery("select ID,ItemName,Price from MenuItems where MenuName = ?", ManagerCoreController.menuName);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getRow(i + 1);
			String id = dr.getString("ID");
			String itemName = dr.getString("ItemName");
			String price = dr.getString("Price");
			sb.append(id + " " + itemName + " " + price + "\r\n");
		}
		return sb.toString();
	}

}
