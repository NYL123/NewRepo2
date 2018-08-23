package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.collections.MapUtils;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.CategoryListingBean;
import com.hertz.digital.platform.bean.OfferDetailsBean;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.impl.ConfigurableTextParSysExportor;
import com.hertz.digital.platform.utils.HertzUtils;
import com.hertz.digital.platform.utils.OfferUtils;

/**
 * Use class method to get the properties of CategoryListing
 * 
 * @author puneet.soni
 *
 */
public class CategoryListingUse extends WCMUsePojo {

	/**
	 * Default Constructor
	 */
	public CategoryListingUse() {
		super();
	}

	/**
	 * Logger instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryListingUse.class);

	/**
	 * Variable that contains the JSON String
	 */
	private String jsonString;

	/**
	 * Active Method of OfferListingUse
	 */
	@Override
	public void activate() throws Exception {
		logger.debug("Start:- activate() of CategoryListingUse");
		Map<String, Object> configTextMap = null;
		List<CategoryListingBean> categoryListingList;
		JSONObject object = new JSONObject();
		ResourceResolver resourceResolver = getResourceResolver();
		Resource resource = getResource();
		RequestPathInfo requestPathInfo = getRequest().getRequestPathInfo();
		SpaPageBean spaPageBean = new SpaPageBean();
		spaPageBean.setPageTitle(getCurrentPage().getTitle());
		HertzUtils.setSeoMetaDataInBean(spaPageBean, resource, requestPathInfo, resourceResolver);

		try {
			categoryListingList=new ArrayList<CategoryListingBean>();
			String[] components = HertzUtils.toArray(get(HertzConstants.COMPONENTS, String.class),
					HertzConstants.COMMA);
			SlingScriptHelper scriptHelper = getSlingScriptHelper();
			Map<String, Object> includedComponents = HertzUtils.getIncludedComponentsAsBeans(requestPathInfo, resource, components,
					resourceResolver, scriptHelper);
			if (MapUtils.isNotEmpty(includedComponents)) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list.add(includedComponents);
				spaPageBean.setIncludedComponents(list);
			}

			Page curPage = getCurrentPage();
			if (curPage != null && curPage.hasContent()) {
				Resource configTextParSysResource = curPage.getContentResource(HertzConstants.CONFIGTEXT_PARSYS);
				if(null!=configTextParSysResource){
					configTextMap = ConfigurableTextParSysExportor.getConfigTextParsysMap(configTextParSysResource,
							getResourceResolver());
				}	
			}
			
			Map<String,List<OfferDetailsBean>> offerMap=OfferUtils.getOfferListMap(resource.getParent(),scriptHelper,resourceResolver);
			
			OfferUtils.getAllOfferListingProperties(resource, categoryListingList, false, offerMap);
			if(null!=configTextMap){
				configTextMap.put("offerCategory", categoryListingList);
			}
			

			if (MapUtils.isNotEmpty(configTextMap)) {
				spaPageBean.setConfiguredProps(configTextMap);
			}

			Gson gson = HertzUtils.initGsonBuilder(true, true, true);

			object.accumulate(HertzConstants.SPA_CONTENT, new JSONObject(gson.toJson(spaPageBean, SpaPageBean.class)));
			setJsonString(object.toString());
		} catch (RepositoryException | JSONException e) {
			logger.error("Error in CategoryListingUse class : {} : {}", e.getMessage(), e);
		}

		logger.debug("END:- activate() of CategoryListingUse");
	}
	
	/**
	 * Get value of jsonString
	 * 
	 * @return
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * 
	 * @param jsonString
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}


}
