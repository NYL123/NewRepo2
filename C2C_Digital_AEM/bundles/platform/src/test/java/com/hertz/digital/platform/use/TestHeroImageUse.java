package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestHeroImageUse {

	@InjectMocks
	HeroImageUse heroImageUse;
	
	@Mock
	ValueMap valueMap;

	Logger log;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	Page page;
	
	@Mock
	Resource resource;
	@Mock
	Asset asset;
	
	@Mock
	Resource resource1;
	
	@Mock
	Resource resource2;
	
	

	@Before
	public final void setup() throws Exception {
		heroImageUse =PowerMockito.mock(HeroImageUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(heroImageUse).activate();

	}
	
	@Test
	public final void testActivate() throws Exception {
		when(heroImageUse.getResourceResolver()).thenReturn(resourceResolver);
		when(heroImageUse.getProperties()).thenReturn(valueMap);
		when(valueMap.get("altText", String.class)).thenReturn("ALt Text");
		when(valueMap.get("taglineText", String.class)).thenReturn("taglineText");
		when(valueMap.get("subTagLineText",String.class)).thenReturn("subTagLineText");
		when(valueMap.get("backgroundImage", String.class)).thenReturn("backgroundImage");
		when(resourceResolver.getResource("backgroundImage")).thenReturn(resource);
		when(resource.adaptTo(Asset.class)).thenReturn(asset);
		String path = "path";
		when(heroImageUse.getResource()).thenReturn(resource1);
		when(resource1.getPath()).thenReturn(path);
		when(resourceResolver.getResource(path+ "/file")).thenReturn(resource2);
		when(valueMap.get("fileName")).thenReturn("Image.jpg"); 
		
		//getProperties().get("fileName")
		
		heroImageUse.activate();
	}

	@Test
	public final void testVariables() {
		Mockito.doCallRealMethod().when(heroImageUse).setAltText("Test/String");
		Mockito.doCallRealMethod().when(heroImageUse).setImagePath("Imagepath");//AltText("Test/String");
		Mockito.doCallRealMethod().when(heroImageUse).setTagLineText("test");
		Mockito.doCallRealMethod().when(heroImageUse).setSubTagLineText("subTagLineText");
		Mockito.doCallRealMethod().when(heroImageUse).getTagLineText();
		Mockito.doCallRealMethod().when(heroImageUse).getImagePath();
		Mockito.doCallRealMethod().when(heroImageUse).getAltText();
		Mockito.doCallRealMethod().when(heroImageUse).getSubTagLineText();
		heroImageUse.setAltText("Test/String");
		heroImageUse.setImagePath("Imagepath");
		heroImageUse.setTagLineText("test");
		heroImageUse.setSubTagLineText("subTagLineText");
		Assert.assertNotNull(heroImageUse.getAltText());
		Assert.assertNotNull(heroImageUse.getTagLineText());
		Assert.assertNotNull(heroImageUse.getImagePath());
		Assert.assertTrue(heroImageUse.getSubTagLineText().equalsIgnoreCase("subTagLineText"));
	}
	
}
