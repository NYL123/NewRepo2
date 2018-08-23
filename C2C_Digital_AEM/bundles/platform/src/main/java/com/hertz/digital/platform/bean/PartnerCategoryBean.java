package com.hertz.digital.platform.bean;

import java.util.List;

public class PartnerCategoryBean {

	/**
	 * Default constructor
	 * */
	public PartnerCategoryBean(){
		super();
	}
	
	/** 
	 * Variable for partner category name
	 */
	private String partnerCategoryName;
	private String partnerCategory;

	/**
	 * Variable for a list of partners for partner details.
	 * */
	private List<PartnerDetailsBean> partners;
	
	/**
	 * @return the partnerCategoryName
	 */
	public String getPartnerCategoryName() {
		return partnerCategoryName;
	}
	/**
	 * @param partnerCategoryName the partnerCategoryName to set
	 */
	public void setPartnerCategoryName(String partnerCategoryName) {
		this.partnerCategoryName = partnerCategoryName;
	}
	/**
	 * @return the partners
	 */
	public List<PartnerDetailsBean> getPartners() {
		return partners;
	}
	/**
	 * @param partners the partners to set
	 */
	public void setPartners(List<PartnerDetailsBean> partners) {
		this.partners = partners;
	}

	public String getPartnerCategory() {
		return partnerCategory;
	}

	public void setPartnerCategory(String partnerCategory) {
		this.partnerCategory = partnerCategory;
	}
}
