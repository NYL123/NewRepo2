package com.hertz.digital.platform.service.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.CountryLanguageItemsBean;
import com.hertz.digital.platform.bean.HeaderCountryLangLoginRedirectItemsBean;
import com.hertz.digital.platform.bean.HeaderCountryLanguageItemsBean;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.bean.LanguageItemsBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.CountryLanguageSelectorService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * This is the service Impl class to generate the JSON for the country language selector
 * service that will be rendered to FED.
 *
 * @author himanshu.i.sharma
 */

@Component(metatype = false, label = "Hertz - Country Language Selector Service", description = "Hertz - Service to render json for the country language")
@Service
public class CountryLanguageSelectorServiceImpl implements CountryLanguageSelectorService {
	/**
	 * Default Constructor
	 */
	public CountryLanguageSelectorServiceImpl() {
		super();
	}

	/**
	 * Instantiating Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CountryLanguageSelectorServiceImpl.class);

	public HeaderCountryLanguageItemsBean getHeaderCountryLanguageJson(Resource currentPageResource) {
		HeaderCountryLanguageItemsBean headerCountryLanguageItemsBean = new HeaderCountryLanguageItemsBean();
		String countryLanguageResPath = HertzConstants.JCR_CONTENT + "/" + HertzConstants.PAR + "/" + HertzConstants.COUNTRYLANGUAGE;
		if(currentPageResource.getChild(countryLanguageResPath) != null) {
			Resource countrylanguageResource = currentPageResource.getChild(countryLanguageResPath);
			getCountryLanguageLabelProperties(countrylanguageResource, headerCountryLanguageItemsBean);
		}
		return headerCountryLanguageItemsBean;
	}
	
	public HeaderCountryLangLoginRedirectItemsBean getHeaderCountryLanguageRedirectJson(Resource currentPageResource) {
		HeaderCountryLangLoginRedirectItemsBean headerCountryLangLoginRedirectItemsBean= new HeaderCountryLangLoginRedirectItemsBean();
		String countryLanguageResPath = HertzConstants.JCR_CONTENT + "/" + HertzConstants.PAR + "/" + HertzConstants.COUNTRYLANGUAGEREDIRECT;
		if(currentPageResource.getChild(countryLanguageResPath) != null) {
			Resource countrylanguageResource = currentPageResource.getChild(countryLanguageResPath);
			getCountryLanguageLabelProperties(countrylanguageResource, headerCountryLangLoginRedirectItemsBean);
		}
		return headerCountryLangLoginRedirectItemsBean;
	}
	
	/**
	 * This method gets the child country pages
	 * 
	 * @param currentPageResource
	 * @param countryLanguageItemsBeanList
	 */
	public List<CountryLanguageItemsBean> getCountryLanguageJson(Resource currentPageResource) {
		logger.debug("Start:- getCountryLanguageJson() of CountryLanguageSelectorServiceImpl");
		List<CountryLanguageItemsBean> countryLanguageItemsBeanList = new ArrayList<CountryLanguageItemsBean>();
		List<String> countryPagesList = new ArrayList<String>();
		Iterable<Resource> countryLevelResources = currentPageResource.getChildren();
		for (Resource countryLevelResource : countryLevelResources) {
			if (!countryLevelResource.getName().equalsIgnoreCase(HertzConstants.JCR_CONTENT)) {
				countryPagesList.add(countryLevelResource.getName());
				Collections.sort(countryPagesList);
			}
		}
		for (String countryName : countryPagesList) {
			Resource countryLevelResource = currentPageResource.getChild(countryName);
			getCountriesPages(countryLevelResource, countryLanguageItemsBeanList);
		}
		
		logger.debug("End:- getCountryLanguageJson() of CountryLanguageSelectorServiceImpl");
		String locale = HertzUtils.getLocaleFromPath(currentPageResource.getPath());
		Collections.sort(countryLanguageItemsBeanList, new Comparator<CountryLanguageItemsBean>() {
			private Collator trCollator = Collator
					.getInstance(new Locale(locale, StringUtils.capitalize(locale)));

			@Override
			public int compare(CountryLanguageItemsBean o1, CountryLanguageItemsBean o2) {
				return trCollator.compare(o1.getCountryName(), o2.getCountryName());
			}
		});
		return countryLanguageItemsBeanList;
	}

