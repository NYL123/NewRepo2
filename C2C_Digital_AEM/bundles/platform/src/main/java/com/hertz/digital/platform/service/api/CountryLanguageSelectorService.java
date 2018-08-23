package com.hertz.digital.platform.service.api;

import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONException;

import com.hertz.digital.platform.bean.CountryLanguageItemsBean;

/**
 * Interface for the Country Language Selector Service
 * @author himanshu.i.sharma
 *
 */
@FunctionalInterface
public interface CountryLanguageSelectorService {

	/**
	 * This method is used to get country language json
	 * @param resource
	 * @return
	 * @throws LoginException
	 * @throws RepositoryException
	 * @throws JSONException
	 */
	public List<CountryLanguageItemsBean> getCountryLanguageJson(Resource resource) throws LoginException, RepositoryException, JSONException;
}
