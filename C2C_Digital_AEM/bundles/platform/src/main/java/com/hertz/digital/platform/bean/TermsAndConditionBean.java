package com.hertz.digital.platform.bean;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * This is the bean class for getting the list of terms and conditions sections.
 * 
 * @author a.dhingra
 *
 */
public class TermsAndConditionBean {
	/**
	 * Default Constructor Declaration
	 */
	public TermsAndConditionBean() {
		super();
	}

	/**
	 * Declaring parent root variable for termsconditions json.
	 */
	@SerializedName("termsandconditions")
	private List<TermsAndConditionsSectionBean> termsAndConditions;

	/**
	 * This method is used to get the list of sections of terms and conditions
	 * 
	 * @return the termsAndConditions
	 */
	public List<TermsAndConditionsSectionBean> getTermsAndConditions() {
		return termsAndConditions;
	}

	/**
	 * This method is used to set the list of sections of terms and conditions.
	 * 
	 * @param termsAndConditions
	 *            the termsAndConditions to set
	 */
	public void setTermsAndConditions(List<TermsAndConditionsSectionBean> termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
}
