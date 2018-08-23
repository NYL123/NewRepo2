package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * This is a Javabean is the wrapper bean for all the rental qualification config
 * 
 * @author himanshu.i.sharma
 * 
 */
public class RQRWrapperBean {
	/**
	 * Default Constructor
	 */
	public RQRWrapperBean(){
		super();
	}
	
	/**
	 * Stores the RQR sections container bean items
	 */
	@SerializedName("rentalqualification-config")
	private RQRSectionContainerBean rQRSectionContainerBean;
	
	/**
	 * This method is used to get the list of all rqr sections container bean.
	 * 
	 */
	public RQRSectionContainerBean getRQRSectionContainerBean() {
		return rQRSectionContainerBean;
	}
	/**
	 * This method is used to set the list of all rqr sections container bean.
	 * 
	 */
	public void setRQRSectionContainerBean(RQRSectionContainerBean rQRSectionContainerBean) {
		this.rQRSectionContainerBean = rQRSectionContainerBean;
	}
}
