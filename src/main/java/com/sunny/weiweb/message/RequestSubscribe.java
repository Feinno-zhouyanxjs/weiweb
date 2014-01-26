/**
 * 
 */
package com.sunny.weiweb.message;

/**
 * 
 * 订阅请求
 * 
 * Create on Jan 26, 2014 11:43:21 AM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class RequestSubscribe extends RequestEvent {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubscribeRequest [getEvent()=" + getEvent() + ", getToUserName()=" + getToUserName() + ", getFromUserName()=" + getFromUserName() + ", getCreateTime()=" + getCreateTime() + ", getMsgType()=" + getMsgType() + "]";
	}

}
