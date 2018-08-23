package com.hertz.digital.platform.bean;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestSocialLinkBean {

	@InjectMocks
	private SocialLinkBean socialLinkBean;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		Map<String, String> renditionMap=new HashMap<String,String>();
		renditionMap.put("renditionName", "renditionPath");
		socialLinkBean=Mockito.mock(SocialLinkBean.class);
		Mockito.doCallRealMethod().when(socialLinkBean).setAltText(Mockito.anyString());
		Mockito.doCallRealMethod().when(socialLinkBean).setCtaAction(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(socialLinkBean).setSocialImage(Mockito.anyString());
		Mockito.doCallRealMethod().when(socialLinkBean).setSocialSite(Mockito.anyString());
		Mockito.doCallRealMethod().when(socialLinkBean).setSocialURL(Mockito.anyString());
		Mockito.doCallRealMethod().when(socialLinkBean).setRenditionMap(Mockito.anyMap());
		Mockito.doCallRealMethod().when(socialLinkBean).setSeoNoFollow(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(socialLinkBean).setCssClass(Mockito.anyString());
		Mockito.doCallRealMethod().when(socialLinkBean).getAltText();
		Mockito.doCallRealMethod().when(socialLinkBean).getCtaAction();
		Mockito.doCallRealMethod().when(socialLinkBean).getSocialImage();
		Mockito.doCallRealMethod().when(socialLinkBean).getSocialSite();
		Mockito.doCallRealMethod().when(socialLinkBean).getSocialURL();
		Mockito.doCallRealMethod().when(socialLinkBean).getRenditionMap();
		Mockito.doCallRealMethod().when(socialLinkBean).getSeoNoFollow();
		Mockito.doCallRealMethod().when(socialLinkBean).getCssClass();
		socialLinkBean.setAltText("Image Not Available");
		socialLinkBean.setCtaAction(true);
		socialLinkBean.setSocialImage("/content/dam/hertz/rac.jpg");
		socialLinkBean.setSocialSite("Hertz");
		socialLinkBean.setSocialURL("https://www.hertz.com");
		socialLinkBean.setRenditionMap(renditionMap);
		socialLinkBean.setSeoNoFollow(true);
		socialLinkBean.setCssClass("css");
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(socialLinkBean.getAltText());
		Assert.assertNotNull(socialLinkBean.getSocialImage());
		Assert.assertNotNull(socialLinkBean.getSocialSite());
		Assert.assertNotNull(socialLinkBean.getSocialURL());
		Assert.assertTrue(socialLinkBean.getCtaAction());
		Assert.assertNotNull(socialLinkBean.getRenditionMap());
		Assert.assertNotNull(socialLinkBean.getSeoNoFollow());
		Assert.assertNotNull(socialLinkBean.getCssClass());
	}
}
