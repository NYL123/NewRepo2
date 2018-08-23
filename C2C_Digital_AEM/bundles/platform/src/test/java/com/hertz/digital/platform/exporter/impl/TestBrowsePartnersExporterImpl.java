/**
 * 
 */
package com.hertz.digital.platform.exporter.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.service.api.JCRService;

import junitx.util.PrivateAccessor;



/**
 * @author a.dhingra
 *
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class})
public class TestBrowsePartnersExporterImpl {

	@InjectMocks
	private BrowsePartnersExporterImpl exporterImpl;
	
	@Mock
	Resource component;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	Resource parentResource;
	
	@Mock
	Session session;
	
	@Mock
	QueryBuilder builder;
	
	@Mock
	Query query;
	
	@Mock
	PredicateGroup predicateGroup;
	
	@Mock
	SearchResult result;
	
	@Mock
	JCRService jcrService;
	
	
	Logger log;
	
	List<Hit> hitList=new ArrayList<Hit>();
	
	@Mock
	Hit hit;
	
	@Mock
	Resource hitResource;

	
	
	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		exporterImpl=PowerMockito.mock(BrowsePartnersExporterImpl.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		Mockito.doCallRealMethod().when(exporterImpl).exportAsBean(component, resolver);
		Mockito.doCallRealMethod().when(exporterImpl).exportAsJson(component, resolver);
		Mockito.doCallRealMethod().when(exporterImpl).exportAsMap(resolver, component);
	}
	
	@Test
	public final void testExportAsJson(){
		Assert.assertNotNull(exporterImpl.exportAsJson(component, resolver));
	}
	
	@Test
	public final void testExportAsMap() throws JSONException, RepositoryException{
		Assert.assertNull(exporterImpl.exportAsMap(resolver, component));
	}
	
	@Test
	public final void testExportAsBean() throws RepositoryException, NoSuchFieldException{
		PrivateAccessor.setField(exporterImpl, "jcrService", jcrService);
		hitList.add(hit);
		when(component.getParent()).thenReturn(parentResource);
		when(parentResource.getPath()).thenReturn(TestConstants.PARTNER_LANDING_PATH);
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
		when(builder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getPredicates()).thenReturn(predicateGroup);
		when(predicateGroup.toString()).thenReturn("predicateGroup");
		when(query.getResult()).thenReturn(result);
		when(result.getHits()).thenReturn(hitList);
		when(hit.getResource()).thenReturn(hitResource);
		when(hit.getTitle()).thenReturn("Partner Landing Page");
		when(hitResource.getPath()).thenReturn(TestConstants.PARTNER_DETAIL_PAGE);
		when(jcrService.searchResults(Mockito.eq(resolver), Mockito.any(HashMap.class))).thenReturn(result);
		exporterImpl.exportAsBean(component, resolver);
		PrivateAccessor.setField(exporterImpl, "jcrService", null);
		exporterImpl.exportAsBean(component, resolver);
	}
}
