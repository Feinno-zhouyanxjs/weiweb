/**
 * 
 */
package com.sunny.weiweb.command;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzoom.database.Database;
import com.sunny.weiweb.message.RequestText;
import com.sunny.weiweb.sys.StringConstant;

/**
 * 
 * 注册用户别名 alias+空格+名称
 * 
 * Create on Jan 30, 2014 11:09:38 AM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class AliasCommand implements Command {

	private Database db;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#execute(java.lang.String[])
	 */
	@Override
	public String execute(String[] args, RequestText request) {
		if (args.length > 0) {
			String cmd = "replace into Users (OpenID,Alias) values (?,?)";
			try {
				int r = db.executeUpdate(cmd, request.getFromUserName(), args[0].trim());
				if (r == 1 || r == 2) {
					return "感谢" + args[0].trim() + "同学关注.如果您对我有什么意见请告诉我，我会积极改进.";
				} else {
					return StringConstant.InternalError;
				}
			} catch (SQLException e) {
				logger.error("AliasCommand. ", e);
				return StringConstant.InternalError;
			}
		} else {
			return "正确用法:alias+空格+名称";
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

}
