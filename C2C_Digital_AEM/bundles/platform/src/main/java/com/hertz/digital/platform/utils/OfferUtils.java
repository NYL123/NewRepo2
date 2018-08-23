package com.hertz.digital.platform.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.CategoriesBean;
import com.hertz.digital.platform.bean.CategoryListingBean;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.bean.OfferCategoryBean;
import com.hertz.digital.platform.bean.OfferDetailsBean;
import com.hertz.digital.platform.bean.PartnerInfoBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.JCRService;

/**
 * This class contains the common methods for the offerdetail and the
 * offerlisting, categorylisting json.
 * 
 * @author a.dhingra
 *
 */
public final class OfferUtils {

	/**
	 * Constructor Declaration
	 */
	private OfferUtils() {
		// to prevent instantiation.
	}

	/** LOGGER instantiation. */
	private static final Logger logger = LoggerFactory.getLogger(HertzUtils.class);
	
	/** Constant for date pattern. */
	private static final String DATE_PATTERN = "MM/dd/yyyy";

	/**
	 * To set the offer details in the json
	 * 
	 * @param offerDetails
	 * @param resource
	 * @throws JSONException
	 */
	public static void setOfferDetailsBean(OfferDetailsBean offerDetails, Resource resource) throws JSONException {
		logger.debug("Start:- setOfferDetailsBean in OfferDetailsUse");
		ValueMap map = resource.getValueMap();
		offerDetails.setOfferName(resource.getParent().getName());
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		String widgetToDisplay = getPropertyValue(map, HertzConstants.WIDGET_TO_DISPLAY);
		attributesMap.put(HertzConstants.WIDGET_TO_DISPLAY, widgetToDisplay);
		if (StringUtils.equalsIgnoreCase(widgetToDisplay, HertzConstants.PARTNER_PROGRAM_VALUE)) {
			String[] partnerValues = PropertiesUtil.toStringArray(map.get(HertzConstants.CONFIGURABLE_PARTNER_VALUES));
			if (ArrayUtils.isNotEmpty(partnerValues)) {
				List<PartnerInfoBean> list = new ArrayList<>();
				for (String string : partnerValues) {
					setValuesInBean(string, list);
				}
				attributesMap.put(HertzConstants.PARTNER_INFO, list);
			}
		}
		attributesMap.put(HertzConstants.PC_CODE, getPropertyValue(map, HertzConstants.PC_CODE));
		attributesMap.put(HertzConstants.CDP_CODE, getPropertyValue(map, HertzConstants.CDP_CODE));
		attributesMap.put(HertzConstants.HEADLINE_FIRST, getPropertyValue(map, HertzConstants.HEADLINE));
		attributesMap.put(HertzConstants.HEADLINE_SECOND, getPropertyValue(map, HertzConstants.HEADLINE_SECOND));
		attributesMap.put(HertzConstants.SUBHEAD, getPropertyValue(map, HertzConstants.SUBHEAD));
		attributesMap.put(HertzConstants.CTA_LABEL_OFFER, getPropertyValue(map, HertzConstants.CTA_LABEL_OFFER));
		attributesMap.put(HertzConstants.CTA_HREF, getPropertyValue(map, HertzConstants.CTA_HREF));

		setImageInfoBean(resource, attributesMap);
		if (MapUtils.isNotEmpty(attributesMap)) {
			offerDetails.setAttributes(attributesMap);
		}
		setMetaDataNodeOfJSON(offerDetails, map);
		setCategoriesNodeOfJSON(offerDetails, map);
		setFilterNodeOfJSON(offerDetails, map);
		logger.debug("Exit:- setOfferDetailsBean in OfferDetailsUse");
	}
	
