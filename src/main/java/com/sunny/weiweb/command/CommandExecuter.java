/**
 * 
 */
package com.sunny.weiweb.command;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.pzoom.database.DBConnectionManager;
import com.pzoom.database.Database;
import com.sunny.weiweb.message.RequestText;
import com.sunny.weiweb.sys.StringConstant;

/**
 * 
 * 命令执行器
 * 
 * Create on Jan 26, 2014 12:24:07 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class CommandExecuter {

	/**
	 * 数据库操作接口
	 */
	private static Database db;

	/**
	 * 命令处理器缓存
	 */
	private static ConcurrentHashMap<String, Command> cmds = new ConcurrentHashMap<String, Command>();

	public static void init() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// 初始化命令映射表
		InputStream in = CommandExecuter.class.getClassLoader().getResourceAsStream("mapping.properties");
		Properties mapping = new Properties();
		mapping.load(in);
		in.close();

		Iterator<Entry<Object, Object>> iter = mapping.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Object, Object> entry = iter.next();
			String key = entry.getKey().toString();
			String clz = entry.getValue().toString();
			Object cmd = Class.forName(clz).newInstance();
			cmds.put(key, (Command) cmd);
		}

		db = DBConnectionManager.getInstance().getDatabase(StringConstant.DB);
	}

	public static String cmd(RequestText request) {
		String content = request.getContent();
		String[] params = content.split(" ");
		if (params.length == 0) {
			return StringConstant.InvalidCommand;
		}

		String[] args = new String[params.length - 1];
		System.arraycopy(params, 1, args, 0, args.length);

		String key = params[0];
		Command cmd = cmds.get(key);
		if (cmd == null) {
			return StringConstant.InvalidCommand;
		} else {
			cmd.init(db);
			return cmd.execute(args, request);
		}
	}

}
