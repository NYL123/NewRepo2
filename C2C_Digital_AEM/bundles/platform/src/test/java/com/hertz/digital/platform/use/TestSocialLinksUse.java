package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
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

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.common.collect.Lists;
import com.hertz.digital.platform.bean.SocialLinkBean;
import com.hertz.digital.platform.constants.HertzConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WCMUsePojo.class, LoggerFactory.class, ResourceUtil.class, Lists.class})
public class TestSocialLinksUse {

	@InjectMocks
	private SocialLinksUse socialLinksUse;
	
	Logger log;
	
	@Mock
	ValueMap map1;
	
	@Mock
	ValueMap map2;
	
	@Mock
	Resource resource1;
	
	@Mock
	Resource resource2;
	
	@Mock
	Resource resource3;
	
	@Mock
	private Iterable<Resource> mockIterable;

	@Mock
	private Iterator mockIterator;
	
	@Mock
	SocialLinkBean socialLinkBean;
	
	List<SocialLinkBean> mockList=new ArrayList<SocialLinkBean>();
	
	String socialLinksSubtitle="Social Links Subtitle";
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		socialLinksUse=PowerMockito.mock(SocialLinksUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log=Mockito.mock(Logger.class);
		Field field=SocialLinksUse.class.getDeclaredField("logger");
		field.setAccessible(true);
		Field modifiersField=Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL); 
		field.set(null,log);	
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);	
		socialLinkBean=PowerMockito.mock(SocialLinkBean.class);
		mockList.add(socialLinkBean);
		Mockito.doCallRealMethod().when(socialLinksUse).activate();
		Mockito.doCallRealMethod().when(socialLinksUse).getSocialLinksValues();
		Mockito.doCallRealMethod().when(socialLinksUse).getSocialLinksDetails();
		Mockito.doCallRealMethod().when(socialLinksUse).getSocialLinksSubtitle();
		Mockito.doCallRealMethod().when(socialLinksUse).setSocialLinksDetails(mockList);
		Mockito.doCallRealMethod().when(socialLinksUse).setSocialLinksSubtitle(socialLinksSubtitle);
	}
	
	@Test
	public void testActivate() throws Exception{
		socialLinksUse.setSocialLinksDetails(mockList);
		when(socialLinksUse.getProperties()).thenReturn(map1);
		when(map1.get(HertzConstants.SOCIAL_LINKS_SUBTITLE, String.class)).thenReturn(socialLinksSubtitle);
		socialLinksUse.setSocialLinksSubtitle(socialLinksSubtitle);
		when(socialLinksUse.getResource()).thenReturn(resource1);
		when(resource1.getChild("socialLinks")).thenReturn(resource2);
		when(resource2.getChildren()).thenReturn(mockIterable);
		when(mockIterable.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true, false);//64
		when(mockIterator.next()).thenReturn(resource3);
		when(resource3.getValueMap()).thenReturn(map2);
		when(map2.containsKey(Mockito.anyString())).thenReturn(true);
		when(map2.get("socialSite",String.class)).thenReturn("Social Site");
		when(map2.get("socialIconImageReference",String.class)).thenReturn("/content/hertz/us/en/abc.jpg");
		when(map2.get("openIconNewWindow",Boolean.class)).thenReturn(true);
		when(map2.get("socialURL",String.class)).thenReturn("https://www.hertz.com");
		when(map2.get("altText",String.class)).thenReturn("Image not available");
		socialLinksUse.activate();
		Assert.assertNotNull(socialLinksUse.getSocialLinksSubtitle());
		Assert.assertNotNull(socialLinksUse.getSocialLinksDetails());
	}
	
}