	/**
	 * This method sets the values in bean
	 * @param string
	 * @param list
	 * @throws JSONException
	 */
	public static void setValuesInBean(String string, List<PartnerInfoBean> list) throws JSONException{
		logger.debug("Start:- setValuesInBean in OfferDetailsUse");
		JSONObject object = new JSONObject(string);
		PartnerInfoBean bean = new PartnerInfoBean();
		if (null != object.optString(HertzConstants.PARTNER_NAME)) {
			bean.setPartnerName(object.getString(HertzConstants.PARTNER_NAME));
		}
		if (null != object.optString(HertzConstants.PARTNER_CDP_CODE)) {
			bean.setCdpCode(object.getString(HertzConstants.PARTNER_CDP_CODE));
		}
		list.add(bean);
		logger.debug("End:- setValuesInBean in OfferDetailsUse");
	}

	/**
	 * Gets the property value.
	 *
	 * @param map
	 *            the map
	 * @param key
	 *            the key
	 * @return the property value
	 */
	public static String getPropertyValue(ValueMap map, String key) {
		return map.containsKey(key) ? map.get(key, String.class) : StringUtils.EMPTY;
	}

	/**
	 * Sets the image info bean.
	 *
	 * @param resource
	 *            the resource
	 * @param attributesMap
	 *            the attributes map
	 */
	private static void setImageInfoBean(Resource resource, Map<String, Object> attributesMap) {
		if (resource.hasChildren()) {
			Resource imageResource = resource.getChild(HertzConstants.IMAGE);
			if (null != imageResource) {
				ImageInfoBean imageBean = new ImageInfoBean(imageResource);
				attributesMap.put(HertzConstants.IMAGE, imageBean);
			}
			Resource logoResource = resource.getChild(HertzConstants.LOGO);
			if (null != logoResource) {
				ImageInfoBean imageBean = new ImageInfoBean(logoResource);
				attributesMap.put(HertzConstants.LOGO, imageBean);
			}
			Resource badgeResource = resource.getChild(HertzConstants.BADGE);
			if (null != badgeResource) {
				ImageInfoBean imageBean = new ImageInfoBean(badgeResource);
				attributesMap.put(HertzConstants.BADGE, imageBean);
			}
		}
	}

	/**
	 * To set the categories node in the json
	 * 
	 * @param offerDetails
	 * @param map
	 * @throws JSONException
	 */
	private static void setCategoriesNodeOfJSON(OfferDetailsBean offerDetails, ValueMap map) throws JSONException {
		logger.debug("Start:- setCategoriesNodeOfJSON in OfferDetailsUse");
		List<CategoriesBean> list = new ArrayList<>();
		if (map.containsKey(HertzConstants.CATEGORY_PATH)) {
			String[] categories = map.get(HertzConstants.CATEGORY_PATH, String[].class);
			for (String string : categories) {
				CategoriesBean bean = new CategoriesBean();
				bean.setCategory(string);
				list.add(bean);
			}
		}
		if (list.size() != 0) {
			offerDetails.setCategories(list);
		}
		logger.debug("Exit:- setCategoriesNodeOfJSON in OfferDetailsUse");
	}

	/**
	 * To set the metadata attributes in the json
	 * 
	 * @param offerDetails
	 * @param map
	 */
	private static void setMetaDataNodeOfJSON(OfferDetailsBean offerDetails, ValueMap map) {
		logger.debug("Start:- setMetaDataNodeOfJSON in OfferDetailsUse");
		Map<String, Object> metaDataMap = new HashMap<>();
		metaDataMap.put(HertzConstants.POS_COUNTRIES, getPropertyValue(map, HertzConstants.POS_COUNTRIES));
		metaDataMap.put(HertzConstants.CHANNEL, getPropertyValue(map, HertzConstants.CHANNEL));
		metaDataMap.put(HertzConstants.CP_CODE, getPropertyValue(map, HertzConstants.CP_CODE));
		String startDate = getPropertyValue(map, HertzConstants.INDEX_PAGE_START_DATE);
		if (StringUtils.isNotBlank(startDate)) {
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(startDate);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
			metaDataMap.put(HertzConstants.INDEX_PAGE_START_DATE, formatter.format(zonedDateTime));
		}
		String pageExpiryDate = getPropertyValue(map, HertzConstants.INDEX_PAGE_EXPIRY_DATE);
		if (StringUtils.isNotBlank(pageExpiryDate)) {
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(pageExpiryDate);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
			metaDataMap.put(HertzConstants.INDEX_PAGE_EXPIRY_DATE, formatter.format(zonedDateTime));
		}
		String contentExpirDate = getPropertyValue(map, HertzConstants.CONTENT_EXPIRY_DATE);
		if (StringUtils.isNotBlank(contentExpirDate)) {
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(contentExpirDate);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
			metaDataMap.put(HertzConstants.CONTENT_EXPIRY_DATE, formatter.format(zonedDateTime));
		}
		if (map.containsKey(HertzConstants.CDP_RESTRICTION)) {
			metaDataMap.put(HertzConstants.CDP_RESTRICTION, map.get(HertzConstants.CDP_RESTRICTION, String.class));
		}
		if (MapUtils.isNotEmpty(metaDataMap)) {
			offerDetails.setMetaData(metaDataMap);
		}
		logger.debug("Exit:- setMetaDataNodeOfJSON in OfferDetailsUse");
	}

