package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;
/**
 * This is a Javabean for the heading part of Authenticated Flyout Component
 * 
 * @author Ajay Pandey
 * 
 */
public class FlyoutBean {
	/**
	 * Default Constructor
	 */
	public FlyoutBean() {
		super();
	}
	/**
	 * Heading field in the flyout component
	 */
	@SerializedName("title")
	private String heading;
	/**
	 * Target link in the flyout component
	 */

	@SerializedName("targetlinks")
	private String linkurl;
	
	@SerializedName("openurlnewwindow")
	private Boolean openLegalLinkURLInNewWindow;

	@SerializedName ("nofollow")
	private Boolean seoNoFollow;
	/**
	 * This method is used to get the value of 
	 * the Heading field.
	 * @return Gets the linkurl for the flyout
	 */
	public String getLinkurl() {
		return linkurl;
	}
	/**
	 * This method is used to set the value of 
	 * the linkurl field.
	 *@param linkurl 
	 *				the linkurl for the flyout
	 */
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	/**
	 * This method is used to get the value of 
	 * the Heading field.
	 * @return Gets the heading for the flyout
	 */
	public String getHeading() {
		return heading;
	}
	/**
	 * This method is used to set the value of 
	 * the Heading field.
	 *@param heading 
	 *				the heading for the flyout
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	
	/**
	 * This method is used to get the Open Legal Link URL 
	 * in new window fields in the footer
	 * 
	 * @return Boolean
	 */
	public Boolean getOpenLegalLinkURLInNewWindow() {
		return openLegalLinkURLInNewWindow;
	}
	/**
	 * This method is used to set the Open Legal Link URL 
	 * in new window fields in the footer
	 * 
	 * @param openLegalLinkURLInNewWindow
	 */
	public void setOpenLegalLinkURLInNewWindow(Boolean openLegalLinkURLInNewWindow) {
		this.openLegalLinkURLInNewWindow = openLegalLinkURLInNewWindow;
	}
	/**
	 * This method is used to get the seoNoFollow attribute
	 * @return
	 */
	public Boolean getSeoNoFollow() {
		return seoNoFollow;
	}

	/**
	 * This method is used to set the seoNoFollow attribute
	 * @param seoNoFollow
	 */
	public void setSeoNoFollow(Boolean seoNoFollow) {
		this.seoNoFollow = seoNoFollow;
	}


}

