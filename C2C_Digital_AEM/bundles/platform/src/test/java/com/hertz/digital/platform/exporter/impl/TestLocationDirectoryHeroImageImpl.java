package com.hertz.digital.platform.exporter.impl;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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
import static org.mockito.Mockito.mock;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.bean.HeroImageBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({  LoggerFactory.class,HertzUtils.class })
public class TestLocationDirectoryHeroImageImpl {
	@InjectMocks
	LocationDirectoryHeroImageImpl exporterImpl;
	
	Logger log;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	PageManager pageManager;
	
	@Mock
	Page page;
	
	@Mock
	HeroImageBean heroImageBean;
	
	@Mock
	Resource  component;
	
	@Mock
	ValueMap properties;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	ValueMap map1;
	
	@Mock
	Page parentPage;
	
	@Mock
	Resource heroImageResource;
	
	String altTxt = "altTxt";
	
	@Mock
	HertzConfigFactory hFactory;
	
	@Before
	public final void setup() throws Exception{
	exporterImpl =	new LocationDirectoryHeroImageImpl();
	PowerMockito.mockStatic(LoggerFactory.class);
	log = Mockito.mock(Logger.class);
	when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
	MockitoAnnotations.initMocks(this);
	
	}
	@Test
	public final void testexportAsJson() throws Exception{
		String str =exporterImpl.exportAsJson(component, resourceResolver);
		Assert.assertNull(str);
	}
	
	/*@Test
	public final void testExportAsBean() throws Exception{
		when(component.getValueMap()).thenReturn(properties);
		when(properties.containsKey(HertzConstants.ALT_TEXT)).thenReturn(true);
		when(properties.get(HertzConstants.ALT_TEXT)).thenReturn(altTxt);
		when(properties.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(true);
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);		
	}*/
	
	@Test
	public final void testExportAsBean1() throws Exception{
		PowerMockito.mockStatic(HertzUtils.class);
    	PowerMockito.when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(hFactory);
		when(page.getAbsoluteParent(4)).thenReturn(parentPage);
		when(parentPage.getContentResource(HertzConstants.HERO)).thenReturn(heroImageResource);
		when(heroImageResource.getValueMap()).thenReturn(map1);
		when(map1.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(true);
		exporterImpl.setHeroImageSource(heroImageBean, page);
		exporterImpl.setHeroImageSource(heroImageBean, null);
		//Assert.assertNotNull(object);		
	}
	
	@Test
	public final void testExportAsBean3() throws Exception{
		when(page.getAbsoluteParent(4)).thenReturn(null);
		exporterImpl.setHeroImageSource(heroImageBean, page);
		when(page.getAbsoluteParent(4)).thenReturn(parentPage);
		when(parentPage.getContentResource(HertzConstants.HERO)).thenReturn(null);
		exporterImpl.setHeroImageSource(heroImageBean, page);
		//Assert.assertNotNull(object);		
	}
	
	@Test
	public final void testExportAsBean4() throws Exception{
		when(page.getAbsoluteParent(4)).thenReturn(parentPage);
		when(parentPage.getContentResource(HertzConstants.HERO)).thenReturn(heroImageResource);
		when(heroImageResource.getValueMap()).thenReturn(map1);
		when(map1.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(false);
		exporterImpl.setHeroImageSource(heroImageBean, page);
		//Assert.assertNotNull(object);		
	}
	
	@Test
	public final void testExportAsBean2() throws Exception{
		PowerMockito.mockStatic(HertzUtils.class);
    	PowerMockito.when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(hFactory);
		when(component.getValueMap()).thenReturn(properties);
		when(properties.containsKey(HertzConstants.ALT_TEXT)).thenReturn(true);
		when(properties.get(HertzConstants.ALT_TEXT)).thenReturn(altTxt);
		when(properties.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(false);
		when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		when(pageManager.getContainingPage(component)).thenReturn(page);
		when(page.getAbsoluteParent(4)).thenReturn(parentPage);
		when(parentPage.getContentResource(HertzConstants.HERO)).thenReturn(heroImageResource);
		when(heroImageResource.getValueMap()).thenReturn(map1);
		when(map1.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(true);
		Object object =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object);
		when(properties.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(true);
		when(properties.containsKey(HertzConstants.ALT_TEXT)).thenReturn(false);
		Object object1 =exporterImpl.exportAsBean(component, resourceResolver);
		Assert.assertNotNull(object1);
	}
	
	@Test
	public final void testExportAsBean5() throws Exception{
		Object object1 =exporterImpl.exportAsBean(null, resourceResolver);
		Assert.assertNull(object1);
	}
	
	@Test
	public final void testExportAsMap() throws Exception{
		Assert.assertNull(exporterImpl.exportAsMap(resolver, component));
	}
}