	/**
	 * This method gets the child language pages and sets the values in bean
	 * 
	 * @param countryLevelResource
	 * @param countryLanguageItemsBeanList
	 */
	private void getCountriesPages(Resource countryLevelResource,
			List<CountryLanguageItemsBean> countryLanguageItemsBeanList) {
		logger.debug("Start:- getCountriesPages() of CountryLanguageSelectorServiceImpl");
		CountryLanguageItemsBean countryLanguageItemsBean = new CountryLanguageItemsBean();
		List<LanguageItemsBean> languageItemsBeanList = new ArrayList<>();
		Resource languageJcrContentResource = null;
		Resource parResource = null;
		Resource languageResource = null;
		String countryCode = countryLevelResource.getName();
		Page countryLevelPage = countryLevelResource.adaptTo(Page.class);
		String countryName = countryLevelPage.getProperties().get(HertzConstants.JCR_TITLE_PROPERTY, String.class);
		if (null != countryLevelResource.getChild(HertzConstants.JCR_CONTENT)) {
			setCountryLanguageItemBean(countryLevelResource, countryLanguageItemsBean);
		}
		if (null != countryLevelResource.getChild(HertzConstants.LANGUAGES)) {
			languageResource = countryLevelResource.getChild(HertzConstants.LANGUAGES);
			languageJcrContentResource = languageResource.getChild(HertzConstants.JCR_CONTENT);
			if (null != languageJcrContentResource.getChild(HertzConstants.PAR)) {
				parResource = languageJcrContentResource.getChild(HertzConstants.PAR);
				getLanguageProperties(parResource, languageItemsBeanList);
				countryLanguageItemsBean.setCountryName(countryName);
				countryLanguageItemsBean.setCountryCode(countryCode);
				String locale = HertzUtils.getLocaleFromPath(countryLevelResource.getPath());
				Collections.sort(languageItemsBeanList, new Comparator<LanguageItemsBean>() {
					private Collator trCollator = Collator
							.getInstance(new Locale(locale, StringUtils.capitalize(locale)));

					@Override
					public int compare(LanguageItemsBean o1, LanguageItemsBean o2) {
						return trCollator.compare(o1.getLanguageName(), o2.getLanguageName());
					}
				});
				countryLanguageItemsBean.setLanguageItemsBeanList(languageItemsBeanList);
				countryLanguageItemsBeanList.add(countryLanguageItemsBean);
			}
		}
		logger.debug("End:- getCountriesPages() of CountryLanguageSelectorServiceImpl");
	}

	/**
	 * Sets the country language item bean.
	 *
	 * @param countryLevelResource the country level resource
	 * @param countryLanguageItemsBean the country language items bean
	 */
	private void setCountryLanguageItemBean(Resource countryLevelResource,
			CountryLanguageItemsBean countryLanguageItemsBean) {
		Resource countryJcrContentResource = countryLevelResource.getChild(HertzConstants.JCR_CONTENT);
		if(null != countryJcrContentResource.getChild(HertzConstants.PAR)) {
			Resource par = countryJcrContentResource.getChild(HertzConstants.PAR);
			if(null != par.getChild(HertzConstants.TEXTFIELD)) {
				Resource domainResource = par.getChild(HertzConstants.TEXTFIELD);
				ValueMap componentProperties = domainResource.getValueMap();
				//countryLanguageItemsBean.setDomain(componentProperties.get("textfield", String.class));
				
			}
			if(null != par.getChild(HertzConstants.COUNTRY_CONFIG_COMP)) {
				Resource countryConfigcompResource = par.getChild(HertzConstants.COUNTRY_CONFIG_COMP);
				ValueMap componentProperties = countryConfigcompResource.getValueMap();
				countryLanguageItemsBean.setIsirac(componentProperties.get(HertzConstants.IS_IRAC, Boolean.class));
				countryLanguageItemsBean.setPos(componentProperties.get(HertzConstants.POS, String.class));
			}
			if(null != par.getChild(HertzConstants.IMAGE_PAIR)) {
				Resource countryConfigcompResource = par.getChild(HertzConstants.IMAGE_PAIR);
				ValueMap componentProperties = countryConfigcompResource.getValueMap();
		        String alt = componentProperties.get(HertzConstants.ALT, String.class);
		        String fileReferencePath = componentProperties.get(HertzConstants.FILE_REFERENCE, String.class);
		        if (StringUtils.isNotBlank(fileReferencePath)) {
		        	ImageInfoBean imageInfo = new ImageInfoBean(countryConfigcompResource);
		            imageInfo.setTitle(alt);
		            countryLanguageItemsBean.setSources(imageInfo.getSources());
		        }
		    }
		}
	}
	
