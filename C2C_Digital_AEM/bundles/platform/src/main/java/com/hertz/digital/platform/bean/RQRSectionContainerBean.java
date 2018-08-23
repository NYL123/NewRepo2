package com.hertz.digital.platform.bean;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * This is a Javabean for the complete rqr sections
 * 
 * @author himanshu.i.sharma
 * 
 */
public class RQRSectionContainerBean {
	/**
	 * Default Constructor
	 */
	public RQRSectionContainerBean(){
		super();
	}
	
	/**
	 * Stores the RQR sections and links list
	 */
	@SerializedName("sections")
	private List<RQRSectionsBean> rQRSectionsBeanList;
	
	/**
	 * This method is used to get the list of all rqr sections and links.
	 * 
	 */
	public List<RQRSectionsBean> getRQRSectionsBeanList() {
		return rQRSectionsBeanList;
	}
	/**
	 * This method is used to set the list of all rqr sections and links.
	 * 
	 */
	public void setRQRSectionsBeanList(List<RQRSectionsBean> rQRSectionsBeanList) {
		this.rQRSectionsBeanList = rQRSectionsBeanList;
	}
}
