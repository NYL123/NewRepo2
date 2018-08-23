package com.hertz.digital.platform.use;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

/**
 * A Use class that initializes a number of Promotion Card components in the
 * Promotion Container component. The number is mentioned in the property
 * promoCardNumber and that property is used to initialize the Promotion card
 * components.
 * 
 * @author puneet.soni
 *
 */
public class PromotionContainerUse extends WCMUsePojo {

	/**
	 * Default Constructor
	 */
	public PromotionContainerUse() {
		super();
	}

	/**
	 * Initialize the Logger
	 */
	protected static final Logger logger = LoggerFactory.getLogger(PromotionContainerUse.class);

	/**
	 * Literal for the Property promoCardNumber
	 */
	private static final String PROMO_CARD_NUMBER = "promocardnumber";
	private static final String PAR = "par";

	/**
	 * This arrayList will have a size that is equal to PROMO_CARD_NUMBER
	 */
	private ArrayList<String> promoCardArrayList = new ArrayList<String>();


	/**
	 * This method will create an ArrayList that has a size that is equal to the
	 * value of promoCardNumber. this is required for the component that is
	 * included to have a unique name.
	 */
	@Override
	public void activate() {
		logger.debug("******Entering activate method of PromotionContainerUse*******");
		Integer promoCardInteger = getProperties().get(PROMO_CARD_NUMBER, Integer.class);
		if (null != promoCardInteger) {
			for (int index = 0; index < promoCardInteger; index++) {
				if (null != promoCardArrayList) {
					promoCardArrayList.add(PAR + Integer.toString(index));
				}
			}
		}
		logger.debug("******Exiting activate method of PromotionContainerUse*******");
	}

	/**
	 * Getter Method of the promoCardArrayList.
	 * 
	 * @return an ArrayList that has a size equal to the value of
	 *         promoCardNumber
	 */
	public ArrayList<String> getPromoCardArrayList() {
		return promoCardArrayList;
	}
	
	/**
	 * Setter Method of the promoCardArrayList.
	 * 
	 * @return 
	 */
	public void setPromoCardArrayList(ArrayList<String> promoCardArrayList) {
		this.promoCardArrayList = promoCardArrayList;
	}

}
