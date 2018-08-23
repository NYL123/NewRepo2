package com.hertz.digital.platform.listeners;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.AgentConfig;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationLog;
import com.day.cq.replication.ReplicationResult;
import com.day.cq.replication.ReplicationTransaction;
import com.day.cq.replication.TransportContext;
import com.day.cq.replication.TransportHandler;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.bean.CoordinatorConsumable;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.factory.MicroServicesConfigurationFactory;
import com.hertz.digital.platform.service.api.MCDataCoordinatorService;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Transport handler to send test and notification requests to MS and handle
 * responses.
 * 
 * @author deepak.parma
 */
@Service(TransportHandler.class)
@Component(label = "Hertz Custom Agent", immediate = true)
public class HertzTransportHandler implements TransportHandler {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(HertzTransportHandler.class);

	/** The Constant HEALTHCHECK_API_PATH. */
	private static final String HEALTH_CHECK_API_PATH = "/api/cacheUploadService/health";

	/**
	 * Protocol for replication agent transport URI that triggers this transport
	 * handler.
	 */
	private final static String TRANSPORT_SCHEMA = "hertz:";

	/** The Constant UP. */
	private static final String UP = "UP";

	/** The Constant STATUS. */
	private static final String STATUS = "status";

	/** The system service. */
	@Reference
	private transient SystemUserService systemService;

	/** The hertz config factory. */
	@Reference
	HertzConfigFactory hConfigFactory;

	/** The mc factory. */
	@Reference
	MicroServicesConfigurationFactory mcFactory;

	/** The mc service path. */
	private String mcServicePath;

	/** The json string. */
	private String jsonString;

	/** The data path. */
	private String dataPath;

	/** The token path. */
	private String tokenPath;

	/** The base path. */
	private String basePath;

	/** The password grant. */
	private String passwordGrant;

	/** The pwd service url. */
	private String pwdServiceUrl;

	/** The service end point. */
	private String serviceEndPoint;

