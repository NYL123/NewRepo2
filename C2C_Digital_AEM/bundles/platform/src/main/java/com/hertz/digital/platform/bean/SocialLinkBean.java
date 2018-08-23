package com.hertz.digital.platform.bean;

import java.util.Map;

import com.google.gson.annotations.SerializedName;
/**
 * This is a Javabean for the social link component multifield properties
 * 
 * @author a.dhingra
 * 
 */
public class SocialLinkBean {
	/**
	 * Default Constructor
	 */
	public SocialLinkBean() {
		super();
	}
	/**
	 * Social Site field in the Social Link Component
	 */
	@SerializedName("socialsite")
	private String socialSite;
	/**
	 * Social Image field in the Social Link Component
	 */
	@SerializedName("socialimage")
	private String socialImage;
	/**
	 * Rendition Map field in the Social Link Component
	 */
	@SerializedName("renditions")
	Map<String,String> renditionMap;
	/**
	 * CTA Action field in the Social Link Component
	 */
	@SerializedName("openurlnewwindow")
	private Boolean ctaAction;
	/**
	 * Social URL field in the Social Link Component
	 */
	@SerializedName("socialurl")
	private String socialURL;
	/**
	 * Alt Text field in the Social Link Component
	 */
	@SerializedName("alttext")
	private String altText;
	/**
	 * CSS Class field for social icon the Social Link Component
	 */
	@SerializedName("cssclass")
	private String cssClass;
	/**
	 * This method is used to get the value of 
	 * the Social Site field.
	 */
	@SerializedName ("nofollow")
	private Boolean seoNoFollow;

	public String getSocialSite() {
		return socialSite;
	}
	/**
	 * This method is used to set the value of 
	 * the Social Site field.
	 */
	public void setSocialSite(String socialSite) {
		this.socialSite = socialSite;
	}
	/**
	 * This method is used to get the value of 
	 * the Social Image field.
	 */
	public String getSocialImage() {
		return socialImage;
	}
	/**
	 * This method is used to set the value of 
	 * the Social Image field.
	 */
	public void setSocialImage(String socialImage) {
		this.socialImage = socialImage;
	}
	/**
	 * This method is used to get the value of 
	 * the CTA action field.
	 */
	public Boolean getCtaAction() {
		return ctaAction;
	}
	/**
	 * This method is used to set the value of 
	 * the CTA action field.
	 */
	public void setCtaAction(Boolean ctaAction) {
		this.ctaAction = ctaAction;
	}
	/**
	 * This method is used to get the value of 
	 * the social icon css class field.
	 */
	public String getCssClass() {
		return cssClass;
	}
	/**
	 * This method is used to set the value of 
	 * the social icon css class field.
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	/**
	 * This method is used to get the value of 
	 * the Social URL field.
	 */
	public String getSocialURL() {
		return socialURL;
	}
	/**
	 * This method is used to set the value of 
	 * the Social URL field.
	 */
	public void setSocialURL(String socialURL) {
		this.socialURL = socialURL;
	}
	/**
	 * This method is used to get the value of 
	 * the Alt Text field.
	 */
	public String getAltText() {
		return altText;
	}
	/**
	 * This method is used to set the value of 
	 * the Alt Text field.
	 */
	public void setAltText(String altText) {
		this.altText = altText;
	}
	/**
	 * This method is used to get the map of
	 * all the renditions of the social image
	 */
	public Map<String, String> getRenditionMap() {
		return renditionMap;
	}
	/**
	 * This method is used to set the map of
	 * all the renditions of the social image
	 */
	public void setRenditionMap(Map<String, String> renditionMap) {
		this.renditionMap = renditionMap;
	}

	public Boolean getSeoNoFollow() {
		return seoNoFollow;
	}

	public void setSeoNoFollow(Boolean seoNoFollow) {
		this.seoNoFollow = seoNoFollow;
	}
}
