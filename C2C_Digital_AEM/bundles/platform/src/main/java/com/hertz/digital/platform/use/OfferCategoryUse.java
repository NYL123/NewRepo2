package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.bean.OfferCategoryBean;
import com.hertz.digital.platform.bean.OfferDetailsBean;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;
import com.hertz.digital.platform.utils.OfferUtils;

/**
 * Use class method to get the properties of OfferCategoryUse
 * 
 * @author puneet.soni
 *
 */
public class OfferCategoryUse extends WCMUsePojo {

    /**
     * Default Constructor
     */
    public OfferCategoryUse() {
        super();
    }

    /**
     * Logger instantiation
     */
	private static final Logger logger = LoggerFactory.getLogger(OfferCategoryUse.class);

    /**
     * Variable that contains the JSON String
     */
    private String jsonString;

    /**
     * Active Method of OfferCategoryUse
     */
    @Override
    public void activate() throws Exception {
        logger.debug("Start:- activate() of OfferCategoryUse");
        Map<String, Object> map = new HashMap<>();
        JSONObject object = new JSONObject();
        ResourceResolver resourceResolver = getResourceResolver();
        Resource resource = getResource();
        Page currentPage = getCurrentPage();
        RequestPathInfo requestPathInfo = getRequest().getRequestPathInfo();
        SpaPageBean spaPageBean = new SpaPageBean();
		spaPageBean.setPageTitle(getCurrentPage().getTitle());
		HertzUtils.setSeoMetaDataInBean(spaPageBean, resource, requestPathInfo, resourceResolver);

		try {
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
			Map<String, List<OfferDetailsBean>> offerMap = OfferUtils
					.getOfferCategoryMap(resource, scriptHelper, resourceResolver);
			getAllOfferCategoryProperties(currentPage, map, offerMap);

            if (MapUtils.isNotEmpty(map)) {
                spaPageBean.setConfiguredProps(map);
            }

			Gson gson = HertzUtils.initGsonBuilder(true, true, true);

			object.accumulate(HertzConstants.SPA_CONTENT, new JSONObject(gson.toJson(spaPageBean, SpaPageBean.class)));
			setJsonString(object.toString());
		} catch (RepositoryException | JSONException e) {
			logger.error("Error in OfferCategoryUse class : {} : {}", e.getMessage(), e);
		}

		logger.debug("END:- activate() of OfferCategoryUse");
	}

	private void getAllOfferCategoryProperties(Page currentPage, Map<String, Object> configTextMap,
			Map<String, List<OfferDetailsBean>> offerMap) throws JSONException {
		Gson gson = HertzUtils.initGsonBuilder(true, true, true);
        Resource resource = currentPage.adaptTo(Resource.class);
        getOfferCategoryProperties(getResource(), configTextMap, offerMap);
        JSONObject object1 = new JSONObject();
		List<OfferCategoryBean> list = new ArrayList<>();
		Iterable<Resource> resourceIterator = resource.getChildren();

		Map<String, Object> subconfigTextMap = new HashMap<>();
		for (Resource subResource : resourceIterator) {
			if (!subResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
				Page subPage = subResource.adaptTo(Page.class);
				if (subPage.getProperties().get(HertzConstants.JCR_CQ_TEMPLATE, String.class)
						.equalsIgnoreCase(HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH)) {
					Resource subJCRResource = subResource.getChild(HertzConstants.JCR_CONTENT);
					OfferCategoryBean bean = getSubOfferCategoryProperties(subJCRResource, subconfigTextMap, offerMap);
					object1.accumulate("subCategory", new JSONObject(gson.toJson(bean, OfferCategoryBean.class)));
                    list.add(bean);
                }
            }
        }

        configTextMap.put("subCategory", list);
    }

