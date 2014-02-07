/**
 * 
 */
package com.sunny.weiweb.command;

import com.pzoom.database.Database;
import com.sunny.weiweb.message.RequestText;

/**
 * 
 * 帮助
 * 
 * Create on Jan 26, 2014 1:08:50 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class HelpCommand implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#execute(java.lang.String[])
	 */
	@Override
	public String execute(String[] args, RequestText request) {
		return "功能菜单如下:\r\n1.发送HELP获取功能菜单\r\n2.发送MENU获取当日午餐菜单\r\n3.发送ORDER获取当日订单状态";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.weiweb.command.Command#init(com.pzoom.database.Database)
	 */
	@Override
	public void init(Database db) {
	}

}
