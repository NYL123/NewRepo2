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
public class TestPartnerInfoBean {

	@InjectMocks
	private PartnerInfoBean partnerInfo;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		partnerInfo=PowerMockito.mock(PartnerInfoBean.class);
		Mockito.doCallRealMethod().when(partnerInfo).getPartnerName();
		Mockito.doCallRealMethod().when(partnerInfo).getCdpCode();
		Mockito.doCallRealMethod().when(partnerInfo).setCdpCode(Mockito.anyString());
		Mockito.doCallRealMethod().when(partnerInfo).setPartnerName(Mockito.anyString());
		partnerInfo.setCdpCode("cdpCode1");
		partnerInfo.setPartnerName("partner1");
	}
	
	@Test
	public void testGetterSetters(){
		Assert.assertTrue(partnerInfo.getCdpCode().equalsIgnoreCase("cdpCode1"));
		Assert.assertTrue(partnerInfo.getPartnerName().equalsIgnoreCase("partner1"));
	}
}
