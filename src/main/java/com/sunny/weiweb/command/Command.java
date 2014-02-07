/**
 * 
 */
package com.sunny.weiweb.command;

import com.pzoom.database.Database;
import com.sunny.weiweb.message.RequestText;

/**
 * 
 * 命令接口
 * 
 * Create on Jan 26, 2014 12:22:47 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public interface Command {

	public String execute(String[] args,RequestText request);

	public void init(Database db);
}
