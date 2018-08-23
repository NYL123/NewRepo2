package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * This is a Javabean for the Footer Component properties
 * 
 * @author a.dhingra
 * 
 */
public class LegalLinksBean {
	/**
	 * Default Constructor
	 */
	public LegalLinksBean(){
		super();	
	}
	
	/**
	 * Legal Link Label field set in the footer
	 */
	@SerializedName("title")
	private String legalLinkLabel;
	/**
	 * Legal Link URL field set in the footer
	 */
	@SerializedName("url")
	private String legalLinkURL;
	/**
	 * Open Legal Link URL In New Window field set in the footer
	 */
	@SerializedName("openurlnewwindow")
	private Boolean openLegalLinkURLInNewWindow;

	@SerializedName ("nofollow")
	private Boolean seoNoFollow;

	/**
	 * This method is used to get the Legal Link Labels
	 * set in the footer
	 * 
	 * @return String
	 */
	public String getLegalLinkLabel() {
		return legalLinkLabel;
	}
	/**
	 * This method is used to set the Legal Link Labels
	 * set in the footer
	 * 
	 * @param legalLinkLabel
	 */
	public void setLegalLinkLabel(String legalLinkLabel) {
		this.legalLinkLabel = legalLinkLabel;
	}
	/**
	 * This method is used to get the Legal Link URL's
	 * set in the footer
	 * 
	 * @return String
	 */
	public String getLegalLinkURL() {
		return legalLinkURL;
	}
	/**
	 * This method is used to set the Legal Link URL's
	 * set in the footer
	 * 
	 * @param legalLinkURL
	 */
	public void setLegalLinkURL(String legalLinkURL) {
		this.legalLinkURL = legalLinkURL;
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

	public Boolean getSeoNoFollow() {
		return seoNoFollow;
	}

	public void setSeoNoFollow(Boolean seoNoFollow) {
		this.seoNoFollow = seoNoFollow;
	}
}
