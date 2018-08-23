package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

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

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.constants.HertzConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestHeader {
	private static final String TOP_NAV_PATH = "topnavpath";
	private static final String TOPNAV_PAGE_NAME = "topnav";
	@InjectMocks
	Header header;

	Logger log;

	@Mock
	Session session;

	@Mock
	ResourceResolver resourceResolver;
	@Mock
	PageManager pageManager;
	
	@Mock
	Page page;
	
	

	@Before
	public final void setup() throws Exception {
		header =PowerMockito.mock(Header.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(header).activate();
	}
 
	@Test
	public final void testActivate() throws Exception {
		when(header.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(PageManager.class)).thenReturn(
				pageManager);
		String path = "/some/dummy/value";
		when(header.getCurrentPage()).thenReturn(page);
		when(page.getPath()).thenReturn(path);
		header.activate();
	}

}