	/**
	 * To set the properties in the filter node of the json
	 * 
	 * @param offerDetails
	 * @param map
	 */
	private static void setFilterNodeOfJSON(OfferDetailsBean offerDetails, ValueMap map) {
		logger.debug("Start:- setFilterNodeOfJSON in OfferDetailsUse");
		List<Map<String, Object>> filterList = new ArrayList<>();
		Map<String, Object> filterMap = new HashMap<>();
		if (map.containsKey(HertzConstants.RANK)) {
			filterMap.put(HertzConstants.RANK, map.get(HertzConstants.RANK, Integer.class));
		}
		if (map.containsKey(HertzConstants.NEW_BURST_START_DATE)) {
			String date = map.get(HertzConstants.NEW_BURST_START_DATE, String.class);
			if (StringUtils.isNotBlank(date)) {
				ZonedDateTime zonedDateTime = ZonedDateTime.parse(date);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
				filterMap.put(HertzConstants.NEW_BURST_START_DATE, formatter.format(zonedDateTime));
			}

		}
		if (map.containsKey(HertzConstants.NEW_BURST_END_DATE)) {
			String date = map.get(HertzConstants.NEW_BURST_END_DATE, String.class);
			if (StringUtils.isNotBlank(date)) {
				ZonedDateTime zonedDateTime = ZonedDateTime.parse(date);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
				filterMap.put(HertzConstants.NEW_BURST_END_DATE, formatter.format(zonedDateTime));
			}

		}

		filterMap.put(HertzConstants.MEMBER_TYPE,
				PropertiesUtil.toString(map.get(HertzConstants.MEMBER_TYPE), StringUtils.EMPTY));
		if (MapUtils.isNotEmpty(filterMap)) {
			filterList.add(filterMap);
			offerDetails.setFilter(filterList);
		}
		logger.debug("Exit:- setFilterNodeOfJSON in OfferDetailsUse");
	}

	/**
	 * Get all the Offers from the hierarchy
	 * 
	 * @param resource
	 *            category Offers resource from hierarchy
	 * @param configTextMap
	 *            Map which holds the offer details
	 * @throws JSONException
	 *             JSONException
	 */
	public static void getAllOfferListingProperties(Resource resource, List<CategoryListingBean> categoryListingList,
			Boolean fromOfferListingPage, Map<String, List<OfferDetailsBean>> map) throws JSONException {
		Iterable<Resource> iterable = resource.getParent().getChildren();
		for (Resource subResource : iterable) {
			if (!subResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
				Page subPage = subResource.adaptTo(Page.class);
				if (subPage.getProperties().get(HertzConstants.JCR_CQ_TEMPLATE, String.class)
						.equalsIgnoreCase(HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH)) {
					CategoryListingBean categoryListingBean = getOfferListingProperties(subResource, map);
					setOfferListingPropertiesInBean(map, categoryListingBean, fromOfferListingPage, subResource);
					categoryListingList.add(categoryListingBean);
				}
			}
		}
	}
	