    /**
     * To get the Properties of Offer Category Page Template
     * 
     * @param resource
     *            Page resource of Offer Category template
     * @param configTextMap
	 *            Map that saves the values of Properties of Offer Category
	 * @throws JSONException
	 *             JSONException
	 */
	private void getOfferCategoryProperties(Resource resource, Map<String, Object> configTextMap,
			Map<String, List<OfferDetailsBean>> offerMap) throws JSONException {
		logger.debug("START:- getOfferCategoryProperties() of OfferCategoryUse");
		ValueMap componentProperties = resource.getValueMap();
		HashMap<String, Object> attributesMap = new HashMap<>();
		String categoryName = StringUtils.EMPTY;
		String db2Category=StringUtils.EMPTY;
		categoryName = resource.getParent().getName();
		if(componentProperties.containsKey(HertzConstants.DB2_CATEGORY)){
			db2Category=componentProperties.get(HertzConstants.DB2_CATEGORY,String.class);
		}
		if (resource.hasChildren()) {
			Resource fileImageResource = (Resource) resource.getChild(HertzConstants.IMAGE);
			if (null != fileImageResource) {
				attributesMap.put("image", new ImageInfoBean(fileImageResource));
			}
			Resource fileLogoResource = (Resource) resource.getChild(HertzConstants.LOGO);
			if (null != fileLogoResource) {
				attributesMap.put("logo", new ImageInfoBean(fileLogoResource));
			}
			Resource fileBadgeResource = (Resource) resource.getChild(HertzConstants.BADGE);
			if (null != fileBadgeResource) {
				attributesMap.put("badge", new ImageInfoBean(fileBadgeResource));
			}
        }
        attributesMap.put(HertzConstants.HEADLINE_FIRST,
                OfferUtils.getPropertyValue(componentProperties,
                        HertzConstants.HEADLINE));
        attributesMap.put(HertzConstants.HEADLINE_SECOND,
                OfferUtils.getPropertyValue(componentProperties,
                        HertzConstants.HEADLINE_SECOND));
        attributesMap.put(HertzConstants.SUBHEAD, OfferUtils
                .getPropertyValue(componentProperties, HertzConstants.SUBHEAD));
		attributesMap.put(HertzConstants.CTA_LABEL_OFFER,
				OfferUtils.getPropertyValue(componentProperties, HertzConstants.CTA_LABEL));
		attributesMap.put(HertzConstants.CTA_HREF,
				OfferUtils.getPropertyValue(componentProperties, HertzConstants.CTA_ACTION));
        if (null != offerMap && offerMap.containsKey(categoryName)) {
            attributesMap.put("offers", offerMap.get(categoryName));
        }
        Map<String, Object> attributesObject = new HashMap<>();
        attributesObject.put(HertzConstants.ATTRIBUTES, attributesMap);
        attributesObject.put(HertzConstants.NAME, categoryName);
        attributesObject.put(HertzConstants.DB2_CATEGORY, db2Category);
        attributesObject.put(HertzConstants.CONTENT_PATH, resource.getParent().getPath());
        configTextMap.put(HertzConstants.OFFER_CATEGORY, attributesObject);
        logger.debug("END:- getOfferCategoryProperties() of OfferCategoryUse");
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

    /**
     * To get the Properties of Offer Category Page Template
     * 
     * @param resource
     *            Page resource of Offer Category template
     * @param configTextMap
     *            Map that saves the values of Properties of Offer Category
	 * @throws JSONException
	 *             JSONException
	 */
	private OfferCategoryBean getSubOfferCategoryProperties(Resource resource, Map<String, Object> configTextMap,
			Map<String, List<OfferDetailsBean>> offerMap) throws JSONException {
		logger.debug("START:- getSubOfferCategoryProperties() of OfferCategoryUse");
		ValueMap componentProperties = resource.getValueMap();
		String subCategoryName = resource.getParent().getName();
		OfferCategoryBean offerCategoryBean = new OfferCategoryBean();
		
		if (resource.hasChildren()) {
			Resource fileImageResource = (Resource) resource.getChild(HertzConstants.IMAGE);
			if (null != fileImageResource) {
				offerCategoryBean.setImage(new ImageInfoBean(fileImageResource));
			}
			Resource fileLogoResource = (Resource) resource.getChild(HertzConstants.LOGO);
			if (null != fileLogoResource) {
				offerCategoryBean.setLogo(new ImageInfoBean(fileLogoResource));
			}
			Resource fileBadgeResource = (Resource) resource.getChild(HertzConstants.BADGE);
			if (null != fileBadgeResource) {
				offerCategoryBean.setBadge(new ImageInfoBean(fileBadgeResource));
			}
        }
		offerCategoryBean.setDb2Category(OfferUtils.getPropertyValue(componentProperties, HertzConstants.DB2_CATEGORY));
		offerCategoryBean.setContentPath(resource.getParent().getPath());
        offerCategoryBean.setCtaAction(OfferUtils.getPropertyValue(componentProperties, HertzConstants.CTA_LABEL));
        offerCategoryBean.setCtaLabel(OfferUtils.getPropertyValue(componentProperties, HertzConstants.CTA_ACTION));
        offerCategoryBean.setHeadline(OfferUtils.getPropertyValue(
                componentProperties, HertzConstants.HEADLINE));
        offerCategoryBean.setHeadlineSecondLine(OfferUtils.getPropertyValue(
                componentProperties, HertzConstants.HEADLINE_SECOND));
        offerCategoryBean.setSubhead(OfferUtils.getPropertyValue(componentProperties, HertzConstants.SUBHEAD));
        if (null != offerMap && offerMap.containsKey(subCategoryName)) {
			offerCategoryBean.setOffers(offerMap.get(subCategoryName));
		}
		logger.debug("END:- getSubOfferCategoryProperties() of OfferCategoryUse");
		return offerCategoryBean;
	}

}
