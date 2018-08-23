package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;
/**
 * This is a Javabean for the multifield of Link Dropdown Component
 * 
 * @author himanshu.i.sharma
 * 
 */
public class LinkDropdownBean {
	/**
	 * Default Constructor
	 */
	public LinkDropdownBean() {
		super();
	}
	
	/**
	 * Dropdown value field in the link dropdown component
	 */
	@SerializedName("value")
	private String dropdownValue;
	
	/**
	 * Order field in the link dropdown component
	 */
	@SerializedName("order")
	private String order;
	
	/**
	 * page url field in the link dropdown component
	 */
	@SerializedName("url")
	private String pageUrl;
	
	/**
	 * This method is used to get the value of 
	 * the dropdownValue field.
	 * @return dropdownValue
	 */
	public String getDropdownValue() {
		return dropdownValue;
	}
	
	/**
	 * This method is used to get the value of 
	 * the dropdownValue field.
	 * @param dropdownValue
	 */
	public void setDropdownValue(String dropdownValue) {
		this.dropdownValue = dropdownValue;
	}
	
	/**
	 * This method is used to get the value of 
	 * the order field.
	 * @return order
	 */
	public String getOrder() {
		return order;
	}
	
	/**
	 * This method is used to set the value of 
	 * the order field.
	 * @param order
	 */
	public void setOrder(String order) {
		this.order = order;
	}
	
	/**
	 * This method is used to get the value of 
	 * the pageUrl field.
	 * @return pageUrl
	 */
	public String getUrl() {
		return pageUrl;
	}
	
	/**
	 * This method is used to set the value of 
	 * the pageUrl field.
	 * @param pageUrl
	 */
	public void setUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}

