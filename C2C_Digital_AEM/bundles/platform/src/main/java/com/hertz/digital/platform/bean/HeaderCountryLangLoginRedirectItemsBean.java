/**
 * 
 */
package com.hertz.digital.platform.bean;
import com.google.gson.annotations.SerializedName;

/**
 * JavaBean for the Header Country Language Login Redirect Items
 * @author ajay.c.pandey
 *
 */
public class HeaderCountryLangLoginRedirectItemsBean {
	/**
	 * Default Constructor
	 */
	public HeaderCountryLangLoginRedirectItemsBean(){
		super();
	}

	/**
	 * Metadata redirectMessage
	 */
	@SerializedName("redirectMessage")
	private String redirectMessage;
	
		/**
	 * Metadata redirectHeading
	 */
	@SerializedName("redirectHeading")
	private String redirectHeading;
	
	/**
	 * Metadata cancelButton
	 */
	@SerializedName("cancelButton")
	private String cancelButton;
	
	/**
	 * Metadata continueButton
	 */
	@SerializedName("continueButton")
	private String continueButton;
	
	
	/**
	 * @return the cancelButton
	 */
	public String getCancelButton() {
		return cancelButton;
	}

	/**
	 * @param cancelButton the cancelButton to set
	 */
	public void setCancelButton(String cancelButton) {
		this.cancelButton = cancelButton;
	}

	/**
	 * @return the continueButton
	 */
	public String getContinueButton() {
		return continueButton;
	}

	/**
	 * @param continueButton the continueButton to set
	 */
	public void setContinueButton(String continueButton) {
		this.continueButton = continueButton;
	}

	/**
	 * @return the redirectMessage
	 */
	public String getRedirectMessage() {
		return redirectMessage;
	}

	/**
	 * @param redirectMessage the redirectMessage to set
	 */
	public void setRedirectMessage(String redirectMessage) {
		this.redirectMessage = redirectMessage;
	}

	/**
	 * @return the redirectHeading
	 */
	public String getRedirectHeading() {
		return redirectHeading;
	}

	/**
	 * @param redirectHeading the redirectHeading to set
	 */
	public void setRedirectHeading(String redirectHeading) {
		this.redirectHeading = redirectHeading;
	}


}
