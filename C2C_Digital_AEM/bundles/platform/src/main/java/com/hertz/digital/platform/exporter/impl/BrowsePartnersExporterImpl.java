package com.hertz.digital.platform.exporter.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.hertz.digital.platform.bean.ImageInfoBean;
import com.hertz.digital.platform.bean.PartnerCategoryBean;
import com.hertz.digital.platform.bean.PartnerDetailsBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.api.ComponentExporterService;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * The component specific implementation of the exporter service required to
 * return the specific requested bean object.
 * 
 * Notice the identifier parameter which must be kept unique for each of the
 * created services.
 * 
 * @author a.dhingra
 *
 */
@Component(immediate = true, metatype = true, label = "Hertz - Browse Partners Service", description = "Hertz - Exposes all the partners/sub-partners as json")
@Service(value = ComponentExporterService.class)
@Properties(value = { @Property(name = "identifier", value = "browsepartners") })
public class BrowsePartnersExporterImpl implements ComponentExporterService {

    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor Declaration
     */
    public BrowsePartnersExporterImpl() {
        super();
    }

    @Reference
    private transient JCRService jcrService;

	/**
	 * LOGGER instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(BrowsePartnersExporterImpl.class);
	
	/**
	 *This method exports the Content of the static included component as json
	 * */
	public String exportAsJson(Resource component, ResourceResolver resolver) {
		logger.debug("Start:- exportAsJson in BrowsePartnersExporterImpl ");
		return "{}";
	}

	/**
	 *This method exports the content of the static included component as an Object
	 * */
	public Object exportAsBean(Resource component, ResourceResolver resolver) {
		logger.debug("Start :- exportAsBean in BrowsePartnersExporterImpl");
		List<PartnerCategoryBean> partnerCategoryList=new ArrayList<>();
        getPartnerDetails(component, resolver, partnerCategoryList);
        logger.debug("Exit:- exportAsBean in BrowsePartnersExporterImpl");
        return partnerCategoryList;
    }

    /**
	 * This method is used to export the content of the components as a Map
	 * */
	public Object exportAsMap(ResourceResolver resolver, Resource configPageResource)
			throws JSONException, RepositoryException {
		
		return null;
	}
	
	/**
	 * This method is used to set the partner details
	 * in the list.
	 * 
	 * @param component
	 * @param resolver
	 * @param partnerCategoryList
	 */
	private void getPartnerDetails(Resource component, ResourceResolver resolver,List<PartnerCategoryBean> partnerCategoryList) {
		logger.debug("Start :- getPartnerDetails in BrowsePartnersExporterImpl");
        try {
            Resource partnerLandingResource = component.getParent();
            Map<String, String> predicateMap = new HashMap<>();
            preparePredicateMap(partnerLandingResource, predicateMap, HertzConstants.PARTNER_CATEGORY_TEMPLATE);
            logger.debug("The predicate map looks like this:- {}", predicateMap);
            String locale = HertzUtils.getLocaleFromPath(component.getPath());
            if (null != jcrService) {
                SearchResult result = jcrService.searchResults(resolver, predicateMap);
                for (Hit hit : result.getHits()) {
                    PartnerCategoryBean partnerCategoryBean = new PartnerCategoryBean();
                    Resource hitResource = hit.getResource();
                    partnerCategoryBean.setPartnerCategoryName(hit.getTitle());
                    partnerCategoryBean.setPartnerCategory(hit.getResource().getName());
                    Map<String, String> predicateChildMap = new HashMap<>();
                    List<PartnerDetailsBean> list = new ArrayList<>();
                    preparePredicateMap(hitResource, predicateChildMap, HertzConstants.PARTNER_DETAIL_TEMPLATE);
                    SearchResult searchResult = jcrService.searchResults(resolver, predicateChildMap);
                    setPartnerDetailsInBean(searchResult, list, resolver);
                    Collections.sort(list, new Comparator<PartnerDetailsBean>() {
						private Collator trCollator = Collator
								.getInstance(new Locale(locale, StringUtils.capitalize(locale)));

						@Override
						public int compare(PartnerDetailsBean o1, PartnerDetailsBean o2) {
							return trCollator.compare(o1.getPartnerName(), o2.getPartnerName());
						}
					});
                    partnerCategoryBean.setPartners(list);
                    partnerCategoryList.add(partnerCategoryBean);
                }
				Collections.sort(partnerCategoryList, new Comparator<PartnerCategoryBean>() {
					private Collator trCollator = Collator
							.getInstance(new Locale(locale, StringUtils.capitalize(locale)));

					@Override
					public int compare(PartnerCategoryBean o1, PartnerCategoryBean o2) {
						return trCollator.compare(o1.getPartnerCategoryName(), o2.getPartnerCategoryName());
					}
				});
            }
		}catch(RepositoryException rep){
			logger.error("Repository Exception in BrowsePartnersExplorerImpl class : - "+rep.getMessage());
		}
			
			
		logger.debug("Exit :- getPartnerDetails in BrowsePartnersExporterImpl");		
	}
	
	/**
	 * This method sets the partner details in bean
	 * @param searchResult
	 * @param list
	 * @param resolver
	 * @throws RepositoryException
	 */
	private void setPartnerDetailsInBean(SearchResult searchResult, List<PartnerDetailsBean> list, ResourceResolver resolver) throws RepositoryException{
		logger.debug("Start :- setPartnerDetailsInBean in BrowsePartnersExporterImpl");
		for (Hit categoryDetailHit : searchResult.getHits()) {
            PartnerDetailsBean partnerDetail = new PartnerDetailsBean();
            partnerDetail.setPartnerName(categoryDetailHit.getTitle());
            partnerDetail.setPartner(categoryDetailHit.getResource().getName());

            Map<String, Object> configTextMap = new LinkedHashMap<>();
            Resource configTextParSysResource = categoryDetailHit.getResource().getChild(
                    HertzConstants.JCR_CONTENT + HertzConstants.SLASH + HertzConstants.CONFIGTEXT_PARSYS);
            if (null != configTextParSysResource) {
                configTextMap = ConfigurableTextParSysExportor
                        .getConfigTextParsysMap(configTextParSysResource, resolver);
            }
            if(configTextMap.containsKey("partnerImage")){
                ImageInfoBean sources = (ImageInfoBean) configTextMap.get("partnerImage");
                partnerDetail.setPartnerImage(sources);
            }
            list.add(partnerDetail);
        }
		logger.debug("Exit :- setPartnerDetailsInBean in BrowsePartnersExporterImpl");
	}

	/**
	 * @param component
	 * @param predicateMap
	 * @param template
	 */
	private void preparePredicateMap(Resource component, Map<String, String> predicateMap, String template) {
		logger.debug("Start :- preparePredicateMap in BrowsePartnersExporterImpl");
		predicateMap.put(HertzConstants.TYPE, "cq:Page");
		predicateMap.put(HertzConstants.PATH, component.getPath());
		predicateMap.put("property", "jcr:content/cq:template");
		predicateMap.put("property.value", template);
		predicateMap.put(HertzConstants.LIMIT, "-1");
		predicateMap.put("path.flat", "true");
		logger.debug("Exit :- preparePredicateMap in BrowsePartnersExporterImpl");
	}
	
	
}
