/**
 * 
 */
package com.hertz.digital.platform.jobs;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.listeners.DataReplicationEventListener;
import com.hertz.digital.platform.service.api.SystemUserService;
import com.hertz.digital.platform.utils.HertzUtils;
import com.hertz.digital.platform.utils.HttpUtils;

/**
 * <p>
 * This class is a custom implementation of the Sling job consumer
 * {@link JobConsumer}. The Sling Job is created by the entry point Listener
 * {@link DataReplicationEventListener} with all the relevant details. The sling
 * job consumer, subsequently processes the job object , hits the Micro Services
 * API, and fetches the response accordingly.
 * </p>
 *
 * @author n.kumar.singhal Written on:- Nov 30, 2016
 * 
 * 
 */
@Component
@Service(value = { JobConsumer.class })
@Property(name = JobConsumer.PROPERTY_TOPICS, value = "com/sling/hertz/microservices/update")
public class MCUpdateJobConsumer implements JobConsumer {

	/** LOGGER instantiation. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MCUpdateJobConsumer.class);

	@Reference
	private transient SystemUserService systemService;

	/**
	 * Default Constructors
	 */
	public MCUpdateJobConsumer() {
		super();
	}

	@Override
	public JobResult process(Job job) {
		LOGGER.debug("Inside Method: process()");
		ResourceResolver resolver = systemService.getServiceResourceResolver();
		Session session = resolver.adaptTo(Session.class);
		try{
			String response = StringUtils.EMPTY;
			String mcServicePath = StringUtils.trim(job.getProperty(HertzConstants.SERVICE_PATH, String.class));
			String jsonString = StringUtils.trim(job.getProperty(HertzConstants.JSON_STRING, String.class));
			String dataPath = StringUtils.trim(job.getProperty(HertzConstants.DATA_PATH, String.class));
			String tokenPath = StringUtils.trim(job.getProperty(HertzConstants.TOKEN_PATH, String.class));
			String basePath = StringUtils.trim(job.getProperty(HertzConstants.BASE_PATH, String.class));
			String passwordGrant = StringUtils.trim(job.getProperty(HertzConstants.PASS_GRANT, String.class));
			String pwdServiceUrl = basePath + passwordGrant;
			String serviceEndPoint = basePath + mcServicePath;
			LOGGER.debug(
					"The JOB parameters: = JobProperties:- {}, serviceEndPoint:- {}, jsonString:- {}, dataPath:- {}, tokenPath:- {}, basePath:- {},passwordGrant:- {}",
					job.getPropertyNames(), serviceEndPoint, jsonString, dataPath, tokenPath, basePath, passwordGrant);
			if (StringUtils.isNotEmpty(serviceEndPoint)) {
				LOGGER.debug("Hitting serviceEndPoint {} with params {}.", serviceEndPoint, jsonString);
				response = HttpUtils.post(serviceEndPoint, jsonString, HertzConstants.CONTENT_TYPE_APP_JSON, true,
						HertzUtils.getOrCreateAccessToken(tokenPath, HertzConstants.PWD_GRANT_TOKEN_NODE_NAME,
								pwdServiceUrl, session));
			} else {
				LOGGER.debug("The service path in configuration is not right. {}", dataPath, mcServicePath);
			}
			
			LOGGER.debug("Exiting Method: process()");
			
			if (StringUtils.isNotEmpty(response) && response.contains(HertzConstants.SUCCESS) && !response.contains(HertzConstants.NOT)) {
				return JobResult.OK;
			} else {
				return JobResult.FAILED;
			}
		}finally{
			HertzUtils.closeResolverSession(resolver, session);
		}
		
	}

}