	/**
	 * 
	 * @param countryLanguageResource
	 * @param headerCountryLanguageItemsBean
	 * @param headerCountryLangLoginRedirectItemsBean
	 */
	private void getCountryLanguageLabelProperties(Resource countryLanguageResource, HeaderCountryLanguageItemsBean headerCountryLanguageItemsBean) {
		logger.debug("Start:- getCountryLanguageLabelProperties() of CountryLanguageSelectorServiceImpl");
		ValueMap componentProperties = countryLanguageResource.getValueMap();
		if (componentProperties.containsKey(HertzConstants.COUNTRY_LABEL)) {
			headerCountryLanguageItemsBean
					.setCountryLabel(componentProperties.get(HertzConstants.COUNTRY_LABEL, String.class));
		}
		if (componentProperties.containsKey(HertzConstants.LANGUAGE_LABEL)) {
			headerCountryLanguageItemsBean
					.setLanguageLabel(componentProperties.get(HertzConstants.LANGUAGE_LABEL, String.class));
		}
		if (componentProperties.containsKey(HertzConstants.BUTTON_LABEL)) {
			headerCountryLanguageItemsBean
					.setUpdateLabel(componentProperties.get(HertzConstants.BUTTON_LABEL, String.class));
		}
		if (componentProperties.containsKey(HertzConstants.UPDATE_MESSAGE)) {
			headerCountryLanguageItemsBean
					.setUpdateMessage(componentProperties.get(HertzConstants.UPDATE_MESSAGE, String.class));
		}
		setParamsInBean(headerCountryLanguageItemsBean, componentProperties);
		logger.debug("End:- getCountryLanguageLabelProperties() of CountryLanguageSelectorServiceImpl");
	}
	
	/**
	 * 
	 * @param countryLanguageResource
	 * @param headerCountryLanguageItemsBean
	 * @param headerCountryLangLoginRedirectItemsBean
	 */
	private void getCountryLanguageLabelProperties(Resource countryLanguageResource, HeaderCountryLangLoginRedirectItemsBean headerCountryLangLoginRedirectItemsBean) {
		logger.debug("Start:- getCountryLanguageLabelProperties() of CountryLanguageSelectorServiceImpl");
		ValueMap componentProperties = countryLanguageResource.getValueMap();
		setParamsInBeanLoginRedirect(headerCountryLangLoginRedirectItemsBean, componentProperties);
		logger.debug("End:- getCountryLanguageLabelProperties() of CountryLanguageSelectorServiceImpl");
	}
	/**
	 * @param headerCountryLangLoginRedirectItemsBean
	 * @param componentProperties
	 */
	private void setParamsInBeanLoginRedirect(HeaderCountryLangLoginRedirectItemsBean headerCountryLangLoginRedirectItemsBean,ValueMap componentProperties) {
	if (componentProperties.containsKey(HertzConstants.REDIRECT_HEADING)) {
					headerCountryLangLoginRedirectItemsBean.setRedirectHeading(componentProperties.get(HertzConstants.REDIRECT_HEADING, String.class));
	}
	if (componentProperties.containsKey(HertzConstants.REDIRECT_MESSAGE)) {
		headerCountryLangLoginRedirectItemsBean.setRedirectMessage(componentProperties.get(HertzConstants.REDIRECT_MESSAGE, String.class));
	}
	if (componentProperties.containsKey(HertzConstants.CANCEL_BUTTON_LABEL)) {
		headerCountryLangLoginRedirectItemsBean
				.setCancelButton(componentProperties.get(HertzConstants.CANCEL_BUTTON_LABEL, String.class));
	}
	if (componentProperties.containsKey(HertzConstants.CONTINUE_BUTTON_LABEL)) {
		headerCountryLangLoginRedirectItemsBean
				.setContinueButton(componentProperties.get(HertzConstants.CONTINUE_BUTTON_LABEL, String.class));
	}
		
	}

