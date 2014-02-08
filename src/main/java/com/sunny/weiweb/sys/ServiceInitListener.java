/**
 * 
 */
package com.sunny.weiweb.sys;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzoom.database.DBConnectionManager;
import com.pzoom.database.Database;
import com.sunny.weiweb.command.CommandExecuter;

/**
 * 
 * 服务启动初始化
 * 
 * Create on Jan 26, 2014 1:02:44 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class ServiceInitListener implements ServletContextListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			// 初始化数据库连接池
			InputStream in = CommandExecuter.class.getClassLoader().getResourceAsStream("db_config.xml");
			Properties dbprop = new Properties();
			dbprop.loadFromXML(in);
			DBConnectionManager.getInstance().getDatabase(StringConstant.DB, dbprop);

			CommandExecuter.init();
		} catch (Exception e) {
			logger.error("failed to init service. ", e);
		}

	}

}
