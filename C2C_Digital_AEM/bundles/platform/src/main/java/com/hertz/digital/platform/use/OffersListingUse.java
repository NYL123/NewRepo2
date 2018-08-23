/**
 * 
 */
package com.hertz.digital.platform.use;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.collections.MapUtils;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.jcr.JsonItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.CategoryListingBean;
import com.hertz.digital.platform.bean.OfferDetailsBean;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.impl.ConfigurableTextParSysExportor;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.utils.HertzUtils;
import com.hertz.digital.platform.utils.OfferUtils;

/**
 * This class is used to construct the json for 
 * offerlisting where attributes for all the offer pages
 * will be listed.
 * 
 * @author a.dhingra
 *
 */
public class OffersListingUse extends WCMUsePojo {

	/**
	 * Default Constructor
	 */
	public OffersListingUse() {
		super();
	}

	/**
	 * Logger instantiation
	 */
	protected static final Logger logger = LoggerFactory.getLogger(OffersListingUse.class);

	/**
	 * Variable that contains the JSON String
	 */
	private String jsonString;

	/**
	 * This method is used to construct the json for
	 * offersListing
	 * 
	 * @throws RepositoryException
	 * @throws JSONException
	 * 
	 */
	public void activate() throws JSONException, RepositoryException {
		logger.debug("Start:- activate() method of OffersListingUse");
		JSONObject object = new JSONObject();
		String requestPath = getRequest().getPathInfo();
		RequestPathInfo requestPathInfo = getRequest().getRequestPathInfo();
		
		if (requestPath.contains(HertzConstants.JCR_CONTENT) || requestPath.contains(HertzConstants.JCR_CONTENT_REQ)) {
			Node node = getResource().adaptTo(Node.class);
			StringWriter stringWriter = new StringWriter();
			JsonItemWriter jsonWriter = new JsonItemWriter(null);
			jsonWriter.dump(node, stringWriter, -1);
			object = new JSONObject(stringWriter.toString());
		} else {
			getAllSerchResult(requestPathInfo, object);
}
		setJsonString(object.toString());
		logger.debug("End:- activate() method of OffersListingUse");
	}

	

	private void getAllSerchResult(RequestPathInfo requestPathInfo, JSONObject object) {
		try {
		List<CategoryListingBean> categoryListingList=new ArrayList<>();
		SpaPageBean spaBean = new SpaPageBean();
		Resource resource = getResource();
		spaBean.setPageTitle(getCurrentPage().getTitle());
		HertzUtils.setSeoMetaDataInBean(spaBean, resource, requestPathInfo, getResourceResolver());
		Map<String, Object> configTextMap = new LinkedHashMap<>();
		SlingScriptHelper scriptHelper = getSlingScriptHelper();
		Resource parResource = resource.getChild(HertzConstants.CONFIGTEXT_PARSYS);
		if (null != parResource) {
			configTextMap = ConfigurableTextParSysExportor.getConfigTextParsysMap(parResource,
					getResourceResolver());
		}
		Map<String, String> predicateMap = new HashMap<String, String>();
		OfferUtils.preparePredicateMap(resource.getParent().getPath(), predicateMap);
		List<OfferDetailsBean> offerList = new ArrayList<>();
		JCRService jcrService = scriptHelper.getService(JCRService.class);
		
		if (null != jcrService) {
			SearchResult result = jcrService.searchResults(getResourceResolver(), predicateMap);
			for (Hit hit : result.getHits()) {
				Resource hitResource = hit.getResource();
				logger.debug("Path of hit Resource :- {}", hitResource.getPath());
				OfferDetailsBean offerDetails = new OfferDetailsBean();
				OfferUtils.setOfferDetailsBean(offerDetails, hitResource);
				offerList.add(offerDetails);
			}
			if(null==configTextMap){
				configTextMap=new LinkedHashMap<>();
			}
			
			//Setting all the offers in the offer node.
			if(!offerList.isEmpty()){
				configTextMap.put(HertzConstants.OFFERS, offerList);
			}
			//Getting all the category information 
			OfferUtils.getAllOfferListingProperties(resource, categoryListingList,true, null);
			if(!categoryListingList.isEmpty()){
				configTextMap.put(HertzConstants.CATEGORIES, categoryListingList);
			}
			if (MapUtils.isNotEmpty(configTextMap)) {
				spaBean.setConfiguredProps(configTextMap);
			}
			
		}
		Gson gson = HertzUtils.initGsonBuilder(true, true, true);
		
			object.accumulate(HertzConstants.SPA_CONTENT, new JSONObject(gson.toJson(spaBean, SpaPageBean.class)));
		} catch (JSONException | RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}



	/**
	 * @return the jsonString
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * @param jsonString
	 *            the jsonString to set
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}
