package com.hertz.digital.platform.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;
/**
 * This is a Javabean for the heading part of Footer Link Component
 * 
 * @author a.dhingra
 * 
 */
public class FooterLinkBean {
	/**
	 * Default Constructor
	 */
	public FooterLinkBean() {
		super();
	}
	/**
	 * Heading field in the footer links component
	 */
	@SerializedName("title")
	private String heading;
	
	/**
	 *List of all the footer sub links properties 
	 */
	@SerializedName("links")
	private List<FooterDetailBean> footerDetailBean;
	
	/**
	 * This method is used to get the value of 
	 * the Heading field.
	 */
	public String getHeading() {
		return heading;
	}
	/**
	 * This method is used to set the value of 
	 * the Heading field.
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}
	/**
	 * This method is used to get the
	 * list of all properties of the 
	 * footer sub links
	 */
	public List<FooterDetailBean> getFooterDetailBean() {
		return footerDetailBean;
	}
	/**
	 * This method is used to set the
	 * list of all properties of the 
	 * footer sub links
	 */
	public void setFooterDetailBean(List<FooterDetailBean> footerDetailBean) {
		this.footerDetailBean = footerDetailBean;
	}

}

