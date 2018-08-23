package com.hertz.digital.platform.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * This is a Javabean for the Link part of the Column Heading Component
 * 
 * @author a.dhingra, puneet.soni
 * 
 */
public class FooterDetailBean {

	/**
	 * Field for Linktext URl
	 */
	@SerializedName("linktext")
	@Expose
	private String linktext;

	/**
	 * String for the linkurl
	 */
	@SerializedName("linkurl")
	@Expose
	private String linkurl;

	/**
	 * String for the rel
	 */
	@SerializedName("rel")
	@Expose
	private String rel;

	/**
	 * String for the targetwindow
	 */
	@SerializedName("targetwindow")
	@Expose
	private String targetwindow;

	/**
	 * Boolean value for the displayDesktopLink
	 */
	@SerializedName("displayDesktopLink")
	@Expose
	private Boolean displayDesktopLink;

	/**
	 * Boolean value for the displayTabletLink
	 */
	@SerializedName("displayTabletLink")
	@Expose
	private Boolean displayTabletLink;

	/**
	 * Boolean value for the displayMobileLink
	 */
	@SerializedName("displayMobileLink")
	@Expose
	private Boolean displayMobileLink;

	/**
	 * Boolean value for the displayAppLink
	 */
	@SerializedName("displayAppLink")
	@Expose
	private Boolean displayAppLink;

	/**
	 * Default Constructor
	 */
	public FooterDetailBean() {
		super();
	}

	/**
	 * Getter for the linktext
	 * 
	 * @return String for linktext
	 */
	public String getLinktext() {
		return linktext;
	}

	/**
	 * Setter for the linktext
	 * 
	 * @param linktext
	 *            String for the linktext
	 */
	public void setLinktext(String linktext) {
		this.linktext = linktext;
	}

	/**
	 * Getter for the linkurl
	 * 
	 * @return String for the linkurl
	 */
	public String getLinkurl() {
		return linkurl;
	}

	/**
	 * Setter for linkurl
	 * 
	 * @param linkurl
	 *            String for linkurl
	 */
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	/**
	 * Getter for Rel
	 * 
	 * @return String for rel
	 */
	public String getRel() {
		return rel;
	}

	/**
	 * Setter for rel
	 * 
	 * @param rel
	 *            String for rel
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}

	/**
	 * Getter for the targetwindow
	 * 
	 * @return String for targetwindow
	 */
	public String getTargetwindow() {
		return targetwindow;
	}

	/**
	 * Setter for the targetwindow
	 * 
	 * @param targetwindow
	 *            String for targetwindow
	 */
	public void setTargetwindow(String targetwindow) {
		this.targetwindow = targetwindow;
	}

	/**
	 * Getter for the displayDesktopLink
	 * 
	 * @return Boolean value for the displayDesktopLink
	 */
	public Boolean getDisplayDesktopLink() {
		return displayDesktopLink;
	}

	/**
	 * Setter for the displayDesktopLink
	 * 
	 * @param displayDesktopLink
	 *            Boolean value
	 */
	public void setDisplayDesktopLink(Boolean displayDesktopLink) {
		this.displayDesktopLink = displayDesktopLink;
	}

	/**
	 * Getter for the displayTabletLink
	 * 
	 * @return Boolean for the displayTabletLink
	 */
	public Boolean getDisplayTabletLink() {
		return displayTabletLink;
	}

	/**
	 * Setter for the displayTabletLink
	 * 
	 * @param displayTabletLink
	 *            Boolean value of the displayTabletLink
	 */
	public void setDisplayTabletLink(Boolean displayTabletLink) {
		this.displayTabletLink = displayTabletLink;
	}

	/**
	 * Getter for the displayMobileLink
	 * 
	 * @return Boolean value displayMobileLink
	 */
	public Boolean getDisplayMobileLink() {
		return displayMobileLink;
	}

	/**
	 * Setter for the displayMobileLink
	 * 
	 * @param displayMobileLink
	 *            Boolean value
	 */
	public void setDisplayMobileLink(Boolean displayMobileLink) {
		this.displayMobileLink = displayMobileLink;
	}

	/**
	 * Getter for the displayAppLink
	 * 
	 * @return Boolean value of displayAppLink
	 */
	public Boolean getDisplayAppLink() {
		return displayAppLink;
	}

	/**
	 * Setter for the Bollean value displayAppLink
	 * 
	 * @param displayAppLink
	 *            Boolean value
	 */
	public void setDisplayAppLink(Boolean displayAppLink) {
		this.displayAppLink = displayAppLink;
	}

}
