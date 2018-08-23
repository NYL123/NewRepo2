package com.hertz.digital.platform.use;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.HeaderFooterBean;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * @author a.dhingra
 *
 */
public class HeaderFooterUse extends WCMUsePojo {
	/**
	 * Default constructor declaration
	 */
	public HeaderFooterUse() {
		super();
	}

	/**
	 * LOGGER instantiation
	 */

	private static final Logger logger = LoggerFactory.getLogger(HeaderFooterUse.class);

	/**
	 * Declaring variable for html to get and display the json.
	 */
	private String jsonString;

	/**
	 * This method is used to generate the headerfooter json.
	 * 
	 */
	@Override
	public void activate() throws Exception {
		logger.debug("Entering method activate() of HeaderFooterUse class");
		
		ResourceResolver resolver=getResourceResolver();
		Resource pageResource = getResource();
		Page page = getCurrentPage();
		SlingScriptHelper helper = getSlingScriptHelper();
		String pagePath=page.getPath();	
		logger.debug("HeaderFooterUse : Page Path :- " + pagePath);
		HeaderFooterBean headerFooterBean = HertzUtils.setHeaderFooterContent(pageResource, resolver, helper);
		Gson gson=new Gson();
		jsonString=gson.toJson(headerFooterBean);
		
		logger.debug("Exit method activate() of HeaderFooterUse class");
		

	}

	/**
	 * This method is used to 
	 * get the json string in the html.
	 * 
	 * @return the jsonString
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * This method is used to 
	 * set the string json in the variable.
	 * 
	 * @param jsonString
	 *            the jsonString to set
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}
