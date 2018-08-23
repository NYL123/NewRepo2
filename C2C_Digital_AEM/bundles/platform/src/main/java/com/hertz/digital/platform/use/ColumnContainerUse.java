package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

/**
 * Use class for Column Container Component. Sends the Number of Column Heading
 * component that needs to be created.
 * 
 * @author puneet.soni
 *
 */
public class ColumnContainerUse extends WCMUsePojo {

	public ColumnContainerUse() {
		super();
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ColumnContainerUse.class);

	private List list;

	/**
	 * Activate Method that sets the Number of Column Heading component that
	 * needs to be created
	 */
	@Override
	public void activate() throws Exception {
		LOGGER.debug("Entering inside activate method of ColumnContainerUse");

		String value = (String) getProperties().get("columnHeading");

		int intValue = Integer.parseInt(value);

		List list = new ArrayList<>();

		for (int i = 0; i < intValue; i++) {
			list.add(i);
		}

		setList(list);
		LOGGER.debug("Exiting activate method of ColumnContainerUse");
	}

	/**
	 * Setter for List
	 * 
	 * @return
	 */
	public List getList() {
		return list;
	}

	/**
	 * Getter for List
	 * 
	 * @param list
	 */
	public void setList(List list) {
		this.list = list;
	}

}
