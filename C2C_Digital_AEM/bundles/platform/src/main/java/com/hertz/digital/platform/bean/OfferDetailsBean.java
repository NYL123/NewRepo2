package com.hertz.digital.platform.bean;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * This is the bean class for all the fields
 * to be shown in the offer details json.
 * 
 * @author a.dhingra
 *
 */
public class OfferDetailsBean {
	
	/**
	 * Default Constructor
	 */
	public OfferDetailsBean() {
		super();
	}

	
	/**
	 * Variable for name field in the json.
	 * */
	@SerializedName("name")
	private String offerName;
	
	/**
	 * Variable for attributes field in the json.
	 * */
	private Map<String, Object> attributes;
	
	/**
	 * Variable for metaData field in the json.
	 * */
	private Map<String,Object> metaData;
	
	/**
	 * Variable for categories field in the json.
	 * */
	private List<CategoriesBean> categories;
	
	/**
	 * Variable for filter field in the json.
	 * */
	private List<Map<String,Object>> filter;
	
	/**
	 * @return the offerName
	 */
	public String getOfferName() {
		return offerName;
	}
	/**
	 * @param offerName the offerName to set
	 */
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	/**
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	/**
	 * @return the metaData
	 */
	public Map<String, Object> getMetaData() {
		return metaData;
	}
	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(Map<String, Object> metaData) {
		this.metaData = metaData;
	}

	/**
	 * @return the filter
	 */
	public List<Map<String, Object>> getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(List<Map<String, Object>> filter) {
		this.filter = filter;
	}
	/**
	 * @return the categories
	 */
	public List<CategoriesBean> getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<CategoriesBean> categories) {
		this.categories = categories;
	}

}
