package com.hertz.digital.platform.jobs;

import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Set;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.event.jobs.Job;
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
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.SystemUserService;

import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class, JcrUtils.class})
public class TestMCUpdateJobConsumer {
	
	@InjectMocks
	private MCUpdateJobConsumer mockJob;
	
	@Mock
	SystemUserService systemService;
	
	@Mock
	Job job;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	Session session;
	
	@Mock
	Set<String> value;
	
	@Mock
	Node node;
	
	@Mock
	Node node1;
	
	@Mock
	Property property;
	
	@Before
	public final void setup() throws NoSuchFieldException{
		MockitoAnnotations.initMocks(this);
		mockJob=PowerMockito.mock(MCUpdateJobConsumer.class);
		PrivateAccessor.setField(mockJob, "systemService", systemService);
		Mockito.doCallRealMethod().when(mockJob).process(job);
	}
	
	@Test
	public final void testProcess() throws LoginException, RepositoryException{
		when(systemService.getServiceResourceResolver()).thenReturn(resolver);
		when(resolver.adaptTo(Session.class)).thenReturn(session);
		when(session.getRootNode()).thenReturn(node);
		when(node.getSession()).thenReturn(session);
		when(node.hasNode(Mockito.anyString())).thenReturn(true);
		when(node.getNode(Mockito.anyString())).thenReturn(node1);
		when(node1.getProperty(Mockito.anyString())).thenReturn(property);
		when(property.getString()).thenReturn("token");
		when(job.getProperty(HertzConstants.SERVICE_PATH, String.class)).thenReturn(".com");
		when(job.getProperty(HertzConstants.JSON_STRING, String.class)).thenReturn("jsonstring");
		when(job.getProperty(HertzConstants.DATA_PATH, String.class)).thenReturn("datapath");
		when(job.getProperty(HertzConstants.TOKEN_PATH,String.class)).thenReturn("token");
		when(job.getProperty(HertzConstants.BASE_PATH,String.class)).thenReturn("http://www.hertz");
		when(job.getProperty(HertzConstants.PASS_GRANT, String.class)).thenReturn(".com");
		when(job.getPropertyNames()).thenReturn(value);
		mockJob.process(job);
		when(job.getProperty(HertzConstants.SERVICE_PATH, String.class)).thenReturn(StringUtils.EMPTY);
		when(job.getProperty(HertzConstants.BASE_PATH,String.class)).thenReturn(StringUtils.EMPTY);
		mockJob.process(job);
		
	}
	

}
