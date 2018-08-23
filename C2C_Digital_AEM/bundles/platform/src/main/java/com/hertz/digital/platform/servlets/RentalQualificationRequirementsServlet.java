package com.hertz.digital.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.hertz.digital.platform.bean.RQRSectionContainerBean;
import com.hertz.digital.platform.bean.RQRWrapperBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.use.RentalQualificationUse;
import com.hertz.digital.platform.utils.HertzUtils;


/**
 * This servlet is used to generate a json containing the RQR info which is configured by the authors on RQR pages and which
 * needs to be rendered to the FED.
 *
 * @author himanshu.i.sharma
 */
@Component(immediate = true, metatype = false, label = "Hertz - Rental Qualification Requirements Servlet", description = "Hertz - Provides a content JSON in response to a hit")
@Service(Servlet.class)
@Properties(value = {
		@Property(name = "sling.servlet.paths", value = "/bin/hertz/fetchrqr", propertyPrivate = true)})

public class RentalQualificationRequirementsServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2464903745509090042L;

	/**
	 * Default Constructor
	 */
	public RentalQualificationRequirementsServlet() {
		super();
	}
	
	/**
	 * LOGGER instantiation.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalQualificationRequirementsServlet.class);
	
	@Reference
    private QueryBuilder builder;
	
	private static final String P_LIMIT = "-1";
	private static final String TRUE = "true";
	
	/**
	 * This method is used to fetch all the content
	 * for the RQR and write them to a JSON
	 *
	 *@param request,response
	 *@return void
	 *
	 *@throws ServletException
	 *@throws- IOException
	 */
	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Entering the doGet method of RentalQualificationRequirementsServlet");
		PrintWriter printWriter = null;
		HertzUtils.setResponseParameters(response);
		String[] selectors = request.getRequestPathInfo().getSelectors();
		LOGGER.debug("Selectors are :- {}", Arrays.toString(selectors));
		try {
			if(selectors.length==5){
				String rqrJSON = getRqrJson(selectors, request);
				LOGGER.debug("OUTPUT JSON " + rqrJSON);
				printWriter = response.getWriter();
				printWriter.write(rqrJSON);
				printWriter.close();
			}
		} catch (RepositoryException e) {
			LOGGER.error("Could not get JSON", e.getMessage());
			response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (LoginException e) {
			LOGGER.error("Error during administrative login", e.getMessage());
			response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (JSONException e) {
			LOGGER.error("Could not get JSON", e.getMessage());
			response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		LOGGER.debug("Exiting the doGet method of RentalQualificationRequirementsServlet");
	}
	
	/**
	 * This method constructs the content path from selectors and queries 
	 * for the oag in jcr to return the final json
	 * @param selectors
	 * @param request 
	 * @return json
	 * @throws RepositoryException
	 * @throws JSONException
	 * @throws LoginException
	 */
	public String getRqrJson(String[] selectors, SlingHttpServletRequest request) throws RepositoryException, JSONException, LoginException{
		LOGGER.debug("Entering the getRqrJson method of RentalQualificationRequirementsServlet");
		RQRSectionContainerBean rQRSectionContainerBean = new RQRSectionContainerBean();
		RQRWrapperBean rQRWrapperBean = new RQRWrapperBean();
		Gson gson = new Gson();
		Session session =request.getResourceResolver().adaptTo(Session.class);
		JSONObject object=new JSONObject();
		String path = selectors[0]+"/"+selectors[1]+"/"+selectors[2]+"/"+selectors[3];
		String pageName = selectors[4];
		SearchResult result = getQueryResults(pageName, path, session);
		if (null != pageName && pageName != StringUtils.EMPTY) {
			getOAGPageResource(object, result, rQRSectionContainerBean);
		}
		gson = HertzUtils.initGsonBuilder(true, true, true);
		rQRWrapperBean.setRQRSectionContainerBean(rQRSectionContainerBean);
		String jsonString = gson.toJson(rQRWrapperBean);
		HertzUtils.closeResolverSession(request.getResourceResolver(), session);
		LOGGER.debug("Exiting the getRqrJson method of RentalQualificationRequirementsServlet");
		return jsonString;
	}
	
	/**
	 * This method is used to execute a query and return the query results
	 * @param path
	 * @param session
	 * @return query result
	 */
	private SearchResult getQueryResults(String pageName, String path, Session session) {
		LOGGER.debug("Entering the getQueryResults method of RentalQualificationRequirementsServlet");
		Map<String, String> map = new HashMap<String, String>();
		map.put("path", HertzConstants.CONTENT + path);
		map.put("type", HertzConstants.CQ_PAGE);
		map.put("nodename", pageName);
		map.put("property.and", TRUE);
		map.put("p.limit", P_LIMIT);
		// Create a Query instance
		Query query = builder.createQuery(PredicateGroup.create(map), session);
		LOGGER.debug("Exiting the getQueryResults method of RentalQualificationRequirementsServlet");
		// Return the query results
		return query.getResult();
	}
	
	/**
	 * This method for every hit passes on the page resource to getParentPageResource method
	 * of RentalQualificationUse class to set the values in bean
	 * @param object
	 * @param result
	 * @param rQRSectionContainerBean
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	private void getOAGPageResource(JSONObject object, SearchResult result, RQRSectionContainerBean rQRSectionContainerBean) throws RepositoryException, JSONException{
		LOGGER.debug("Entering the getOagPagePath method of RentalQualificationRequirementsServlet");
		Resource pageResource = null;
		for (Hit hit : result.getHits()) {
			if(!ResourceUtil.isNonExistingResource(hit.getResource())){
				pageResource = hit.getResource();
				RentalQualificationUse rentalQualificationUse = new RentalQualificationUse();
				rentalQualificationUse.getParentPageResource(pageResource, rQRSectionContainerBean);
			}
		}
		LOGGER.debug("Exiting the getOagPagePath method of RentalQualificationRequirementsServlet");
	}
}
