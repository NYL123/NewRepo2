package com.hertz.digital.platform.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;
/**
 * Bean to store the RQR Sections
 * @author himanshu.i.sharma
 */
public class RQRSectionsBean {
	
	/**
	 * Default Constructor
	 */
	public RQRSectionsBean() {
		super();
	}

	/**
	 * Stores the section name
	 */
	@SerializedName("section-name")
	private String sectionName;
	
	/**
	 * Stores the list of all the rqr links
	 */
	@SerializedName("links")
	private List<RQRLinksBean> rQRLinksBeanList;

	/**
	 * get the link text
	 * @return String that contains the link text
	 */
	public String getSectionName() {
		return sectionName;
	}
	
	/**
	 * set the link text
	 * @param linkText that contains the link text
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * This method is used to get the list of all the rqr links.
	 * 
	 */
	public List<RQRLinksBean> getRQRLinksBeanList() {
		return rQRLinksBeanList;
	}
	
	/**
	 * This method is used to set all rqr links in a list.
	 * 
	 */
	public void setRQRLinksBeanList(List<RQRLinksBean> rQRLinksBeanList) {
		this.rQRLinksBeanList = rQRLinksBeanList;
	}
}
