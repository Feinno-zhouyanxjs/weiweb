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

	public String encode(ResponseMessage resp) throws IOException {
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

	public RequestMessage decode(HttpServletRequest request) throws IOException, DocumentException {
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
		if (type.equals("text")) {
			RequestMessage msg = new RequestMessage();
			msg.setContent(root.elementText("Content"));
			msg.setCreateTime(Long.valueOf(createTime));
			msg.setFromUserName(fromUserName);
			msg.setMsgId(Long.valueOf(msgId));
			msg.setToUserName(toUserName);
			msg.setMsgType("text");
			return msg;
		} else {
			return null;
		}
	}
}
