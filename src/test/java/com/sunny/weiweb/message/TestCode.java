/**
 * 
 */
package com.sunny.weiweb.message;

import java.io.IOException;
import java.util.Date;

import org.dom4j.DocumentException;
import org.junit.Test;

/**
 * 
 * 
 * Create on Jan 24, 2014 9:43:22 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class TestCode {

	@Test
	public void testDecode() throws IOException, DocumentException {
		Code code = new Code();
		RequestMessage rm = code.decode(null);
		System.out.println(rm);

		ResponseMessage rem = new ResponseMessage();
		rem.setContent("1122");
		rem.setCreateTime(new Date().getTime());
		rem.setFromUserName("zhouyan");
		rem.setMsgType("text");
		rem.setToUserName("bingbing");
		System.out.println(code.encode(rem));
	}
}
