package com.hertz.digital.platform.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.service.api.SystemUserService;

/**
 * 
 * User mapper Service Implementation for getting the system user.
 * 
 * @author n.kumar.singhal
 *
 */
@Component(immediate = true, metatype = false)
@Service(value = SystemUserService.class)
public class SystemUserServiceImpl implements SystemUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemUserServiceImpl.class);

	@Reference
	private ResourceResolverFactory resolverFactory;

	/**
	 * Constructor
	 */
	public SystemUserServiceImpl() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hertz.digital.platform.service.api.SystemUserService#
	 * getServiceResourceResolver()
	 */

	@Override
	public ResourceResolver getServiceResourceResolver() {
		Map<String, Object> param = new HashMap<>();
		param.put(ResourceResolverFactory.SUBSERVICE, "systemUserService");
		ResourceResolver resolver = null;

		try {
			resolver = resolverFactory.getServiceResourceResolver(param);
		} catch (LoginException e) {
			LOGGER.error("Error while obtaining the service resource resolver{} {}", e, e.getStackTrace());
		}
		return resolver;
	}

}
