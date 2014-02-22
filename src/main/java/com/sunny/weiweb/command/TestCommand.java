/**
 * 
 */
package com.sunny.weiweb.command;

import com.pzoom.database.Database;
import com.sunny.weiweb.message.RequestText;

/**
 * 
 * 
 * Create on Feb 22, 2014 2:13:23 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class TestCommand implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#execute(java.lang.String[], com.sunny.weiweb.message.RequestText)
	 */
	@Override
	public String execute(String[] args, RequestText request) {
		return "<a href=\"http://www.baidu.com\">百度</a>";
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
