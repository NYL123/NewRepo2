package com.hertz.digital.platform.service.impl;


import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.hertz.digital.platform.service.api.JCRService;

/**
 * This class contain the method to hit the repository using xpath queries and
 * return hits.
 * 
 * @author n.kumar.singhal
 * @version 1.0
 */
@Component(description = "Hertz Oak Search Service", immediate = true, metatype = false, label = "Hertz Oak Search Service")
@Service
public class JCRServiceImpl implements JCRService {

	
	/**
	 *Default Constructor Declaration 
	 */
	public JCRServiceImpl(){
		super();
	}
	
	/** LOGGER instantiation. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JCRServiceImpl.class);

	/**
	 * The method to compile the predicates and execute the query.
	 * 
	 * @param resourceResolver
	 *            The resource resolver.
	 * @param param
	 *            The parameter map consisting of the predicates.
	 * @return The hits based on the query.
	 * @throws RepositoryException
	 *             In case query is unsuccessful.
	 */
	@Override
	public SearchResult searchResults(final ResourceResolver resourceResolver, final Map<String, String> param) throws RepositoryException {
		final Session session = resourceResolver.adaptTo(Session.class);
		SearchResult result=null;
		try{
            if (null != session) {
            	final QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
            	final Query query = queryBuilder.createQuery(PredicateGroup.create(param), session);
            	LOGGER.debug("Query to be executed: " + query.getPredicates().toString());
            	result = query.getResult();
            }
		}finally{
			if(null!=session && session.isLive()){
				session.save();
			}
		}
	
		return result;
	}
}

