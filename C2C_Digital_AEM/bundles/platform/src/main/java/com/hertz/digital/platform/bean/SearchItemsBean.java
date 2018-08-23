package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * JavaBean for the Header Search
 * @author himanshu.i.sharma
 *
 */
public class SearchItemsBean {
	/**
	 * Default Constructor
	 */
	public SearchItemsBean(){
		super();
	}

	/**
	 * Metadata searchIconAltTxt
	 */
	@SerializedName("alttext")
	private String searchIconAltTxt;

	
	/**
	 * get value of display_tablet
	 * 
	 * @return
	 */
	public String getSearchIconAltText() {
		return searchIconAltTxt;
	}
	
	/**
	 * 
	 * @param display_native
	 */
	public void setSearchIconAltText(String searchIconAltTxt) {
		this.searchIconAltTxt = searchIconAltTxt;
	}

}
