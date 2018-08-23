package com.hertz.digital.platform.servlets;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
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

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.commons.util.DamUtil;
import com.day.cq.wcm.foundation.Image;
import com.day.image.Layer;
import com.hertz.digital.platform.factory.HertzConfigFactory;

import junitx.util.PrivateAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, DamUtil.class })
public class TestHertzImageRenderingServlet {
	@InjectMocks
	private HertzImageRenderingServlet hertzImageRenderingServlet;

	Logger log;

	@Mock
	SlingHttpServletRequest request;

	@Mock
	SlingHttpServletResponse response;

	@Mock
	PrintWriter writer;

	@Mock
	RequestPathInfo pathInfo;

	@Mock
	HertzConfigFactory hConfigFactory;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	ResourceResolver resourceResolver1;

	@Mock
	Resource resource;

	@Mock
	Resource resource1;

	@Mock
	Resource resource2;

	@Mock
	Layer layer;

	@Mock
	Asset asset;

	@Mock
	Rendition rendition;

	@Mock
	Image image;
	@Mock
	ValueMap valueMap;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		hertzImageRenderingServlet = new HertzImageRenderingServlet();
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);

		Field field = HertzImageRenderingServlet.class.getDeclaredField("LOGGER");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, log);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);

		PrivateAccessor.setField(hertzImageRenderingServlet, "hConfigFactory", hConfigFactory);
		when(hConfigFactory.getStringPropertyValue("hertz.renditions.quality")).thenReturn("96");
		when(hConfigFactory.getPropertyValue("hertz.renditions.selectornamesmapping"))
				.thenReturn(new String[] { "large-2x:large2x", "large-1x:large1x" });
		when(hConfigFactory.getStringPropertyValue("hertz.renditions.extension")).thenReturn("png");
	}

	@Test
	public final void testDoGet() throws Exception {

		when(request.getRequestPathInfo()).thenReturn(pathInfo);
		when(pathInfo.getResourcePath()).thenReturn("/content/hertz-rac/en-us/abc");
		when(pathInfo.getSelectors()).thenReturn(new String[] { "enscale", "large-1x" });

		when(request.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(valueMap);
		when(valueMap.get("fileReference")).thenReturn("/content/hertz-rac/en-us/abc.jpg");

		PowerMockito.mockStatic(DamUtil.class);

		when(resourceResolver.getResource("/content/hertz-rac/en-us/abc.jpg")).thenReturn(resource1);
		PowerMockito.when(DamUtil.resolveToAsset(resource1)).thenReturn(asset);
		when(asset.getRendition(Mockito.anyString())).thenReturn(rendition);
		when(asset.getOriginal()).thenReturn(rendition);
		when(rendition.getPath()).thenReturn("/content/hertz-rac/en-us/abc.jpg");
		when(resource1.getPath()).thenReturn("/content/hertz-rac/en-us/abc.jpg");

		when(resource1.getResourceResolver()).thenReturn(resourceResolver1);
		when(resourceResolver1.getResource("/content/hertz-rac/en-us/abc.jpg")).thenReturn(resource2);

		when(image.getMimeType()).thenReturn("image/png");
		when(image.getLayer(Mockito.anyBoolean(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(layer);

		hertzImageRenderingServlet.doGet(request, response);

	}

}
