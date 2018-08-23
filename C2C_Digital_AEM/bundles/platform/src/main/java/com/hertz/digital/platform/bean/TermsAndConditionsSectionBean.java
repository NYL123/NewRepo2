/**
 * 
 */
package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * This is the bean class for the terms and condition sections.
 * 
 * @author a.dhingra
 *
 */
public class TermsAndConditionsSectionBean {
	/**
	 * Declaring default Constructor
	 */
	public TermsAndConditionsSectionBean() {
		super();
	}

	/**
	 * Declaring variable for order number
	 * 
	 */

	private Integer orderNumber;

	/**
	 * Declaring variable for section description
	 */
	@SerializedName("section-description")
	private String sectionDescription;

	/**
	 * Getter for sectionDescription variable
	 * 
	 * @return the sectionDescription
	 */
	public String getSectionDescription() {
		return sectionDescription;
	}

	/**
	 * Sets the sections beans.
	 * 
	 * @param sectionDescription
	 *            the sectionDescription to set
	 */
	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}

	/**
	 * @return the orderNumber
	 */
	public Integer getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
	 */
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
}
