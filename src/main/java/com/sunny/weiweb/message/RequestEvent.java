/**
 * 
 */
package com.sunny.weiweb.message;

/**
 * 
 * 事件基类
 * 
 * 
 * Create on Jan 26, 2014 11:10:37 AM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public abstract class RequestEvent extends Request {

	private String event;

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

}
