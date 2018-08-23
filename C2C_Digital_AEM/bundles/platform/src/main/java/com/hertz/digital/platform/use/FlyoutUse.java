package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;


import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.bean.FlyoutBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlyoutUse extends WCMUsePojo {
	/**
	 * Default Constructor
	 */
	public FlyoutUse(){
		super();
	}
	/**
	 * LOGGER instantiation.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlyoutUse.class);
	
	List<FlyoutBean> flyoutBeanList=new ArrayList<FlyoutBean>();
	/**
	 * 
	 * Activate method for FlyoutUse Class
	 * @param none
	 * @return void
	 * @throws JSONException
	 * 
	 * */
	@Override
	public void activate() throws JSONException  {
		logger.debug("******Entering activate method of FlyoutUse*******");
		getFlyoutValues();
		logger.debug("******Exiting activate method of FlyoutUse*******");
	}
	/**
	 * 
	 *This method is used to get all the Flyout  component values
	 * @param none
	 * @return void
	 * @throws JSONException
	 * 
	 * */
	private void getFlyoutValues() throws JSONException {
		logger.debug("******Entering getFlyoutValues method of FlyoutUse*******");
		FlyoutBean flyoutBean;	
		String[] headerArray = getProperties().get(HertzConstants.LINKS, String[].class);
		if(null!=headerArray){
			for (String headerElement : headerArray) {
				JSONObject object = new JSONObject(headerElement);
				flyoutBean = new FlyoutBean();
				if (null != object) {
					setFlyoutBeanValues(flyoutBean, object);
				}
				flyoutBeanList.add(flyoutBean);
			}
		}

		logger.debug("******Exiting getFlyoutValues method of FlyoutUse*******");
	}
	
	/**
	 * This method is called from the getFlyoutValues() method and 
	 * is used to set all the values in the FlyoutBean
	 * @param FlyoutBean, object
	 * @return void
	 * @throws JSONException
	 * 
	 * */
	private void setFlyoutBeanValues(FlyoutBean flyoutBean, JSONObject object) throws JSONException {
		logger.debug("******Entering setFlyoutBeanValues method of FlyoutUse*******");
		
		flyoutBean.setHeading(HertzUtils.getStringValueFromObject(object, HertzConstants.HEADING));
		flyoutBean.setLinkurl(HertzUtils.getStringValueFromObject(object, HertzConstants.FOOTER_LINK_URL));
		String openUrlInNewWindow=object.optString(HertzConstants.OPEN_URL_NEW_WINDOW);
		if(openUrlInNewWindow.equalsIgnoreCase("no")){
			flyoutBean.setOpenLegalLinkURLInNewWindow(false);
		}else if(openUrlInNewWindow.equalsIgnoreCase("yes")){
			flyoutBean.setOpenLegalLinkURLInNewWindow(true);
		}
		String seoNoFollow=object.optString(HertzConstants.SEO_NOFOLLOW);
		if(seoNoFollow.equalsIgnoreCase("no")){
			flyoutBean.setSeoNoFollow(false);
		}else if(seoNoFollow.equalsIgnoreCase("yes")){
			flyoutBean.setSeoNoFollow(true);
		}
		logger.debug("******Exiting setFlyoutBeanValues method of FlyoutUse*******");
	}


	/**
	 * This method is called from the sightly code of flyout component and 
	 * is used to get the list of all the values of the flyout component
	 * @param none
	 * @return List<flyoutBeanList>
	 *
	 * 
	 * */
	public List<FlyoutBean> getFlyoutBeanList() {
		return flyoutBeanList;
	}
	/**
	 * This method is used to set the list of all the values of the flyout  component
	 * @param flyoutBeanList
	 * @return void
	 *
	 * */
	public void setFlyoutBeanList(List<FlyoutBean> flyoutBeanList) {
		this.flyoutBeanList = flyoutBeanList;
	}

}

