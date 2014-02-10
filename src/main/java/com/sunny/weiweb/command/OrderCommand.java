/**
 * 
 */
package com.sunny.weiweb.command;

import com.pzoom.database.Database;
import com.sunny.weiweb.message.RequestText;

/**
 * 
 * 当日菜单 order
 * 
 * Create on Feb 8, 2014 10:40:32 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class OrderCommand implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#execute(java.lang.String[], com.sunny.weiweb.message.RequestText)
	 */
	@Override
	public String execute(String[] args, RequestText request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#init(com.pzoom.database.Database)
	 */
	@Override
	public void init(Database db) {
		// TODO Auto-generated method stub

	}

}
