/**
 * 
 */
package com.hertz.digital.platform.bean;

/**
 * This bean is used to set the partner 
 * level information in the offerDetails
 * and offerlisting json.
 * 
 * @author a.dhingra
 *
 */
public class PartnerInfoBean {

	/**
	 * Default Constructor
	 */
	public PartnerInfoBean() {
		super();
	}
	
	/**
	 * Variable for parterName in the json
	 * */
	private String partnerName;
	
	/**
	 * Variable for cdpCode in the json
	 * */
	private String cdpCode;
	/**
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}
	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	/**
	 * @return the cdpCode
	 */
	public String getCdpCode() {
		return cdpCode;
	}
	/**
	 * @param cdpCode the cdpCode to set
	 */
	public void setCdpCode(String cdpCode) {
		this.cdpCode = cdpCode;
	}
}
