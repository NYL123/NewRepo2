package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestFooterLinkBean {

	@InjectMocks
	private FooterLinkBean footerLinkBean;
	
	@Mock
	FooterDetailBean footerDetailBean;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		footerLinkBean=Mockito.mock(FooterLinkBean.class);
		List<FooterDetailBean> footerDetailBeans=new ArrayList<FooterDetailBean>();
		footerDetailBeans.add(footerDetailBean);
		Mockito.doCallRealMethod().when(footerLinkBean).setFooterDetailBean(footerDetailBeans);
		Mockito.doCallRealMethod().when(footerLinkBean).setHeading(Mockito.anyString());
		Mockito.doCallRealMethod().when(footerLinkBean).getHeading();
		Mockito.doCallRealMethod().when(footerLinkBean).getFooterDetailBean();
		footerLinkBean.setFooterDetailBean(footerDetailBeans);
		footerLinkBean.setHeading("heading");
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(footerLinkBean.getFooterDetailBean());
		Assert.assertNotNull(footerLinkBean.getHeading());
	}
}
