package com.hertz.digital.platform.use;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.engine.SlingRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.contentsync.handler.util.RequestResponseFactory;
import com.day.cq.wcm.api.WCMMode;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.spa.SpaPageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.use.spa.GenericPageUse;
import com.hertz.digital.platform.utils.HertzUtils;

public class PartnerDetailUse extends GenericPageUse {

	/**
	 * Logger instantiation
	 */
	private static final Logger logger = LoggerFactory.getLogger(PartnerDetailUse.class);

	/** The Constant MARKETINGSLOT2. */
	private static final String MARKETINGSLOT2 = "marketingslot2";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hertz.digital.platform.use.spa.NativeUse#activate()
	 */
	@Override
	public void activate() throws JSONException, RepositoryException, IOException, ServletException {

		super.activate();
		this.prepareJsonString();
	}

	/**
	 * Instantiates a new partner detail use.
	 */
	public PartnerDetailUse() {
		super();
	}

	/**
	 * Prepare json string.
	 *
	 * @throws JSONException
	 *             the JSON exception
	 * @throws RepositoryException
	 *             the repository exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ServletException
	 *             the servlet exception
	 */
	public void prepareJsonString() throws JSONException, RepositoryException, IOException, ServletException {
		logger.debug("Received spa bean from generic page use is :- {}", getSpaBean());

		JSONObject object = new JSONObject();
		List<Map<String, Object>> componentsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> beanMap = null;
		Iterator<Map<String, Object>> iterator = getSpaBean().getIncludedComponents().iterator();
		while (iterator.hasNext()) {
			beanMap = iterator.next();
			if (beanMap.containsKey(MARKETINGSLOT2)) {
				beanMap.remove(MARKETINGSLOT2);
				// setting marketing slot in format [{},{}]
				List<Map<String, Object>> marketingList = new ArrayList<Map<String, Object>>();

				Resource marketingResource = getCurrentPage().getContentResource(MARKETINGSLOT2);
				if (null != marketingResource) {
					getMarketingSlotData(marketingList, marketingResource);
				}
				beanMap.put(MARKETINGSLOT2, marketingList);
			}
		}
		componentsList.add(beanMap);

		// overriding SPA
		getSpaBean().setIncludedComponents(componentsList);
		Gson gson = HertzUtils.initGsonBuilder(true, true, true);
		object.accumulate(HertzConstants.SPA_CONTENT, new JSONObject(gson.toJson(getSpaBean(), SpaPageBean.class)));
		// overriding SPA
		setJsonString(object.toString());
	}

	/**
	 * Gets the marketing slot data.
	 *
	 * @param marketingList the marketing list
	 * @param marketingResource the marketing resource
	 * @return the marketing slot data
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void getMarketingSlotData(List<Map<String, Object>> marketingList, Resource marketingResource)
			throws ServletException, IOException {
		Iterator<Resource> resIterator = marketingResource.listChildren();
		Map<String, Object> internalMap = null;
		while (resIterator.hasNext()) {
			Resource child = resIterator.next();
			internalMap = new LinkedHashMap<String, Object>();
			if (child.getValueMap().get(HertzConstants.SLING_RESOURCE_TYPE)
					.equals("hertz/components/content/tabletextaccordion")) {
				getTableTextAccordionJson(internalMap, child);
			} else {
				internalMap.put("responsivegrid",
						getHTMLofComponent(getResourceResolver(),
								getSlingScriptHelper().getService(RequestResponseFactory.class),
								getSlingScriptHelper().getService(SlingRequestProcessor.class),
								child.getPath() + HertzConstants.DOT + HertzConstants.HTML));
			}
			marketingList.add(internalMap);
		}
	}

	/**
	 * Gets the table text accordion html.
	 *
	 * @param internalMap
	 *            the internal map
	 * @param child
	 *            the child
	 * @return the table text accordion html
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void getTableTextAccordionJson(Map<String, Object> internalMap, Resource child)
			throws ServletException, IOException {
		ValueMap childData = child.getValueMap();
		internalMap.put("title", childData.get("heading"));
		internalMap.put("subTitle", childData.getOrDefault("subTitle", "Miles Breakdown"));

		internalMap.put("responsivegrid",
				getHTMLofComponent(getResourceResolver(),
						getSlingScriptHelper().getService(RequestResponseFactory.class),
						getSlingScriptHelper().getService(SlingRequestProcessor.class),
						child.getPath() + HertzConstants.DOT + "accordian"));

		if (child.hasChildren()) {
			Iterator<Resource> childIterator = child.listChildren();
			while (childIterator.hasNext()) {
				Resource subChild = childIterator.next();
				internalMap.put("dropDownKey", subChild.getValueMap().get("columnname"));
				internalMap.put("tablecontent",
						getHTMLofComponent(getResourceResolver(),
								getSlingScriptHelper().getService(RequestResponseFactory.class),
								getSlingScriptHelper().getService(SlingRequestProcessor.class),
								subChild.getPath() + HertzConstants.DOT + HertzConstants.HTML));
			}
		}
	}

	/**
	 * Private implementation without the JSOUP
	 * 
	 * @param resolver
	 * @param requestResponseFactory
	 * @param requestProcessor
	 * @param requestPath
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public StringBuilder getHTMLofComponent(ResourceResolver resolver, RequestResponseFactory requestResponseFactory,
			SlingRequestProcessor requestProcessor, String requestPath) throws ServletException, IOException {
		StringBuilder stringBuilder = new StringBuilder();
		HttpServletRequest request = requestResponseFactory.createRequest("GET", requestPath);
		WCMMode.DISABLED.toRequest(request);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		HttpServletResponse response = requestResponseFactory.createResponse(out);
		requestProcessor.processRequest(request, response, resolver);
		return stringBuilder.append(out.toString());
	}

}
