package com.hertz.digital.platform.use.spa;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.jcr.JsonItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.HeaderFooterBean;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.impl.ConfigurableTextParSysExportor;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * The use class for Home Page. Invoked when hit using .spa. selector and
 * extension .json with home page.
 * <ul>
 * Returns a wrapper JSON with :-
 * <li>Self Properties</li>
 * <li>Component HTMLs for components under /par</li>
 * <li>Component JSONs for all pre-included components.</li>
 * <li>JSONs for all the properties included as part of page properties</li>
 * </ul>
 * 
 * @author n.kumar.singhal
 *
 */
public class GenericPageUse extends WCMUsePojo {

	/**
	 * Default constructor Declaration
	 */
	public GenericPageUse() {
		super();
	}
	/**
	 * PARTNER_BASE_PAGE_PATH
	 */
	private static final String PARTNER_BASE_PAGE_PATH =  "partnerBasePagePath";
	/**
	 * Logger instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(GenericPageUse.class);
	/**
	 * Variable that contains the JSON String
	 */
	private String jsonString;

	private SpaPageBean spaBean;
	/**
	 * Map for Storing the SPA Configurable Properties
	 */
	Map<String, Object> configuredSPAPropertiesMap;

	/**
	 * Get the Config Properties of the SPA Template Page. and then generate a
	 * JSON from those Properties. If the Template is not SPA Template then gets
	 * all the information of the component and create a JSON.
	 * 
	 * @throws IOException
	 * @throws ServletException 
	 */
	@Override
	public void activate() throws JSONException, RepositoryException, IOException, ServletException {
		logger.debug("Start:- activate() of GenericPageUse");
		JSONObject object = new JSONObject();
		String requestPath = StringUtils.EMPTY;		
		requestPath = getRequest().getPathInfo();
		RequestPathInfo requestPathInfo = getRequest().getRequestPathInfo();
		if (requestPath.contains(HertzConstants.JCR_CONTENT) || requestPath.contains(HertzConstants.JCR_CONTENT_REQ)) {
			object = setObjectforJcrContentRequest();

		} else {
			SpaPageBean spaBean = new SpaPageBean();
			spaBean.setPageTitle(getCurrentPage().getTitle());
			HertzUtils.setSeoMetaDataInBean(spaBean, getResource(), requestPathInfo, getResourceResolver());
			if(getCurrentPage().hasContent()) {
				setSpaPageBeanRapidSplash(spaBean, getCurrentPage().getContentResource());
			}
			try {
				setConfigPropsInBean(requestPathInfo, spaBean);
				setHeaderFooterInSpaBean(spaBean);
				//for native hand-off.
				setSpaBean(spaBean);
				Gson gson = HertzUtils.initGsonBuilder(true, true, true);
				object.accumulate(HertzConstants.SPA_CONTENT, new JSONObject(gson.toJson(spaBean, SpaPageBean.class)));

			} catch (RepositoryException | JSONException e) {
				logger.error("Error in GenericPageUse class : {} : {}", e.getMessage(), e);
			}
		}
		setJsonString(object.toString());

		logger.debug("End:- activate() of GenericPageUse");
	}

	/**
	 * @param spaBean
	 */
	private void setHeaderFooterInSpaBean(SpaPageBean spaBean) {
		HeaderFooterBean headerFooterBean = HertzUtils.setHeaderFooterContent(getResource(), getResourceResolver(), getSlingScriptHelper());
		if (null!=headerFooterBean.getHeaderBeanList() && headerFooterBean.getHeaderBeanList().size() > 0) {
			spaBean.setHeaderBeanList(headerFooterBean.getHeaderBeanList());
		}
		
	if (null!=headerFooterBean.getFooterContentList() && headerFooterBean.getFooterContentList().size() > 0) {
			spaBean.setFooterContentList(headerFooterBean.getFooterContentList());
		}
	}

	/**
	 * @param spaBean
	 * @throws JSONException
	 * @throws RepositoryException
	 * @throws ServletException
	 * @throws IOException
	 */
	private void setConfigPropsInBean(RequestPathInfo requestPathInfo, SpaPageBean spaBean)
			throws JSONException, RepositoryException, ServletException, IOException {
		String[] components = HertzUtils.toArray(get(HertzConstants.COMPONENTS, String.class),
				HertzConstants.COMMA);
		SlingScriptHelper scriptHelper=getSlingScriptHelper();
		Map<String, Object> includedComponents = HertzUtils.getIncludedComponentsAsBeans(requestPathInfo, getResource(),
				components, getResourceResolver(), scriptHelper);
		if (MapUtils.isNotEmpty(includedComponents)) {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			list.add(includedComponents);
			spaBean.setIncludedComponents(list);
		}
		Map<String, Object> configTextMap = null;
		Page curPage = getCurrentPage();
		if (curPage != null && curPage.hasContent()) {
			Resource configTextParSysResource = curPage.getContentResource(HertzConstants.CONFIGTEXT_PARSYS);
			if(null!=configTextParSysResource){
				configTextMap = ConfigurableTextParSysExportor
						.getConfigTextParsysMap(configTextParSysResource, getResourceResolver());
			}
		}

		if(MapUtils.isNotEmpty(configTextMap)){
			spaBean.setConfiguredProps(configTextMap);
		}
	}

	/**
	 * @param spaBean
	 * @param jcrRes
	 */
	private void setSpaPageBeanRapidSplash(SpaPageBean spaBean, Resource jcrRes) {
		if(jcrRes.getResourceType().equals(HertzConstants.RAPID_RESOURCE_TYPE)) {
			Map<String, Object> rapidComponentMap = null;
			rapidComponentMap = HertzUtils.getRapidComponents(jcrRes, getResourceResolver()); 
			if(MapUtils.isNotEmpty(rapidComponentMap)) {
				spaBean.setConfiguredProps(rapidComponentMap);
			}
		}
		if(jcrRes.getResourceType().equals(HertzConstants.SPLASH_RESOURCE_TYPE)) {
			Map<String, Object> hertzLinksMap = null;
			hertzLinksMap = HertzUtils.getHertzLinksProps(jcrRes); 
			if(MapUtils.isNotEmpty(hertzLinksMap)) {
				spaBean.setHertzLinks(hertzLinksMap);
			}
		}
	}

	/**
	 * @return
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	private JSONObject setObjectforJcrContentRequest() throws RepositoryException, JSONException {
		JSONObject object;
		Node node = getResource().adaptTo(Node.class);
		StringWriter stringWriter = new StringWriter();
		JsonItemWriter jsonWriter = new JsonItemWriter(null);
		jsonWriter.dump(node, stringWriter, -1);
		object = new JSONObject(stringWriter.toString());
		return object;
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



	public SpaPageBean getSpaBean() {
		return spaBean;
	}



	public void setSpaBean(SpaPageBean spaBean) {
		this.spaBean = spaBean;
	}

}
