package com.hertz.digital.platform.bean;

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
public class TestRQRWrapperBean {

	@InjectMocks
	private RQRWrapperBean RQRWrapperBeanObj;

	@Mock
	private RQRSectionContainerBean RQRSectionContainerBeanObj;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		RQRWrapperBeanObj = PowerMockito.mock(RQRWrapperBean.class);
		Mockito.doCallRealMethod().when(RQRWrapperBeanObj).setRQRSectionContainerBean(RQRSectionContainerBeanObj);
		Mockito.doCallRealMethod().when(RQRWrapperBeanObj).getRQRSectionContainerBean();
		RQRWrapperBeanObj.setRQRSectionContainerBean(RQRSectionContainerBeanObj);
	}

	@Test
	public void test() {
		Assert.assertNotNull(RQRWrapperBeanObj.getRQRSectionContainerBean());
	}

}
