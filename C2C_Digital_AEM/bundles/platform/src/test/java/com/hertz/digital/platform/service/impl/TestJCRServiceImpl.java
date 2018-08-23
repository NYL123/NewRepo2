package com.hertz.digital.platform.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
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
import com.day.cq.search.result.SearchResult;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestJCRServiceImpl {

	@InjectMocks
	private JCRServiceImpl mockObject;

	@Mock
	ResourceResolver resolver;

	@Mock
	Session session;
	
	@Mock
	QueryBuilder builder;
	
	@Mock
	Query query;
	
	@Mock
	SearchResult result;
	
	@Mock
	PredicateGroup predicateGroup;

	Logger log;

	Map<String, String> param =new HashMap<>();
	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockObject = PowerMockito.mock(JCRServiceImpl.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		Field field = JCRServiceImpl.class.getDeclaredField("LOGGER");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, log);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		Mockito.doCallRealMethod().when(mockObject).searchResults(Mockito.any(ResourceResolver.class), Mockito.anyMap());
	}

	@Test
	public void testSearchResults() throws RepositoryException {
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
		when(builder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getPredicates()).thenReturn(predicateGroup);
		when(predicateGroup.toString()).thenReturn("predicateGroup");
		when(query.getResult()).thenReturn(result);
		when(session.isLive()).thenReturn(true);
		mockObject.searchResults(resolver, param);

	}
	
	@Test
	public void testSearchResults1() throws RepositoryException {
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
		when(builder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getPredicates()).thenReturn(predicateGroup);
		when(predicateGroup.toString()).thenReturn("predicateGroup");
		when(query.getResult()).thenReturn(result);
		when(session.isLive()).thenReturn(false);
		mockObject.searchResults(resolver, param);
	}
	
	@Test
	public void testSearchResults2() throws RepositoryException {
		when(resolver.adaptTo(Session.class)).thenReturn(null);
		when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
		when(builder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getPredicates()).thenReturn(predicateGroup);
		when(predicateGroup.toString()).thenReturn("predicateGroup");
		when(query.getResult()).thenReturn(result);
		when(session.isLive()).thenReturn(false);
		mockObject.searchResults(resolver, param);
	}
	
	@Test
	public void testSearchResults3() throws RepositoryException {
		when(resolver.adaptTo(Session.class)).thenReturn(null);
		when(resolver.adaptTo(QueryBuilder.class)).thenReturn(builder);
		when(builder.createQuery(any(PredicateGroup.class), eq(session))).thenReturn(query);
		when(query.getPredicates()).thenReturn(predicateGroup);
		when(predicateGroup.toString()).thenReturn("predicateGroup");
		when(query.getResult()).thenReturn(result);
		when(session.isLive()).thenReturn(true);
		mockObject.searchResults(resolver, param);
	}

}
