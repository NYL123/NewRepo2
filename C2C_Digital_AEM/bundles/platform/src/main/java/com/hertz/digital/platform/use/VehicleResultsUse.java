package com.hertz.digital.platform.use;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.constants.HertzConstants;
import org.apache.sling.api.resource.ValueMap;

/**
 * A Use class that is used to find if the VehicleResults component as any
 * authored properties.
 * 
 * @author puneet.soni
 *
 */
public class VehicleResultsUse extends WCMUsePojo {

	/**
	 * Default Constructor
	 */
	public VehicleResultsUse() {
		super();
	}

	/**
	 * Boolean value that mentions if the properties for the vehicleResults
	 * component are empty or not
	 */
	private boolean empty;

	/**
	 * Initialize the Logger
	 */
	protected static final Logger logger = LoggerFactory.getLogger(VehicleResultsUse.class);

	/**
	 * This method that is used to find if the VehicleResults component as any
	 * authored properties.
	 */
	@Override
	public void activate() {
		logger.debug("******Entering activate method of VehicleResultsUse*******");
		HashMap hashMap = new HashMap();
		ValueMap componentProperties = getResource().getValueMap();
		for (Map.Entry e : componentProperties.entrySet()) {
			if (!e.getKey().toString().contains(HertzConstants.COLON)) {
				hashMap.put(e.getKey().toString(), e.getValue());
			}
		}
		if (hashMap.isEmpty()) {
			setEmpty(true);
		}

		logger.debug("******Exiting activate method of VehicleResultsUse*******");
	}

	/**
	 * Getter method for Boolean Empty
	 * 
	 * @return boolean true if properties are empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Setter method for boolean empty
	 * 
	 * @param empty
	 *            boolean that indicates if the properties are empty
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
