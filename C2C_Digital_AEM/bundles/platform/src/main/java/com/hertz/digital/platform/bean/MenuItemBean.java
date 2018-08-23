package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * JavaBean for Menu Item in HeaderBean.
 * 
 * @author puneet.soni
 * 
 */
public class MenuItemBean {
	/**
	 * Default Constructor
	 */
	public MenuItemBean() {
		super();
	}

	/**
	 * Java Bean for the Heading section for Menu Item
	 */
	@SerializedName("heading")
	private HeadingBean heading;

	/**
	 * Getter for the Heading
	 * 
	 * @return HeadingBean
	 */
	public HeadingBean getHeading() {
		return heading;
	}

	/**
	 * setter for the Heading
	 * 
	 * @param heading
	 *            Object of HeadingBean
	 */
	public void setHeading(HeadingBean heading) {
		this.heading = heading;
	}

}
