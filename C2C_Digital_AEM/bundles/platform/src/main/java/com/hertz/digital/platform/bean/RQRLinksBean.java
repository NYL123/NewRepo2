package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;
/**
 * Bean to store the RQR Links text and href
 * @author himanshu.i.sharma
 */
public class RQRLinksBean {
	
	/**
	 * Default Constructor
	 */
	public RQRLinksBean() {
		super();
	}

	/**
	 * Stores the link name
	 */
	@SerializedName("linkName")
	private String linkName;
	
	/**
	 *  stores the link url value
	 */
	@SerializedName("linkURL")
	private String linkURL;
	
	/**
	 *  stores the link alt text value
	 */
	@SerializedName("linkAltText")
	private String linkAltText;

	/**
	 * get the link text
	 * @return String that contains the link text
	 */
	public String getLinkName() {
		return linkName;
	}
	
	/**
	 * set the link text
	 * @param linkText that contains the link text
	 */
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	
	/**
	 * get the link text
	 * @return String that contains the link text
	 */
	public String getLinkURL() {
		return linkURL;
	}
	
	/**
	 * set the link text
	 * @param linkText that contains the link text
	 */
	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	/**
	 * get the link href
	 * @return
	 */
	public String getLinkAltText() {
		return linkAltText;
	}

	/**
	 * set the link href 
	 * @param href that contains the link href
	 */
	public void setLinkAltText(String linkAltText) {
		this.linkAltText = linkAltText;
	}
}

