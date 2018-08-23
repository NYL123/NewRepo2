package com.hertz.digital.platform.bean.spa;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.hertz.digital.platform.bean.FooterContainerBean;
import com.hertz.digital.platform.bean.HeaderBean;

/**
 * This is the bean class for generating the json 
 * for the spa pages. It contains all the variables and
 * getters setters of the variables that are to be rendered
 * in the json.
 * 
 * @author n.kumar.singhal
 *
 */
public class SpaPageBean {
	
	/**
	 * Default Constructor
	 */
	public SpaPageBean() {
		super();
	}
	
	/**
	 * Field for page title
	 */
	@SerializedName(value = "title")
	private String pageTitle;

	/**
	 * Field for page metadata
	 */
	private Map<String,Object> metaData;
	
	/**
	 * Map of all the resources
	 */
	@SerializedName(value = "parsys")
	private Map<String, String> parsysResources;

	/**
	 * Map of all the content of static included components
	 */
	@SerializedName(value = "components")
	private List<Map<String, Object>> includedComponents;
	
	/**
	 * Map of all the paragraph system based configurable text properties
	 */
	@SerializedName(value = "configuredProps")
	private Map<String, Object> configuredProps;
	
	/**
	 * Map of hertz links page properties
	 */
	@SerializedName(value = "hertzlinks")
	private Map<String, Object> hertzlinks;

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
	/**
	 * This method is used to get the pageTitle
	 * @return
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * This method is used to set the pageTitle
	 * @param pageTitle
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**
	 * This method is used to get content of all 
	 * components dragged dropped in parsys
	 * @return
	 */
	public Map<String, String> getParsysResources() {
		return parsysResources;
	}

	/**
	 * This method is used to set content of all 
	 * components dragged dropped in parsys
	 * 
	 * @param parsysResources
	 */
	public void setParsysResources(Map<String, String> parsysResources) {
		this.parsysResources = parsysResources;
	}

	/**
	 * This method is used to get content of all 
	 * components static included on the pages.
	 * @return
	 */
	public List<Map<String, Object>> getIncludedComponents() {
		return includedComponents;
	}

	/**
	 * This method is used to set content of all 
	 * components static included on the pages.
	 * 
	 * @param includedComponents
	 */
	public void setIncludedComponents(List<Map<String, Object>> includedComponents) {
		this.includedComponents = includedComponents;
	}
	
	/**
	 * This method is used to get all the paragraph system based c
	 * onfigurable text properties
	 *
	 * @return the configuredProps
	 */
	public Map<String, Object> getConfiguredProps() {
		return configuredProps;
	}

	/**
	 * This method is used to set all the paragraph system based c
	 * onfigurable text properties.
	 *
	 * @param configuredProps the paragraph system configurable text properties to set
	 */
	public void setConfiguredProps(Map<String, Object> configuredProps) {
		this.configuredProps = configuredProps;
	}
	
	/**
	 * Gets the metadata info map
	 * 
	 * @return the metaData
	 */
	public Map<String,Object> getMetaData() {
		return metaData;
	}

	/**
	 * Sets the meta data map
	 * 
	 * @param metaData the metaData to set
	 */
	public void setMetaData(Map<String,Object> metaData) {
		this.metaData = metaData;
	}
	
	/**
	 * This method is used to get all the hertz links page properties.
	 *
	 * @return the hertzlinks
	 */
	public Map<String, Object> getHertzLinks() {
		return hertzlinks;
	}

	/**
	 * This method is used to set all the hertz links page properties.
	 *
	 * @param hertzlinks 
	 */
	public void setHertzLinks(Map<String, Object> hertzlinks) {
		this.hertzlinks = hertzlinks;
	}

}
