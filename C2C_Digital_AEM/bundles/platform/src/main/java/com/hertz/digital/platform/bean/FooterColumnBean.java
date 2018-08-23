package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean class to store the values of each Column of Footer
 * 
 * @author puneet.soni
 *
 */
public class FooterColumnBean {

	/**
	 * Default Constructor
	 */
	public FooterColumnBean() {
		super();
	}

	/**
	 * List of class of the FooterLinkBean
	 */
	private final List<FooterLinkBean> column = new ArrayList<FooterLinkBean>();

	/**
	 * Getter for the FooterLinkBean
	 * 
	 * @return bean class of FooterLinkBean
	 */
	public List<FooterLinkBean> getColumn() {
		return column;
	}

	/**
	 * Setter for the FooterLinkBean
	 * 
	 * @param column
	 *            Bean of FooterLinkBean
	 */
	public void setColumn(FooterLinkBean column) {
		this.column.add(column);
	}

}
