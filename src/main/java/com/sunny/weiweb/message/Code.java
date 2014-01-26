/**
 * 
 */
package com.sunny.weiweb.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * 消息编码解码器
 * 
 * Create on Jan 23, 2014 10:20:44 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class Code {

	public String encode(ResponseText resp) throws IOException {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		root.addElement("ToUserName").addCDATA(resp.getToUserName());
		root.addElement("FromUserName").addCDATA(resp.getFromUserName());
		root.addElement("CreateTime").setText(resp.getCreateTime() + "");
		root.addElement("MsgType").addCDATA(resp.getMsgType());
		root.addElement("Content").addCDATA(resp.getContent());
		ByteArrayOutputStream strout = new ByteArrayOutputStream();
		XMLWriter writer = new XMLWriter(strout);
		writer.write(document);
		writer.flush();
		writer.close();
		return new String(strout.toByteArray());
	}

	public Request decode(HttpServletRequest request) throws IOException, DocumentException {
		InputStream in = request.getInputStream();
		// FileInputStream in = new FileInputStream("/Users/sunny/Desktop/request");
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(in);
		in.close();
		Element root = document.getRootElement();

		String type = root.elementText("MsgType");
		String toUserName = root.elementText("ToUserName");
		String fromUserName = root.elementText("FromUserName");
		String createTime = root.elementText("CreateTime");
		String msgId = root.elementText("MsgId");

		Request msg = null;

		if (type.equals(MessageType.text.name())) {
			msg = new RequestText();
			msg.setMsgType(type);
			RequestText rt = (RequestText) msg;
			rt.setContent(root.elementText("Content"));
			rt.setMsgId(Long.valueOf(msgId));
		} else if (type.equals(MessageType.event.name())) {
			String event = root.elementText("Event");
			if (event.equals(EventType.subscribe.name())) {
				msg = new RequestSubscribe();
				msg.setMsgType(type);
				RequestSubscribe sr = (RequestSubscribe) msg;
				sr.setEvent(event);
			} else {
				return null;
			}
		} else {
			return null;
		}
		msg.setCreateTime(Long.valueOf(createTime));
		msg.setFromUserName(fromUserName);
		msg.setToUserName(toUserName);
		return msg;
	}
}
