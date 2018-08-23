package com.hertz.digital.platform.use;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Use class for checking if the service name has been
 * configured on the error data page or not.
 * 
 * @author himanshu.i.sharma
 *
 */
public class ServiceNotificationDataPageUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public ServiceNotificationDataPageUse() {
		super();
	}

	/**
	 * LOGGER instantiation
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceNotificationDataPageUse.class);
	
	/**
	 * key values are set in this bean list
	 */
	private String serviceName = StringUtils.EMPTY;
	private Session session;
	
	/**
	 * Activate method
	 */
	@Override
	public void activate() {
		LOGGER.debug("Start:- activate() of ServiceNotificationDataPageUse");
		ResourceResolver resolver = null;
		try {
			resolver = getResourceResolver();
			session = resolver.adaptTo(Session.class);
			getServiceName();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Repository Exception {}", e.getMessage());
		}
		LOGGER.debug("End:- activate() of ServiceNotificationDataPageUse");
	}
	
	/**
	 * This method gets the page content resource and service name property from its value map
	 * @throws RepositoryException 
	 */
	public void getServiceName() throws RepositoryException{
		LOGGER.debug("Start:- getServiceName() of ServiceNotificationDataPageUse");
		Resource jcrContentResource = getResource();
		Resource pageResource = jcrContentResource.getParent();
		ValueMap resourceProperties = jcrContentResource.getValueMap();
		serviceName = HertzUtils.getValueFromMap(resourceProperties, HertzConstants.SERVICE).trim();
		if(StringUtils.isEmpty(serviceName)){
			getServiceNameFromParent(pageResource.getParent(),jcrContentResource);
		}
		LOGGER.debug("End:- getServiceName() of ServiceNotificationDataPageUse");
	}
	
	/**
	 * This method iterates through the par resource of parent page
	 * @param parentResource
	 * @param jcrContentResource
	 * @throws RepositoryException
	 */
	public void getServiceNameFromParent(Resource parentResource, Resource jcrContentResource) throws RepositoryException{
		LOGGER.debug("Start:- getServiceNameFromParent() of ServiceNotificationDataPageUse");
		Resource parentContentResource = parentResource.getChild(HertzConstants.JCR_CONTENT);
		if(null!=parentContentResource.getChild(HertzConstants.PAR)){
			Resource parResource = parentContentResource.getChild(HertzConstants.PAR);
			getServiceNameComponent(parResource, jcrContentResource);
		}
		LOGGER.debug("End:- getServiceNameFromParent() of ServiceNotificationDataPageUse");
	}
	
	/**
	 * This method retrieves the serviceName property from component value map
	 * @param parResource
	 * @param jcrContentResource
	 * @throws RepositoryException
	 */
	public void getServiceNameComponent(Resource parResource, Resource jcrContentResource) throws RepositoryException{
		LOGGER.debug("Start:- getServiceNameComponent() of ServiceNotificationDataPageUse");
		Iterable<Resource> componentResources = parResource.getChildren();
	    for(Resource componentResource : componentResources){
		    if(componentResource.getResourceType().equalsIgnoreCase(HertzConstants.SERVICE_NAME_COMPONENT_RES_TYPE)){
		    	ValueMap componentProperties = componentResource.getValueMap();
		    	if(componentProperties.containsKey(HertzConstants.SERVICE_NAME)){
		    		serviceName = HertzUtils.getValueFromMap(componentProperties, HertzConstants.SERVICE_NAME).trim();
		    		setServiceNameInChild(serviceName,jcrContentResource);
		    	}
		    	
		    }
	    }
	    LOGGER.debug("End:- getServiceNameComponent() of ServiceNotificationDataPageUse");
	}
	
	/**
	 * This method sets the service name property in child page
	 * @param serviceNameFromParent
	 * @param jcrContentResource
	 * @throws RepositoryException
	 */
	public void setServiceNameInChild(String serviceNameFromParent,Resource jcrContentResource) throws RepositoryException{
		LOGGER.debug("Start:- setServiceNameInChild() of ServiceNotificationDataPageUse");
		Node jcrContentNode = jcrContentResource.adaptTo(Node.class);
		jcrContentNode.setProperty(HertzConstants.SERVICE, serviceNameFromParent);
		session.save();
		LOGGER.debug("End:- setServiceNameInChild() of ServiceNotificationDataPageUse");
	}
}
