/**
 * 
 */
package com.sunny.weiweb.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 字符串工具类
 * 
 * Create on Feb 8, 2014 10:17:16 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class SysUtils {

	private static Logger logger = LoggerFactory.getLogger(SysUtils.class);

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String toMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			logger.error("", e);
			return null;
		}

	}

}
