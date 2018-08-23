package com.hertz.digital.platform.bean;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * This is a Javabean for the complete country language properties
 * 
 * @author himanshu.i.sharma
 * 
 */
public class CountryLanguageBean {
	/**
	 * Default Constructor
	 */
	public CountryLanguageBean(){
		super();
	}
	
	@SerializedName("dropdown-labels")
	private HeaderCountryLanguageItemsBean headerCountryLanguageItemsBean;
	
	@SerializedName("login-redirect")
	private HeaderCountryLangLoginRedirectItemsBean headerCountryLanguageLoginRedirectItemsBean;
	
	@SerializedName("countries-languages")
	private List<CountryLanguageItemsBean> countryLanguageItemsBeanList;
	
	public HeaderCountryLanguageItemsBean getHeaderCountryLanagueItemsBean() {
		return headerCountryLanguageItemsBean;
	}
	
	public void setHeaderCountryLanguageItemsBean(HeaderCountryLanguageItemsBean headerCountryLanguageItemsBean) {
		this.headerCountryLanguageItemsBean = headerCountryLanguageItemsBean;
	}
	
	/**
	 * This method is used to get the list of all properties of the 
	 * country language selector and all it's child components.
	 * 
	 */
	public List<CountryLanguageItemsBean> getCountryLanguageItemsBeanList() {
		return countryLanguageItemsBeanList;
	}
	/**
	 * This method is used to set all properties of the 
	 * country language selector and all it's child components in a list.
	 * 
	 */
	public void setCountryLanguageItemsBeanList(List<CountryLanguageItemsBean> countryLanguageItemsBeanList) {
		this.countryLanguageItemsBeanList = countryLanguageItemsBeanList;
	}

	/**
	 * @return the headerCountryLanguageItemsBean
	 */
	public HeaderCountryLanguageItemsBean getHeaderCountryLanguageItemsBean() {
		return headerCountryLanguageItemsBean;
	}
	
	/**
	 * @return the headerCountryLanguageLoginRedirectItemsBean
	 */
	public HeaderCountryLangLoginRedirectItemsBean getHeaderCountryLanguageLoginRedirectItemsBean() {
		return headerCountryLanguageLoginRedirectItemsBean;
	}

	/**
	 * @param headerCountryLanguageLoginRedirectItemsBean the headerCountryLanguageLoginRedirectItemsBean to set
	 */
	public void setHeaderCountryLanguageLoginRedirectItemsBean(
			HeaderCountryLangLoginRedirectItemsBean headerCountryLanguageLoginRedirectItemsBean) {
		this.headerCountryLanguageLoginRedirectItemsBean = headerCountryLanguageLoginRedirectItemsBean;
	}
}
