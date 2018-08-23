package com.hertz.digital.platform.service.impl;

import java.io.IOException;
import java.util.HashMap;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class AbstractMCDataCoordinatorTest {
	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testIsValidDataPath() throws SAXException, IOException {
		AbstractMCDataCoordinator abstractTransformer = Mockito.spy(AbstractMCDataCoordinator.class);

		String path = "/content/hertz-rac/data/abc";
		String[] basePaths = new String[] { TestConstants.CONTENT_DATA_PATH, TestConstants.CONTENT_MOBILE_DATA_PATH };
		boolean result = abstractTransformer.isValidDataPath(path, basePaths);
		Assert.assertTrue(result);

		path = "";
		basePaths = new String[] { TestConstants.CONTENT_DATA_PATH, TestConstants.CONTENT_MOBILE_DATA_PATH };
		result = abstractTransformer.isValidDataPath(path, basePaths);
		Assert.assertTrue(!result);

		path = "/content/hertz-rac/data/abc";
		basePaths = new String[] { "/content/hertz-rac1/data", "/content/hertz-rac1/rac-mobile/data" };
		result = abstractTransformer.isValidDataPath(path, basePaths);
		Assert.assertTrue(!result);
	}

	@Test
	public void testGetBasePath() {
		String path = "/content/hertz-rac/data/abc";
		String[] basePaths = new String[] { TestConstants.CONTENT_DATA_PATH, TestConstants.CONTENT_MOBILE_DATA_PATH };
		AbstractMCDataCoordinator abstractTransformer = Mockito.spy(AbstractMCDataCoordinator.class);
		String result = abstractTransformer.getBasePath(path, basePaths);
		Assert.assertNotNull(result);

		path = "";
		basePaths = new String[] { TestConstants.CONTENT_DATA_PATH, TestConstants.CONTENT_MOBILE_DATA_PATH };
		result = abstractTransformer.getBasePath(path, basePaths);
		Assert.assertNotNull(result);

		path = "/content/hertz-rac/data/abc1";
		basePaths = new String[] { "/content/hertz-rac1/data", "/content/hertz-rac1/rac-mobile/data" };
		result = abstractTransformer.getBasePath(path, basePaths);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCheckOnlyMapping() {
		AbstractMCDataCoordinator abstractTransformer = Mockito.spy(AbstractMCDataCoordinator.class);
		String path = "/content/hertz-rac/data/rates-config/default-rq-codes/us/en/abc";
		String[] mappings = new String[] { "/rates-config/default-rq-code:rqcodes", "/locations:location" };
		boolean result = abstractTransformer.checkOnlyMapping(path, mappings);
		Assert.assertTrue(result);

		path = "/content/hertz-rac/data/rates-config-test/default-rq-codes/us/en/abc";
		mappings = new String[] { "/rates-config/default-rq-code:rqcodes", "/locations:location" };
		result = abstractTransformer.checkOnlyMapping(path, mappings);
		Assert.assertTrue(!result);
	}

	@Test
	public void testCheckMappingAndTemplate() {
		AbstractMCDataCoordinator abstractTransformer = Mockito.spy(AbstractMCDataCoordinator.class);
		String path = "/content/hertz-rac/data/rates-config/default-rq-codes/us/en/abc";
		String[] mappings = new String[] { "/rates-config/default-rq-code:rqcodes", "/locations:location" };
		String[] templateMappings = new String[] { "location:/apps/hertz/templates/locationdata", "test:" };
		String identifier = "location";
		ResourceResolver resolver = Mockito.mock(ResourceResolver.class);
		PageManager pageManager = Mockito.mock(PageManager.class);
		Page page = Mockito.mock(Page.class);
		PowerMockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		PowerMockito.when(pageManager.getPage(Mockito.anyString())).thenReturn(page);
		Resource resource = Mockito.mock(Resource.class);
		PowerMockito.when(page.getContentResource()).thenReturn(resource);
		ValueMap valueMap = Mockito.mock(ValueMap.class);
		valueMap = new ValueMapDecorator(new HashMap());
		valueMap.put(HertzConstants.JCR_CQ_TEMPLATE, "/apps/hertz/templates/locationdata");

		PowerMockito.when(resource.getValueMap()).thenReturn(valueMap);
		
		boolean result = abstractTransformer.checkMappingAndTemplate(resolver, path, mappings, templateMappings,
				identifier);
		Assert.assertTrue(result);

		mappings = new String[] { "/rates-confi/default-rq-code:rqcodes", "/locations:location" };
		result = abstractTransformer.checkMappingAndTemplate(resolver, path, mappings, templateMappings, identifier);
		Assert.assertTrue(!result);

		mappings = new String[] { "/rates-config/default-rq-code:rqcodes", "/locations:location" };
		valueMap.put(HertzConstants.JCR_CQ_TEMPLATE, "/apps/hertz/templates/locationdata123");
		result = abstractTransformer.checkMappingAndTemplate(resolver, path, mappings, templateMappings, identifier);
		Assert.assertTrue(!result);

	}

	@Test
	public void testGetAPIUrl() {
		AbstractMCDataCoordinator abstractTransformer = Mockito.spy(AbstractMCDataCoordinator.class);
		String identifier = "location";
		String[] mappings = new String[] { "rqcodes:/api/rateCodeService/defaultRateCode",
				"location:/api/locationService" };
		String url = abstractTransformer.getAPIUrl(identifier, mappings);
		Assert.assertNotNull(url);

		mappings = new String[] { "rqcodes:/api/rateCodeService/defaultRateCode", "location1:/api/locationService" };
		Assert.assertNotNull(url);
	}

}
