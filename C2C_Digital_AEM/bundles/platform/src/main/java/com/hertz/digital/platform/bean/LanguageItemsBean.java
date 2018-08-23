package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

public class LanguageItemsBean {
	/**
	 * Default Constructor
	 */
	public LanguageItemsBean(){
		super();
	}
	
	/**
	 * Country Language 
	 */
	@SerializedName("locale")
	private String locale;
	
	/**
	 * Country Name 
	 */
	@SerializedName("country")
	private String countryName;
	
	/**
	 * Language Name 
	 */
	@SerializedName("language")
	private String languageName;
	
	/**
	 * Language Code 
	 */
	@SerializedName("languagecode")
	private String languageCode;
	
	/**
	 * Language Code 
	 */
	@SerializedName("isDefaultLanguage")
	private Boolean defaultLanguage;
	
	/**
	 * Internal or External URL 
	 */
	@SerializedName("URL")
	private String url;
	
	/**
	 * Redirect URL
	 */
	@SerializedName("redirectUrl")
	private String iracLink;
	
	/**
	 * This method is used for getting the country language
	 * @return
	 */
	public String getLocale() {
		return locale;
	}
	
	/**
	 * This method is used to set the country language
	 * @param countryLanguage
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	/**
	 * This method is used for getting the country name
	 * @return
	 */
	public String getCountryName() {
		return countryName;
	}
	
	/**
	 * This method is used to set the country name
	 * @param countryName
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	/**
	 * This method is used for getting the country code
	 * @return
	 */
	public String getLanguageName() {
		return languageName;
	}
	
	/**
	 * This method is used to set the country code
	 * @param countryCode
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
	/**
	 * This method is used for getting the language code
	 * @return
	 */
	public String getLanguageCode() {
		return languageCode;
	}
	
	/**
	 * This method is used to set the language code
	 * @param languageCode
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	/**
	 * This method is used for getting the language code
	 * @return
	 */
	public Boolean getDefaultLanguage() {
		return defaultLanguage;
	}
	
	/**
	 * This method is used to set the language code
	 * @param languageCode
	 */
	public void setDefaultLanguage(Boolean defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}
	
	/**
	 * This method is used to get the internal or external url
	 * @return
	 */
	public String getURL() {
		return url;
	}
	
	/**
	 * This method is used to set the internal or external url
	 * @param url
	 */
	public void setURL(String url) {
		this.url = url;
	}
	
	/**
	 * This method is used to get the IRACLINK
	 * @return
	 */
	public String getIracLink() {
		return iracLink;
	}
	
	/**
	 * This method is used to set the iracLink
	 * @param iracLink
	 */
	public void setIracLink(String iracLink) {
		this.iracLink = iracLink;
	}
}
