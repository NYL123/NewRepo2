package com.hertz.digital.platform.bean;

import java.util.List;
import java.util.Map;

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
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestHeroImageBean {

	@InjectMocks
	private HeroImageBean heroImageBean;
	
	@Mock
	ImageInfoBean imageInfo;
	
	@Mock
	List<Map<String, Object>> mockList;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		heroImageBean=PowerMockito.mock(HeroImageBean.class);
		Mockito.doCallRealMethod().when(heroImageBean).setAltText(Mockito.anyString());
		Mockito.doCallRealMethod().when(heroImageBean).setImagePath(Mockito.anyString());
		Mockito.doCallRealMethod().when(heroImageBean).setTagLineText(Mockito.anyString());
		Mockito.doCallRealMethod().when(heroImageBean).setSubTagLineText(Mockito.anyString());
		Mockito.doCallRealMethod().when(heroImageBean).setImageInfo(imageInfo);
		Mockito.doCallRealMethod().when(heroImageBean).setSources(mockList);
		Mockito.doCallRealMethod().when(heroImageBean).getAltText();
		Mockito.doCallRealMethod().when(heroImageBean).getImagePath();
		Mockito.doCallRealMethod().when(heroImageBean).getTagLineText();
		Mockito.doCallRealMethod().when(heroImageBean).getImageInfo();
		Mockito.doCallRealMethod().when(heroImageBean).getSources();
		Mockito.doCallRealMethod().when(heroImageBean).getSubTagLineText();
		heroImageBean.setAltText("altText");
		heroImageBean.setImagePath("imagePath");
		heroImageBean.setTagLineText("tagLineText");
		heroImageBean.setImageInfo(imageInfo);
		heroImageBean.setSources(mockList);
		heroImageBean.setSubTagLineText("subTagLineText");
	}
	
	
	@Test
	public void test(){
		Assert.assertTrue(heroImageBean.getAltText().equalsIgnoreCase("alttext"));
		Assert.assertTrue(heroImageBean.getImagePath().equalsIgnoreCase("imagePath"));
		Assert.assertTrue(heroImageBean.getTagLineText().equalsIgnoreCase("tagLineText"));
		Assert.assertTrue(heroImageBean.getSubTagLineText().equalsIgnoreCase("subTagLineText"));
		Assert.assertNotNull(heroImageBean.getImageInfo());
		Assert.assertNotNull(heroImageBean.getSources());
	}
}
