package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * JavaBean for the Header Country Language Items
 * @author himanshu.i.sharma
 *
 */
public class HeaderCountryLanguageItemsBean {
	/**
	 * Default Constructor
	 */
	public HeaderCountryLanguageItemsBean(){
		super();
	}
	/**
	 * Metadata countryLabel
	 */
	@SerializedName("countryLabel")
	private String countryLabel;
	
	/**
	 * Metadata languageLabel
	 */
	@SerializedName("languageLabel")
	private String languageLabel;
	
	/**
	 * Metadata updateLabel
	 */
	@SerializedName("buttonLabel")
	private String updateLabel;
	
	/**
	 * Metadata updateMessage
	 */
	@SerializedName("updateMessage")
	private String updateMessage;
	
	/**
	 * Metadata updateLabel
	 */
	@SerializedName("targetUrl")
	private String targetUrl;
	
	/**
	 * Metadata updateLabel
	 */
	@SerializedName("openUrlNewWindow")
	private Boolean openUrlNewWindow;

	@SerializedName ("nofollow")
	private Boolean seoNoFollow;

	private String noResultsMessage;
	
	/**
	 * boolean for display_native
	 * @return
	 */
	public String getCountryLabel() {
		return countryLabel;
	}
	
	/**
	 * 
	 * @param display_native
	 */
	public void setCountryLabel(String countryLabel) {
		this.countryLabel = countryLabel;
	}
	
	/**
	 * get value of display_mobile
	 * @return
	 */
	public String getLanguageLabel() {
		return languageLabel;
	}
	
	/**
	 * 
	 * @param display_native
	 */
	public void setLanguageLabel(String languageLabel) {
		this.languageLabel = languageLabel;
	}
	
	/**
	 * get value of display_tablet
	 * 
	 * @return
	 */
	public String getTargetUrl() {
		return targetUrl;
	}
	
	/**
	 * 
	 * @param display_native
	 */
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
	/**
	 * get value of display_tablet
	 * 
	 * @return
	 */
	public String getUpdateLabel() {
		return updateLabel;
	}
	
	/**
	 * 
	 * @param display_native
	 */
	public void setUpdateLabel(String updateLabel) {
		this.updateLabel = updateLabel;
	}
	
	/**
	 * get value of display_tablet
	 * 
	 * @return
	 */
	public String getUpdateMessage() {
		return updateMessage;
	}
	
	/**
	 * 
	 * @param display_native
	 */
	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}
	
	/**
	 * get value of display_tablet
	 * 
	 * @return
	 */
	public Boolean getOpenUrlNewWindow() {
		return openUrlNewWindow;
	}
	
	/**
	 * 
	 * @param display_native
	 */
	public void setOpenUrlNewWindow(Boolean openUrlNewWindow) {
		this.openUrlNewWindow = openUrlNewWindow;
	}

	public Boolean getSeoNoFollow() {
		return seoNoFollow;
	}

	public void setSeoNoFollow(Boolean seoNoFollow) {
		this.seoNoFollow = seoNoFollow;
	}

	/**
	 * @return the noResultsMessage
	 */
	public String getNoResultsMessage() {
		return noResultsMessage;
	}

	/**
	 * @param noResultsMessage the noResultsMessage to set
	 */
	public void setNoResultsMessage(String noResultsMessage) {
		this.noResultsMessage = noResultsMessage;
	}
	
}
