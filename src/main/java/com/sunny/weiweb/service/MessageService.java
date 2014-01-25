/**
 * 
 */
package com.sunny.weiweb.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.weiweb.message.Code;
import com.sunny.weiweb.message.RequestMessage;
import com.sunny.weiweb.message.ResponseMessage;

/**
 * 
 * 消息应答服务
 * 
 * Create on Jan 23, 2014 10:18:10 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class MessageService {

	private Code coder = new Code();

	private Logger logger = LoggerFactory.getLogger(getClass());

	public String process(HttpServletRequest request) throws IOException, DocumentException {
		RequestMessage req = coder.decode(request);
		if (req == null) {
			logger.info("request message is null.");
			return "";
		}
		logger.info(req.toString());
		ResponseMessage resp = new ResponseMessage();
		resp.setContent(req.getContent());
		resp.setCreateTime(new Date().getTime());
		resp.setFromUserName(req.getToUserName());
		resp.setMsgType(req.getMsgType());
		resp.setToUserName(req.getFromUserName());
		return coder.encode(resp);
	}

}
