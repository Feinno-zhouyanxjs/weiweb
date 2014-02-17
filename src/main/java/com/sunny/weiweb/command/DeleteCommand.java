/**
 * 
 */
package com.sunny.weiweb.command;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzoom.database.DataTable;
import com.pzoom.database.Database;
import com.sunny.weiweb.message.RequestText;
import com.sunny.weiweb.sys.StringConstant;

/**
 * 
 * 删除订单del
 * 
 * Create on 2014-2-17 上午10:15:21
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class DeleteCommand implements Command {

	private Database db;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#execute(java.lang.String[], com.sunny.weiweb.message.RequestText)
	 */
	@Override
	public String execute(String[] args, RequestText request) {
		int id = 0;
		String fromUser = request.getFromUserName();
		try {
			DataTable dt = db.executeQuery("select ID from Orders where OpenID=? and to_days(Orders.CreatedTime)= to_days(now()) ", fromUser);
			if (dt.getRowCount() > 0) {
				for (int i = 0; i < dt.getRowCount(); i++) {
					id = dt.getRow(i + 1).getInt("ID");
					db.executeUpdate("delete from OrderItems where OrderID=?", id);
					db.executeUpdate("delete from Orders where ID=?", id);
				}
			}
		} catch (SQLException e) {
			logger.error("", e);
			return StringConstant.InternalError;
		}
		return "当天订单撤销成功";
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
