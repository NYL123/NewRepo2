package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestSocialLinksContentBean {
	
	@InjectMocks
	private SocialLinksContentBean socialLinksContentBean;
	
	@Mock
	SocialLinkBean socialLinkBean;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		socialLinksContentBean=Mockito.mock(SocialLinksContentBean.class);
		List<SocialLinkBean> mockList=new ArrayList<SocialLinkBean>();
		mockList.add(socialLinkBean);
		Mockito.doCallRealMethod().when(socialLinksContentBean).setSocialLinkBeanList(mockList);
		Mockito.doCallRealMethod().when(socialLinksContentBean).setSocialLinksSubtitle(Mockito.anyString());
		Mockito.doCallRealMethod().when(socialLinksContentBean).getSocialLinkBeanList();
		Mockito.doCallRealMethod().when(socialLinksContentBean).getSocialLinksSubtitle();
		socialLinksContentBean.setSocialLinkBeanList(mockList);
		socialLinksContentBean.setSocialLinksSubtitle("Social Links");
	}
	
	@Test
	public final  void test(){
		Assert.assertNotNull(socialLinksContentBean.getSocialLinksSubtitle());
		Assert.assertNotNull(socialLinksContentBean.getSocialLinkBeanList());
	}
	
}
