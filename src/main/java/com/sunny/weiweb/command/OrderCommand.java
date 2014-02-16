/**
 * 
 */
package com.sunny.weiweb.command;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzoom.database.DataTable;
import com.pzoom.database.DataTransaction;
import com.pzoom.database.Database;
import com.sunny.weiweb.controller.ManagerCoreController;
import com.sunny.weiweb.message.RequestText;
import com.sunny.weiweb.sys.StringConstant;

/**
 * 
 * 当日菜单 order+空格+菜单ID(多个用逗号分隔)[+空格+备注]
 * 
 * Create on Feb 8, 2014 10:40:32 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class OrderCommand implements Command {

	private Database db;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#execute(java.lang.String[], com.sunny.weiweb.message.RequestText)
	 */
	@Override
	public String execute(String[] args, RequestText request) {
		String openID = request.getFromUserName();
		String comment = "";
		String[] ids = null;
		if (args.length > 0) {
			ids = args[0].split(",");
		} else {
			return "请提交您要吃的菜单ID";
		}

		if (args.length > 1)
			comment = args[1];

		String checkcmd = "select ID from MenuItems where MenuName = '" + ManagerCoreController.menuName + "' and ID in ";
		String ordercmd = "insert into Orders (OpenID,CreatedTime,Comment) values (?,now(),?)";
		String itemscmd = "insert into OrderItems (OrderID,MenuItemID) values (?,?)";

		String checkids = "(";
		for (int i = 0; i < ids.length; i++) {
			checkids += ids[i] + ",";
		}
		checkids = checkids.substring(0, checkids.length() - 1);
		checkids += ")";

		try {
			DataTable dt = db.executeQuery(checkcmd + checkids);
			if (dt.getRowCount() == 0)
				return "请输入正确的菜单ID";
		} catch (SQLException e2) {
			logger.error("", e2);
			return StringConstant.InternalError;
		}

		DataTransaction trans = db.createTransaction();
		try {
			trans.begin();

			int orderID = trans.getKeyOnExecuted(ordercmd, openID, comment);
			for (int i = 0; i < ids.length; i++) {
				trans.executeUpdate(itemscmd, orderID, ids[i]);
			}

			trans.commit();
		} catch (Exception e) {
			try {
				trans.rollback();
			} catch (SQLException e1) {
				logger.error("", e1);
			}
			logger.error("", e);
			return StringConstant.InternalError;
		}

		return "下单成功";
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

}
