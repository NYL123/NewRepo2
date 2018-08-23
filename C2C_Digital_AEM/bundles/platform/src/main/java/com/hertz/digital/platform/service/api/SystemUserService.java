package com.hertz.digital.platform.service.api;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * User mapper Service for getting the system user.
 * 
 * @author n.kumar.singhal
 *
 */
@FunctionalInterface 
public interface SystemUserService {

	/**
	 * This method returns the service resource resolver and admin resource
	 * resolver as a fallback.
	 * 
	 * @return
	 */
	public ResourceResolver getServiceResourceResolver();

}
