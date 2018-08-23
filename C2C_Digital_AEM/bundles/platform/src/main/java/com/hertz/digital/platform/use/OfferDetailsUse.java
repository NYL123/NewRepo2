/**
 * 
 */
package com.hertz.digital.platform.use;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.jcr.JsonItemWriter;
import org.apache.sling.engine.SlingRequestProcessor;
import org.apache.sling.settings.SlingSettingsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.SightlyWCMMode;
import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.wcm.api.WCMMode;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.OfferDetailsBean;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exporter.impl.ConfigurableTextParSysExportor;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.utils.HertzUtils;
import com.hertz.digital.platform.utils.OfferUtils;

/**
 * A use class to generate the offerdetails json for each and every offer. This
 * will set a jsonString which will be returned when called from
 * offerdetail.json.html
 * 
 * @author a.dhingra
 *
 */
public class OfferDetailsUse extends WCMUsePojo {

	/**
	 * Default Constructor
	 */
	public OfferDetailsUse() {
		super();
	}

	/**
	 * Logger instantiation
	 */
	protected static final Logger logger = LoggerFactory.getLogger(OfferDetailsUse.class);

	/**
	 * Variable that contains the JSON String
	 */
	private String jsonString;

	/**
	 * This method sets the json string to be returned.
	 * 
	 * @throws JSONException
	 * @throws RepositoryException
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	public void activate() throws RepositoryException, JSONException, ServletException, IOException {
		logger.debug("Start:- activate() of OfferDetailsUse");
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
			SpaPageBean spaBean = new SpaPageBean();
			SlingScriptHelper scriptHelper = getSlingScriptHelper();

			Resource currentResource = getResource();
			spaBean.setPageTitle(getCurrentPage().getTitle());
			HertzUtils.setSeoMetaDataInBean(spaBean, currentResource, requestPathInfo, getResourceResolver());

			Map<String, Object> configTextMap = new LinkedHashMap<>();
			Resource parResource = currentResource.getChild(HertzConstants.CONFIGTEXT_PARSYS);
			RequestResponseFactory requestResponseFactory = scriptHelper.getService(RequestResponseFactory.class);
			SlingRequestProcessor requestProcessor = scriptHelper.getService(SlingRequestProcessor.class);
			HertzConfigFactory configFactory = scriptHelper.getService(HertzConfigFactory.class);
			String[] spaAllowedComponents = (String[]) configFactory
					.getPropertyValue(HertzConstants.Hertz_SPA_ALLOWED_COMPONENTS);
			if (null != parResource) {
				configTextMap = ConfigurableTextParSysExportor.getConfigTextParsysMap(parResource,
						getResourceResolver());
			}

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String[] components = HertzUtils.toArray(get(HertzConstants.COMPONENTS, String.class),
					HertzConstants.COMMA);
			Map<String, Object> includedComponents = HertzUtils.getStaticIncludedComponentsAsBean(getResource(),
					components, getResourceResolver(), scriptHelper);

			String parentWrapperText = setStaticComponentHtml(currentResource, requestResponseFactory, requestProcessor,
					spaAllowedComponents, includedComponents);

			if (includedComponents.size() > 0) {
				list.add(includedComponents);
			}
			if (!list.isEmpty()) {
				spaBean.setIncludedComponents(list);
			}
			List<OfferDetailsBean> offerList = new ArrayList<>();
			OfferDetailsBean offerDetails = new OfferDetailsBean();
			OfferUtils.setOfferDetailsBean(offerDetails, getResource());
			offerList.add(offerDetails);
			configTextMap.put(HertzConstants.OFFERS, offerList);
			if (MapUtils.isNotEmpty(configTextMap)) {
				spaBean.setConfiguredProps(configTextMap);
			}
			Gson gson = HertzUtils.initGsonBuilder(true, true, true);
			object.accumulate(parentWrapperText, new JSONObject(gson.toJson(spaBean, SpaPageBean.class)));
		}
		setJsonString(object.toString());

		logger.debug("End:- activate() of OfferDetailsUse");
	}

	/**
	 * Sets the static component html.
	 *
	 * @param parentWrapperText
	 *            the parent wrapper text
	 * @param currentResource
	 *            the current resource
	 * @param requestResponseFactory
	 *            the request response factory
	 * @param requestProcessor
	 *            the request processor
	 * @param spaAllowedComponents
	 *            the spa allowed components
	 * @param includedComponents
	 *            the included components
	 * @return the string
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private String setStaticComponentHtml(Resource currentResource, RequestResponseFactory requestResponseFactory,
			SlingRequestProcessor requestProcessor, String[] spaAllowedComponents,
			Map<String, Object> includedComponents) throws ServletException, IOException {
		String parentWrapperText = HertzConstants.SPA_CONTENT;
		if (ArrayUtils.contains(getRequest().getRequestPathInfo().getSelectors(), "spa")) {
			Resource marketingSlot1 = currentResource.getChild(HertzConstants.MARKETING_SLOT_1);
			if (null != marketingSlot1) {
				String requestHtmlPath = marketingSlot1.getPath() + ".html";
				Element body = getHtmlOfMarketingSlot(requestResponseFactory, requestProcessor, spaAllowedComponents,
						requestHtmlPath);
				if (StringUtils.isNotBlank(body.html())) {
					includedComponents.put(marketingSlot1.getName(), body.html());
				}
			}
			Resource marketingSlot2 = currentResource.getChild(HertzConstants.MARKETING_SLOT_2);
			if (null != marketingSlot2) {
				String requestHtmlPath = marketingSlot2.getPath() + ".html";
				Element body = getHtmlOfMarketingSlot(requestResponseFactory, requestProcessor, spaAllowedComponents,
						requestHtmlPath);
				if (StringUtils.isNotBlank(body.html())) {
					includedComponents.put(marketingSlot2.getName(), body.html());
				}
			}
		} else if (ArrayUtils.contains(getRequest().getRequestPathInfo().getSelectors(), "native")) {
			parentWrapperText = HertzConstants.NATIVE_CONTENT;
			Resource parResource = currentResource.getChild(HertzConstants.NATIVE_PARSYS);
			if (null != parResource) {
				includedComponents.put("nativeParsys",
						ConfigurableTextParSysExportor.getConfigTextParsysMap(parResource, getResourceResolver()));
			}
		}
		return parentWrapperText;
	}

	/**
	 * This method makes get requests to get html of the component and returns
	 * the element body of the html.
	 * 
	 * @param requestResponseFactory
	 * @param requestProcessor
	 * @param spaAllowedComponents
	 * @param requestHtmlPath
	 * @return Element
	 * @throws ServletException
	 * @throws IOException
	 */
	private Element getHtmlOfMarketingSlot(RequestResponseFactory requestResponseFactory,
			SlingRequestProcessor requestProcessor, String[] spaAllowedComponents, String requestHtmlPath)
			throws ServletException, IOException {
		logger.debug("Start:- getHtmlOfMarketingSlot() of OfferDetailsUse");
		HttpServletRequest request = requestResponseFactory.createRequest("GET", requestHtmlPath);
		WCMMode.DISABLED.toRequest(request);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		HttpServletResponse response = requestResponseFactory.createResponse(out);
		requestProcessor.processRequest(request, response, getResourceResolver());
		Document doc = Jsoup.parse(out.toString());
		for (String componentName : spaAllowedComponents) {
			doc.getElementsByClass(componentName).remove();
		}
		doc.select("script,style,link").remove();
		HertzUtils.replaceImageTagWithPicture(doc, getResourceResolver());
		SlingSettingsService slingSetting=getSlingScriptHelper().getService(SlingSettingsService.class);
		if(slingSetting.getRunModes().contains("author")){
			HertzUtils.removeHtmlExtensionFromUrl(doc, getResourceResolver());
		}
		Element body = doc.body();
		logger.debug("End:- getHtmlOfMarketingSlot() of OfferDetailsUse");
		return body;
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