	/**
	 * This method sets offer listing props in bean
	 * @param map
	 * @param categoryListingBean
	 * @param fromOfferListingPage
	 * @param subResource
	 * @throws JSONException
	 */
	public static void setOfferListingPropertiesInBean(Map<String, List<OfferDetailsBean>> map, 
			CategoryListingBean categoryListingBean, Boolean fromOfferListingPage, 
			Resource subResource) throws JSONException{
		if (!fromOfferListingPage) {
			getOfferListingResourceProperties(subResource, categoryListingBean, map);
		}
	}

	/**
	 * Get all the Sub Category Offers from the hierarchy
	 * 
	 * @param resource
	 *            Sub Category Offers resource from hierarchy
	 * @param categoryListingBean
	 *            Bean that holds the Sub category offer details
	 * @throws JSONException
	 *             JSONException
	 */
	private static void getOfferListingResourceProperties(Resource resource, CategoryListingBean categoryListingBean,
			Map<String, List<OfferDetailsBean>> map) throws JSONException {
		Iterable<Resource> resourceIterator = resource.getChildren();
		for (Resource subResource : resourceIterator) {
			if (!subResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
				Page subPage = subResource.adaptTo(Page.class);
				if (subPage.getProperties().get(HertzConstants.JCR_CQ_TEMPLATE, String.class)
						.equalsIgnoreCase(HertzConstants.OFFER_CATEGORY_TEMPLATE_PATH)) {
					OfferCategoryBean categoryBean = getSubOfferCategoryProperties(subResource, map);
					categoryListingBean.setSubCategory(categoryBean);
				}
			}
		}
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
	private static CategoryListingBean getOfferListingProperties(Resource resource, Map<String, 
			List<OfferDetailsBean>> map) throws JSONException {
		logger.debug("START:- getOfferCategoryProperties() of OfferCategoryUse");
		Resource subJCRResource = resource.getChild(HertzConstants.JCR_CONTENT);
		ValueMap componentProperties = subJCRResource.getValueMap();
		OfferCategoryBean offerCategoryBean = new OfferCategoryBean();
		CategoryListingBean categoryListingBean = new CategoryListingBean();
		if (subJCRResource.hasChildren()) {
			Resource fileImageResource = (Resource) subJCRResource.getChild(HertzConstants.IMAGE);
			if (null != fileImageResource) {
				offerCategoryBean.setImage(new ImageInfoBean(fileImageResource));
			}
			Resource fileLogoResource = (Resource) subJCRResource.getChild(HertzConstants.LOGO);
			if (null != fileLogoResource) {
				offerCategoryBean.setLogo(new ImageInfoBean(fileLogoResource));
			}
			Resource fileBadgeResource = (Resource) subJCRResource.getChild(HertzConstants.BADGE);
			if (null != fileBadgeResource) {
				offerCategoryBean.setBadge(new ImageInfoBean(fileBadgeResource));
			}
		}
		String category = StringUtils.substringAfterLast(resource.getPath(), "/");
		offerCategoryBean.setCtaAction(getPropertyValue(componentProperties, HertzConstants.CTA_LABEL));
		offerCategoryBean.setCtaLabel(getPropertyValue(componentProperties, HertzConstants.CTA_ACTION));
		
		offerCategoryBean.setHeadline(getPropertyValue(componentProperties, HertzConstants.HEADLINE));
		offerCategoryBean.setHeadlineSecondLine(getPropertyValue(componentProperties, HertzConstants.HEADLINE_SECOND));
        
		offerCategoryBean.setSubhead(getPropertyValue(componentProperties, HertzConstants.SUBHEAD));
		if (null != map && map.containsKey(category)) {
			offerCategoryBean.setOffers(map.get(category));
		}
		categoryListingBean.setAttributes(offerCategoryBean);
		categoryListingBean.setRank(getPropertyValue(componentProperties, HertzConstants.RANK));
		categoryListingBean.setDb2Category(getPropertyValue(componentProperties, HertzConstants.DB2_CATEGORY));
		categoryListingBean.setContentPath(resource.getPath());
		categoryListingBean.setName(
				componentProperties.containsKey(HertzConstants.CATEGORYNAME) ? resource.getName() : StringUtils.EMPTY);
		logger.debug("END:- getOfferCategoryProperties() of OfferCategoryUse");
		return categoryListingBean;
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
	private static OfferCategoryBean getSubOfferCategoryProperties(Resource resource,
			Map<String, List<OfferDetailsBean>> map) throws JSONException {
		logger.debug("START:- getSubOfferCategoryProperties() of OfferCategoryUse");
		Resource subJCRResource = resource.getChild(HertzConstants.JCR_CONTENT);
		ValueMap componentProperties = subJCRResource.getValueMap();
		OfferCategoryBean offerCategoryBean = new OfferCategoryBean();
		if (subJCRResource.hasChildren()) {
			Resource fileImageResource = (Resource) subJCRResource.getChild(HertzConstants.IMAGE);
			if (null != fileImageResource) {
				offerCategoryBean.setImage(new ImageInfoBean(fileImageResource));
			}
			Resource fileLogoResource = (Resource) subJCRResource.getChild(HertzConstants.LOGO);
			if (null != fileLogoResource) {
				offerCategoryBean.setLogo(new ImageInfoBean(fileLogoResource));
			}
			Resource fileBadgeResource = (Resource) subJCRResource.getChild(HertzConstants.BADGE);
			if (null != fileBadgeResource) {
				offerCategoryBean.setBadge(new ImageInfoBean(fileBadgeResource));
			}
		}
		String subCategory = StringUtils.substringAfterLast(resource.getPath(), "/");
		offerCategoryBean.setCtaAction(getPropertyValue(componentProperties, HertzConstants.CTA_LABEL));
		offerCategoryBean.setCtaLabel(getPropertyValue(componentProperties, HertzConstants.CTA_ACTION));
		offerCategoryBean.setHeadline(getPropertyValue(componentProperties, HertzConstants.HEADLINE));
		offerCategoryBean.setHeadline(getPropertyValue(componentProperties, HertzConstants.HEADLINE_SECOND));
		offerCategoryBean.setSubhead(getPropertyValue(componentProperties, HertzConstants.SUBHEAD));
		offerCategoryBean.setDb2Category(getPropertyValue(componentProperties, HertzConstants.DB2_CATEGORY));
		offerCategoryBean.setContentPath(resource.getPath());
		if (null != map && map.containsKey(subCategory)) {
			offerCategoryBean.setOffers(map.get(subCategory));
		}
		logger.debug("END:- getSubOfferCategoryProperties() of OfferCategoryUse");
		return offerCategoryBean;
	}

	/**
	 * This method prepares the predicate map for the search
	 * 
	 * @param parentPath
	 * @param predicateMap
	 */
	public static void preparePredicateMap(String parentPath, Map<String, String> predicateMap) {
		logger.debug("Start :-preparePredicateMap() method in OffersListingUse ");
		predicateMap.put(HertzConstants.TYPE, HertzConstants.CQ_PAGE_CONTENT);
		predicateMap.put(HertzConstants.PATH, parentPath);
		predicateMap.put("property", HertzConstants.SLING_RESOURCE_TYPE);
		predicateMap.put("property.value", HertzConstants.OFFER_DETAILS_PAGE);
		predicateMap.put(HertzConstants.LIMIT, "-1");
		logger.debug("Exit :-preparePredicateMap() method in OffersListingUse ");
	}

	/**
	 * @param resource
	 * @param scriptHelper
	 * @param resourceResolver
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public static Map<String, List<OfferDetailsBean>> getOfferCategoryMap(Resource resource,
			SlingScriptHelper scriptHelper, ResourceResolver resourceResolver)
			throws RepositoryException, JSONException {
		Map<String, List<OfferDetailsBean>> map = new HashMap<>();
		Map<String, String> predicateMap = new HashMap<String, String>();
		Resource parentResource = resource.getParent().getParent();
		OfferUtils.preparePredicateMap(parentResource.getPath(), predicateMap);
		JCRService jcrService = scriptHelper.getService(JCRService.class);
		if (null != jcrService) {
			SearchResult result = jcrService.searchResults(resourceResolver, predicateMap);
			for (Hit hit : result.getHits()) {
				Resource hitResource = hit.getResource();
				ValueMap valueMap = hitResource.getValueMap();
				if (valueMap.containsKey(HertzConstants.CATEGORY_PATH)) {
					setOfferCategoryInBean(valueMap, hitResource, resource, map);	
			    }
		    }
        }
		return map;
    }
	
	/**
	 * This method sets offer category in bean
	 * @param valueMap
	 * @param hitResource
	 * @param resource
	 * @param map
	 * @throws JSONException
	 */
	public static void setOfferCategoryInBean(ValueMap valueMap, Resource hitResource, 
			Resource resource, Map<String, List<OfferDetailsBean>> map) throws JSONException{
		String[] categories = valueMap.get(HertzConstants.CATEGORY_PATH, String[].class);
		for (String category : categories) {
			String categoryName = StringUtils.substringAfterLast(category, "/");
			String category1 =StringUtils.substringBeforeLast(category, "/");
			OfferDetailsBean detailBean = new OfferDetailsBean();
			OfferUtils.setOfferDetailsBean(detailBean, hitResource);
			if(resource.getPath().contains(category1)){
			   if (map.containsKey(categoryName)) {
				  List<OfferDetailsBean> offerList = map.get(categoryName);
				  offerList.add(detailBean);
				  map.put(categoryName, offerList);
			   } else {
				   List<OfferDetailsBean> offerDetailBean = new ArrayList<>();
				   offerDetailBean.add(detailBean);
				   map.put(categoryName, offerDetailBean);
			   }
			
		    }
	    }
	}
	
	/**
	 * @param resource
	 * @param scriptHelper
	 * @param resourceResolver
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public static Map<String, List<OfferDetailsBean>> getOfferListMap(Resource resource,
			SlingScriptHelper scriptHelper, ResourceResolver resourceResolver)
			throws RepositoryException, JSONException {
		Map<String, List<OfferDetailsBean>> map = new HashMap<>();
		Map<String, String> predicateMap = new HashMap<String, String>();
		OfferUtils.preparePredicateMap(resource.getPath(), predicateMap);
		JCRService jcrService = scriptHelper.getService(JCRService.class);
		if (null != jcrService) {
			SearchResult result = jcrService.searchResults(resourceResolver, predicateMap);

			for (Hit hit : result.getHits()) {
				Resource hitResource = hit.getResource();
				ValueMap valueMap = hitResource.getValueMap();
				if (valueMap.containsKey(HertzConstants.CATEGORY_PATH)) {
					setOfferListInBean(valueMap, hitResource, map);
				}
			}
		}
		return map;
	}
	
	/**
	 * This method sets offer list in bean
	 * @param valueMap
	 * @param hitResource
	 * @param map
	 * @throws JSONException
	 */
	public static void setOfferListInBean(ValueMap valueMap, Resource hitResource, 
			Map<String, List<OfferDetailsBean>> map) throws JSONException{
		String[] categories = valueMap.get(HertzConstants.CATEGORY_PATH, String[].class);
		for (String category : categories) {
			String categoryName = StringUtils.substringAfterLast(category, "/");
			OfferDetailsBean detailBean = new OfferDetailsBean();
			OfferUtils.setOfferDetailsBean(detailBean, hitResource);
			if (map.containsKey(categoryName)) {
				List<OfferDetailsBean> offerList = map.get(categoryName);
				offerList.add(detailBean);
				map.put(categoryName, offerList);
			} else {
				List<OfferDetailsBean> offerDetailBean = new ArrayList<>();
				offerDetailBean.add(detailBean);
				map.put(categoryName, offerDetailBean);
			}
		}
	}
	
}
