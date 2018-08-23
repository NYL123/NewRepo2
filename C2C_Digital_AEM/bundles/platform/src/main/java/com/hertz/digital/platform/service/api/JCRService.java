package com.hertz.digital.platform.service.api;

import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.RepositoryException;
import java.util.Map;

/**
 * The Interface JCRService.
 * 
 * @author psharma
 * @version 1.0
 */
@FunctionalInterface
public interface JCRService {

	/**
	 * Search results.
	 * 
	 * @param resource
	 *            the resource
	 * @param param
	 *            the param
	 * @return the search result
	 * @throws RepositoryException
	 *             the repository exception
	 */
	SearchResult searchResults(ResourceResolver resourceResolver,
			Map<String, String> param) throws RepositoryException;

}
