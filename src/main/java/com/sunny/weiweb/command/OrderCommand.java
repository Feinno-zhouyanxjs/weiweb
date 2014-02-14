/**
 * 
 */
package com.sunny.weiweb.command;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzoom.database.DataTransaction;
import com.pzoom.database.Database;
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
	 * @see com.sunny.weiweb.command.Command#execute(java.lang.String[],
	 * com.sunny.weiweb.message.RequestText)
	 */
	@Override
	public String execute(String[] args, RequestText request) {
		String openID = request.getFromUserName();
		String comment = "";
		String[] ids = null;
		if (args.length > 1) {
			comment = args[1];
			ids = args[0].split(",");
		}

		String ordercmd = "insert into Orders (OpenID,CreatedTime,Comment) values (?,now(),?)";
		String itemscmd = "insert into OrderItems (OrderID,MenuItemID) values (?,?)";

		DataTransaction trans = db.createTransaction();
		try {
			trans.begin();

			int orderID = trans.getKeyOnExecuted(ordercmd, openID, comment);
			for (int i = 0; i < ids.length; i++) {
				trans.executeUpdate(itemscmd, orderID, ids[i]);
			}

			trans.commit();
		} catch (SQLException e) {
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