	/** The token. */
	private String token;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.day.cq.replication.TransportHandler#canHandle(com.day.cq.replication.
	 * AgentConfig)
	 */
	@Override
	public boolean canHandle(AgentConfig config) {

		final String transportURI = config.getTransportURI();
		return (transportURI != null) ? transportURI.toLowerCase().startsWith(TRANSPORT_SCHEMA) : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.day.cq.replication.TransportHandler#deliver(com.day.cq.replication.
	 * TransportContext, com.day.cq.replication.ReplicationTransaction)
	 */
	@Override
	public ReplicationResult deliver(TransportContext ctx, ReplicationTransaction tx) throws ReplicationException {

		ResourceResolver resolver = systemService.getServiceResourceResolver();
		Session session = resolver.adaptTo(Session.class);
		try {
			final ReplicationActionType replicationType = tx.getAction().getType();
			setTokenPath(StringUtils.trim(mcFactory.getStringPropertyValue(HertzConstants.HERTZ_TOKEN_INGESTION_PATH)));
			setBasePath(StringUtils.trim(mcFactory.getStringPropertyValue(HertzConstants.HERTZ_MC_BASEPATH)));
			setPasswordGrant(StringUtils.trim(mcFactory.getStringPropertyValue(HertzConstants.HERTZ_MC_PASSGRANT)));
			setPwdServiceUrl(getBasePath() + getPasswordGrant());
			setToken(HertzUtils.getOrCreateAccessToken(getTokenPath(), HertzConstants.PWD_GRANT_TOKEN_NODE_NAME,
					pwdServiceUrl, session));
			if (replicationType == ReplicationActionType.TEST) {
				return doTest(ctx, tx);
			} else if (replicationType == ReplicationActionType.ACTIVATE) {
				initializeParams(tx, resolver, session);
				return doActivate(ctx, tx);
			} else {
				tx.getLog().info("Replication action type " + replicationType + " not supported.");
				return ReplicationResult.OK;
			}
		} finally {
			HertzUtils.closeResolverSession(resolver, session);
		}
	}

	/**
	 * Send test request to MS via a GET request.
	 *
	 * @param ctx
	 *            the ctx
	 * @param tx
	 *            the tx
	 * @return the replication result
	 * @throws ReplicationException
	 *             the replication exception
	 */
	private ReplicationResult doTest(TransportContext ctx, ReplicationTransaction tx) throws ReplicationException {

		final ReplicationLog log = tx.getLog();
		if (StringUtils.isNotEmpty(getBasePath())) {
			final HttpGet request = new HttpGet(getBasePath() + HEALTH_CHECK_API_PATH);
			final HttpResponse response = sendRequest(request);
			if (response != null) {
				final int statusCode = response.getStatusLine().getStatusCode();
				log.info(response.toString());
				log.info("---------------------------------------");
				if (statusCode == HttpStatus.SC_OK) {
					JSONObject responseObject;
					try {
						responseObject = new JSONObject(EntityUtils.toString(response.getEntity()));
						if (responseObject.has(STATUS)
								&& StringUtils.equalsIgnoreCase(responseObject.getString(STATUS), UP)) {
							log.info("Notification Test GET call to MS succeeded");
							return ReplicationResult.OK;
						}
					} catch (ParseException | JSONException | IOException e) {
						LOG.error("JSON Execption Occured : {} ", e);
					}
				} else {
					log.info("Notification Test GET call to MS failed");
					log.info("statusCode : " + statusCode);
				}
			}
		} else {
			log.info("The service base path in configuration is not right", getBasePath());
			LOG.debug("The service base path in configuration is not right. {}", getBasePath());
		}
		return new ReplicationResult(false, 0, "Notification Test GET call to MS failed");
	}

	

	/**
	 * Send notification request to MS via a POST request.
	 *
	 * @param ctx
	 *            the ctx
	 * @param tx
	 *            the tx
	 * @return the replication result
	 * @throws ReplicationException
	 *             the replication exception
	 */
	private ReplicationResult doActivate(TransportContext ctx, ReplicationTransaction tx) throws ReplicationException {

		final ReplicationLog log = tx.getLog();
		if (StringUtils.isNotEmpty(getServiceEndPoint())) {
			final HttpPost request = new HttpPost(getServiceEndPoint());
			final StringEntity entity = new StringEntity(getJsonString(), ContentType.APPLICATION_JSON);
			log.info("JSON : " + getJsonString());
			entity.setContentEncoding(HertzConstants.UTF8);
			request.setEntity(entity);
			final HttpResponse response = sendRequest(request);
			if (response != null) {
				final int statusCode = response.getStatusLine().getStatusCode();
				log.info(response.toString());
				log.info("---------------------------------------");
				if (statusCode == HttpStatus.SC_OK) {
					log.info("Notification POST call to MS succeeded");
					return ReplicationResult.OK;
				}
			} else {
				log.info("Notification POST call to MS failed");
			}
		} else {
			LOG.debug("The service path in configuration is not right. {}", dataPath, mcServicePath);
		}
		return new ReplicationResult(false, 0, "Notification POST to MS failed");
	}

	/**
	 * Send request.
	 *
	 * @param <T>
	 *            the generic type
	 * @param request
	 *            the request
	 * @return the http response
	 * @throws ReplicationException
	 *             the replication exception
	 */
	private <T extends HttpRequestBase> HttpResponse sendRequest(final T request) throws ReplicationException {

		request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
		request.setHeader(HttpHeaders.ACCEPT_CHARSET, HertzConstants.CHARSET_UTF_8);

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response;
		LOG.debug("Hitting serviceEndPoint {} with params {}.", serviceEndPoint, jsonString);

		request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getToken());
		try {
			response = client.execute(request);
		} catch (IOException e) {
			throw new ReplicationException("Could not send replication request.", e);
		}
		return response;
	}

	/**
	 * Initialize params.
	 *
	 * @param tx
	 *            the tx
	 * @param resolver
	 *            the resolver
	 * @param session
	 *            the session
	 */
	private void initializeParams(final ReplicationTransaction tx, ResourceResolver resolver, Session session) {

		Bundle bundle = FrameworkUtil.getBundle(MCDataCoordinatorService.class);
		ServiceReference[] services = null;
		if (bundle != null) {
			try {
				BundleContext bundleContext = bundle.getBundleContext();
				services = getService(tx, bundleContext);
				if (null != services && services.length > 0) {
					LOG.debug("Service reference obtained. {}", services[0]);
					MCDataCoordinatorService coordService = (MCDataCoordinatorService) bundleContext
							.getService(services[0]);
					checkCoordinatorData(resolver, session, coordService, tx);
				}
			} catch (InvalidSyntaxException e) {
				LOG.error("Error occured:- {}{}", e.getMessage(), e);
			}
		}
	}

	private void checkCoordinatorData(ResourceResolver resolver, Session session, MCDataCoordinatorService coordService, ReplicationTransaction tx) {
		// TODO Auto-generated method stub
		if (null != coordService) {
			CoordinatorConsumable consumable;
			try {
				consumable = coordService.getCollatedData(tx.getAction().getPath());
			
			LOG.debug("Building Coordinator consumable object. {}", consumable.ishealthy());
			if (checkPreRequisites(tx.getAction(), resolver, session) && consumable.ishealthy()) {
				setMcServicePath(StringUtils.trim(consumable.getApiUrl()));
				setJsonString(StringUtils.trim(consumable.getRequestParams()));
				setDataPath(StringUtils.trim(consumable.getDataPath()));
				setServiceEndPoint(getBasePath() + getMcServicePath());
			}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
				LOG.error("JSONException occured:- {}{}", e.getMessage(), e);
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				LOG.error("RepositoryException occured:- {}{}", e.getMessage(), e);
			}
		}
		
	}

	/**
	 * Gets the service.
	 *
	 * @param tx
	 *            the tx
	 * @param bundleContext
	 *            the bundle context
	 * @return the service
	 * @throws InvalidSyntaxException
	 *             the invalid syntax exception
	 */
	private ServiceReference[] getService(final ReplicationTransaction tx, BundleContext bundleContext)
			throws InvalidSyntaxException {
		return bundleContext
				.getServiceReferences(MCDataCoordinatorService.class.getName(),
						"(identifier="
								+ HertzUtils.getIdentifier(tx.getAction().getPath(),
										(String[]) hConfigFactory
												.getPropertyValue(HertzConstants.HERTZ_DATA_COORDINATOR_MAPPING))
								+ ")");
	}

	/**
	 * It checks and filters the initial request before handing over request to
	 * the coordinator service.
	 *
	 * @param action
	 *            The action.
	 * @param resolver
	 *            Resolver object
	 * @param session
	 *            the session
	 * @return The true/false flag.
	 */
	private boolean checkPreRequisites(ReplicationAction action, ResourceResolver resolver, Session session) {
		PageManager pageManager = resolver.adaptTo(PageManager.class);
		LOG.debug("The pre-requisites in order:- IsPage - {}, Type Activate - {}",
				pageManager.getPage(action.getPath()), action.getType().equals(ReplicationActionType.ACTIVATE));
		return null != pageManager.getPage(action.getPath()) && (action.getType().equals(ReplicationActionType.ACTIVATE)
				|| action.getType().equals(ReplicationActionType.TEST));
	}

	/**
	 * Gets the mc service path.
	 *
	 * @return the mc service path
	 */
	private String getMcServicePath() {
		return mcServicePath;
	}

	/**
	 * Sets the mc service path.
	 *
	 * @param mcServicePath
	 *            the new mc service path
	 */
	private void setMcServicePath(String mcServicePath) {
		this.mcServicePath = mcServicePath;
	}

	/**
	 * Gets the json string.
	 *
	 * @return the json string
	 */
	private String getJsonString() {
		return jsonString;
	}

	/**
	 * Sets the json string.
	 *
	 * @param jsonString
	 *            the new json string
	 */
	private void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	/**
	 * Sets the data path.
	 *
	 * @param dataPath
	 *            the new data path
	 */
	private void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	/**
	 * Sets the token path.
	 *
	 * @param tokenPath
	 *            the new token path
	 */
	private void setTokenPath(String tokenPath) {
		this.tokenPath = tokenPath;
	}

	/**
	 * Gets the base path.
	 *
	 * @return the base path
	 */
	private String getBasePath() {
		return basePath;
	}

	/**
	 * Sets the base path.
	 *
	 * @param basePath
	 *            the new base path
	 */
	private void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * Gets the password grant.
	 *
	 * @return the password grant
	 */
	private String getPasswordGrant() {
		return passwordGrant;
	}

	/**
	 * Sets the password grant.
	 *
	 * @param passwordGrant
	 *            the new password grant
	 */
	private void setPasswordGrant(String passwordGrant) {
		this.passwordGrant = passwordGrant;
	}

	/**
	 * Sets the pwd service url.
	 *
	 * @param pwdServiceUrl
	 *            the new pwd service url
	 */
	private void setPwdServiceUrl(String pwdServiceUrl) {
		this.pwdServiceUrl = pwdServiceUrl;
	}

	/**
	 * Gets the service end point.
	 *
	 * @return the service end point
	 */
	private String getServiceEndPoint() {
		return serviceEndPoint;
	}

	/**
	 * Sets the service end point.
	 *
	 * @param serviceEndPoint
	 *            the new service end point
	 */
	private void setServiceEndPoint(String serviceEndPoint) {
		this.serviceEndPoint = serviceEndPoint;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	private String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token
	 *            the new token
	 */
	private void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the token path.
	 *
	 * @return the token path
	 */
	private String getTokenPath() {
		return tokenPath;
	}
}
