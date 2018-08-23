package com.hertz.digital.platform.service.api;

import javax.jcr.RepositoryException;

import org.apache.sling.commons.json.JSONException;

import com.hertz.digital.platform.bean.CoordinatorConsumable;

/**
 * This service is responsible for binding the request to a correct coordinator
 * back-end service.
 * 
 * @author n.kumar.singhal
 *
 */
public interface MCDataCoordinatorService {
	/**
	 * Method exposed to check whether the action path is whitelisted for
	 * performing business logic.
	 * 
	 * @param path
	 *            The action path.
	 * @return The flag suggesting the status of the page.
	 */
	boolean isWhitelisted(String path);

	/**
	 * This method is responsible to provide the collated data to the using
	 * classes.
	 * 
	 * @param path
	 *            The action path supplied.
	 * @return The consumable object.
	 * @throws RepositoryException
	 *             The repository exception.
	 * @throws JSONException
	 *             The JSON parsing exception.
	 */
	CoordinatorConsumable getCollatedData(String path) throws JSONException, RepositoryException;
}
