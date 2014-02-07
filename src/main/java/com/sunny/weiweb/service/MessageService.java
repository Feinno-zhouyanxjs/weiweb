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

import com.sunny.weiweb.command.CommandExecuter;
import com.sunny.weiweb.message.Code;
import com.sunny.weiweb.message.EventType;
import com.sunny.weiweb.message.MessageType;
import com.sunny.weiweb.message.Request;
import com.sunny.weiweb.message.RequestEvent;
import com.sunny.weiweb.message.RequestText;
import com.sunny.weiweb.message.ResponseText;

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
		Request req = coder.decode(request);
		if (req == null) {
			logger.info("request message is null.");
			return "";
		}
		logger.info(req.toString());

		ResponseText resp = new ResponseText();
		resp.setCreateTime(new Date().getTime());
		resp.setFromUserName(req.getToUserName());
		resp.setMsgType(MessageType.text.name());
		resp.setToUserName(req.getFromUserName());

		if (req.getMsgType().equals(MessageType.text.name())) {
			RequestText reqtext = (RequestText) req;
			resp.setContent(CommandExecuter.cmd(reqtext));
			return coder.encode(resp);
		} else if (req.getMsgType().equals(MessageType.event.name())) {
			RequestEvent reqEvent = (RequestEvent) req;
			if (reqEvent.getEvent().equals(EventType.subscribe.name())) {
				resp.setContent("欢迎关注品众小助手,请使用命令 alias+空格+姓名 绑定账号后使用更多功能.");
				return coder.encode(resp);
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

}
