package com.hertz.digital.platform.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestBadgeImageBean {
	
	private BadgeImageBean bean = new BadgeImageBean();
	
	@Before
	public void setUp() throws Exception{
	
		PowerMockito.mockStatic(LoggerFactory.class);
		MockitoAnnotations.initMocks(this);
		ImageInfoBean  imageInfoBean = Mockito.mock(ImageInfoBean.class);
		bean.setTierName("alt");
		bean.setBadge(imageInfoBean);
		bean.setKey("key");
	}
	
	@Test
	public void testGetterSetters(){
		Assert.assertNotNull(bean.getTierName());
		Assert.assertNotNull(bean.getKey());
		Assert.assertNotNull(bean.getBadge());
	}

}
