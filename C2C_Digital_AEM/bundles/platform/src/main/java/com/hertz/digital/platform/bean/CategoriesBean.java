package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * This class is for generating
 * the category name in the offer detail and 
 * offer listing json.
 * 
 * @author a.dhingra
 *
 */
public class CategoriesBean {
	
	/**
	 * Default Constructor
	 */
	public CategoriesBean() {
		super();
	}

	
	/**
	 * Variable for category-name in the json
	 * */
	@SerializedName("category-name")
	private String category;

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
}