	/**
	 * @param headerCountryLanguageItemsBean
	 * @param componentProperties
	 */
	private void setParamsInBean(HeaderCountryLanguageItemsBean headerCountryLanguageItemsBean,
			ValueMap componentProperties) {
		if (componentProperties.containsKey(HertzConstants.NO_RESULTS_MESSAGE)) {
			headerCountryLanguageItemsBean
					.setNoResultsMessage(componentProperties.get(HertzConstants.NO_RESULTS_MESSAGE, String.class));
		}

	}

	/**
	 * This method reads the properties of language components
	 * 
	 * @param parResource
	 * @param languageItemsBeanList
	 */
	private void getLanguageProperties(Resource parResource, List<LanguageItemsBean> languageItemsBeanList) {
		logger.debug("Start:- getLanguageProperties() of CountryLanguageSelectorServiceImpl");
		Boolean isDefaultLanguage;
		Boolean useLocaleInUrl;
		ValueMap componentProperties = null;
		//String strLocale = StringUtils.EMPTY;
		String strLanguageName = StringUtils.EMPTY;
		String strLanguageCode = StringUtils.EMPTY;
		Boolean irac;
		String strIracLink = StringUtils.EMPTY; 		
		//String strUrl = StringUtils.EMPTY;
		Iterable<Resource> languageComponents = parResource.getChildren();
		for (Resource componentResource : languageComponents) {
			componentProperties = componentResource.getValueMap();
			//strLocale = HertzUtils.getValueFromMap(componentProperties, HertzConstants.LOCALE).trim();
			strLanguageName = HertzUtils.getValueFromMap(componentProperties, HertzConstants.LANGUAGE_NAME).trim();
			strLanguageCode = HertzUtils.getValueFromMap(componentProperties, HertzConstants.LANGUAGE_CODE).trim();
			isDefaultLanguage = HertzUtils.getBooleanValueFromMap(componentProperties, HertzConstants.IS_DEFAULT_LANGUAGE);
			//strUrl = HertzUtils.getValueFromMap(componentProperties, HertzConstants.URL).trim();
			useLocaleInUrl = HertzUtils.getBooleanValueFromMap(componentProperties, HertzConstants.USE_LOCALE_IN_URL);
			irac = HertzUtils.getBooleanValueFromMap(componentProperties, HertzConstants.IRAC);
			strIracLink = HertzUtils.getValueFromMap(componentProperties, HertzConstants.IRACLINK).trim();			
			setLanguageValuesInBean(strLanguageName, strLanguageCode, isDefaultLanguage, useLocaleInUrl,
					languageItemsBeanList,irac,strIracLink);
					
		}
		logger.debug("End:- getLanguageProperties() of CountryLanguageSelectorServiceImpl");
	}

	/**
	 * This method sets the language properties in a bean
	 * 
	 * @param strLocale
	 * @param strLanguageName
	 * @param strLanguageCode
	 * @param isDefaultLanguage
	 * @param strUrl
	 * @param languageItemsBeanList
	 */
	private void setLanguageValuesInBean(String strLanguageName, String strLanguageCode,
			Boolean isDefaultLanguage, Boolean useLocaleInUrl, List<LanguageItemsBean> languageItemsBeanList,Boolean irac,String strIracLink) {
		logger.debug("Start:- setLanguageValuesInBean() of CountryLanguageSelectorServiceImpl");
		LanguageItemsBean languageItemsBean = new LanguageItemsBean();
		//languageItemsBean.setLocale(strLocale);
		languageItemsBean.setLanguageName(strLanguageName);
		languageItemsBean.setLanguageCode(strLanguageCode);
		languageItemsBean.setDefaultLanguage(isDefaultLanguage);
		languageItemsBean.setIracLink(strIracLink);
		//languageItemsBean.setURL(strUrl);
		languageItemsBeanList.add(languageItemsBean);
		logger.debug("End:- setLanguageValuesInBean() of CountryLanguageSelectorServiceImpl");
	}

}
