/**
 * 
 */
package com.hertz.digital.platform.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * This bean is used to set all the properties in the Header
 * 
 * @author a.dhingra
 *
 */
public class HeaderFooterBean {
	
	/**
	 * Declaring Public Constructor
	 */
	public HeaderFooterBean() {
		super();
	}
	/**
	 * Field for headerJSON;
	 */
	@SerializedName("header")
	private List<HeaderBean> headerBeanList;
	
	/**
	 * List of all footer properties
	 */
	@SerializedName("footer")
	private List<FooterContainerBean> footerContentList;
	/**
	 * 
	 * This method is used to get the headerJSON
	 */
	public List<HeaderBean> getHeaderBeanList() {
		return headerBeanList;
	}

	/**
	 * 
	 * This method is used to set the headerJSON
	 * @param headerBeanList
	 */
	public void setHeaderBeanList(List<HeaderBean> headerBeanList) {
		this.headerBeanList = headerBeanList;
	}
	/**
	 * This method is used to get the list of all properties of the footer
	 * component and all it's child components.
	 *
	 * @return List<FooterContainerBean>
	 */
	public List<FooterContainerBean> getFooterContentList() {
		return footerContentList;
	}

	/**
	 * This method is used to set all properties of the footer component and all
	 * it's child components in a list.
	 * 
	 * @param footerContentList
	 */
	public void setFooterContentList(List<FooterContainerBean> footerContentList) {
		this.footerContentList = footerContentList;
	}

}
