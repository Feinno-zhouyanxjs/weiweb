/**
 * 
 */
package com.sunny.weiweb.message;

/**
 * 
 * 文本请求
 * 
 * Create on Jan 23, 2014 9:46:02 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class RequestText extends Request {

	private String content;

	private long msgId;

	/**
	 * @return the msgId
	 */
	public long getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId
	 *            the msgId to set
	 */
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestText [content=" + content + ", msgId=" + msgId + ", getToUserName()=" + getToUserName() + ", getFromUserName()=" + getFromUserName() + ", getCreateTime()=" + getCreateTime() + ", getMsgType()=" + getMsgType() + "]";
	}

}
