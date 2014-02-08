/**
 * 
 */
package com.sunny.weiweb.controller;

import com.pzoom.database.DBConnectionManager;
import com.pzoom.database.Database;
import com.sunny.weiweb.sys.StringConstant;

/**
 * 
 * 管理界面控制器
 * 
 * Create on Feb 8, 2014 9:42:32 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class ManagerCoreController {

	private Database db = DBConnectionManager.getInstance().getDatabase(StringConstant.DB);
	
	
}
