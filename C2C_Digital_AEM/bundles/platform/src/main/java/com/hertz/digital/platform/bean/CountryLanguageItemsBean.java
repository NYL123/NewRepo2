package com.hertz.digital.platform.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This is the bean class for country language items.
 *
 * @author himanshu.i.sharma
 */

public class CountryLanguageItemsBean {
	/**
	 * Default Constructor
	 */
	public CountryLanguageItemsBean(){
		super();
	}
	
	/**
	 * Country Name 
	 */
	@SerializedName("country")
	private String countryName;
	
	/**
	 * Country Code 
	 */
	@SerializedName("countrycode")
	private String countryCode;
	
	/**
	 * Domain 
	 */
	@SerializedName("domain")
	private String domain;
	
	@Expose
    @SerializedName("sources")
    private List<Map<String, Object>> sources = new LinkedList<Map<String, Object>>();

	private ImageInfoBean imageInfo;

	/**
	 * pos 
	 */
	@SerializedName("POS")
	private String pos;
	/**
	 * Is IRAC 
	 */
	@SerializedName("isIRAC")
	private Boolean isirac;
	
	@SerializedName("languages")
	private List<LanguageItemsBean> languageItemsBeanList;
	
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
	public String getCountryCode() {
		return countryCode;
	}
	
	/**
	 * Gets the imageInfo 
	 * @return ImageInfoBean
	 */
	public ImageInfoBean getImageInfo() {
		return imageInfo;
	}
	/**
	 * Sets the image info in the bean
	 * @param imageInfo
	 */
	public void setImageInfo(ImageInfoBean imageInfo) {
		this.imageInfo = imageInfo;
	}
	/**
	 * This gets the list of sources for the image.
	 * 
	 * @return list of sources
	 */
	public List<Map<String, Object>> getSources() {
		return sources;
	}
	/**
	 * @param sources
	 */
	public void setSources(List<Map<String, Object>> sources) {
		this.sources = sources;
	}
	
	/**
	 * This method is used to set the country code
	 * @param countryCode
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	/**
	 * This method is used to get the list of all properties of the 
	 * country language selector and all it's child components.
	 * 
	 */
	public List<LanguageItemsBean> getLanguageItemsBeanList() {
		return languageItemsBeanList;
	}
	/**
	 * @return the pos
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(String pos) {
		this.pos = pos;
	}

	/**
	 * @return the isirac
	 */
	public Boolean getIsirac() {
		return isirac;
	}

	/**
	 * @param isirac1 the isirac to set
	 */
	public void setIsirac(Boolean isirac) {
		this.isirac = isirac;
	}

	/**
	 * This method is used to set all properties of the 
	 * country language selector and all it's child components in a list.
	 * 
	 */
	public void setLanguageItemsBeanList(List<LanguageItemsBean> languageItemsBeanList) {
		this.languageItemsBeanList = languageItemsBeanList;
	}
}
