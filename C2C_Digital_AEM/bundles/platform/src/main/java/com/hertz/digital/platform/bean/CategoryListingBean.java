package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;
/**
 * Bean class for CategoryListingUse class
 * @author puneet.soni
 *
 */
public class CategoryListingBean {
	
	/**
	 * Default Constructor
	 */
	public CategoryListingBean() {
		super();

	}

	/**
	 * Holds the value of Rank
	 */
	String rank ;
	
	/**
	 * Holds the value of Name
	 */
	String name ;
	
	/**
	 * Field for db2Mapping
	 * */
	String db2Category;
	
	/**
	 * Path of the resource
	 * */
	String contentPath;
	/**
	 * OfferCategoryBean
	 */
	OfferCategoryBean attributes;
	
	/**
	 * List of OfferCategoryBean
	 */
	List<OfferCategoryBean>  subCategory=  new ArrayList<OfferCategoryBean>();


	/**
	 * Getter of List SubCategory
	 * @return list of OfferCategoryBean
	 */
	public List<OfferCategoryBean> getSubCategory() {
		return subCategory;
	}
	
	/**
	 * Adds the OfferCategoryBean in the list 
	 * @param subCategory
	 */
	public void setSubCategory(OfferCategoryBean subCategory) {
		this.subCategory.add(subCategory);
	}
	
	/**
	 * Getter for OfferCategoryBean
	 * @return
	 */
	public OfferCategoryBean getAttributes() {
		return attributes;
	}
	
	/**
	 * Setter for OfferCategoryBean
	 * @param attributes value of OfferCategoryBean
	 */
	public void setAttributes(OfferCategoryBean attributes) {
		this.attributes = attributes;
	}

	/**
	 * Getter for Name
	 * @return String that contains Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for  Name
	 * @param name String that contains Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for Rank
	 * @return String that contains rank
	 */
	public String getRank() {
		return rank;
	}
	
	/**
	 * Setter for Rank
	 * @param rank String that contains rank
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}

	/**
	 * @return the db2Category
	 */
	public String getDb2Category() {
		return db2Category;
	}

	/**
	 * @param db2Category the db2Category to set
	 */
	public void setDb2Category(String db2Category) {
		this.db2Category = db2Category;
	}

	/**
	 * @return the contentPath
	 */
	public String getContentPath() {
		return contentPath;
	}

	/**
	 * @param contentPath the contentPath to set
	 */
	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}	

}
