package com.hertz.digital.platform.bean;

import org.junit.Assert;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestPartnerDetailsBean {

	@InjectMocks
	private PartnerDetailsBean bean;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		bean = PowerMockito.mock(PartnerDetailsBean.class);
		Mockito.doCallRealMethod().when(bean).setPartnerName("name");
		Mockito.doCallRealMethod().when(bean).getPartnerName();
		Mockito.doCallRealMethod().when(bean).setPartner("name");
		Mockito.doCallRealMethod().when(bean).getPartner();
		bean.setPartnerName("name");
		bean.setPartner("name");

	}

	@Test
	public void test() {

		Assert.assertNotNull(bean.getPartnerName());
		Assert.assertNotNull(bean.getPartner());
	}
}
