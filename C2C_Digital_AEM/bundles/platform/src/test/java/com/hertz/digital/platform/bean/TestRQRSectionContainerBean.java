package com.hertz.digital.platform.bean;

import java.util.List;

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
public class TestRQRSectionContainerBean {

	@InjectMocks
	private RQRSectionContainerBean RQRContainerBean;

	@Mock
	private List<RQRSectionsBean> rQRSectionsBeanList;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		RQRContainerBean = PowerMockito.mock(RQRSectionContainerBean.class);
		Mockito.doCallRealMethod().when(RQRContainerBean).setRQRSectionsBeanList(rQRSectionsBeanList);
		Mockito.doCallRealMethod().when(RQRContainerBean).getRQRSectionsBeanList();
		RQRContainerBean.setRQRSectionsBeanList(rQRSectionsBeanList);
	}

	@Test
	public void test() {
		Assert.assertNotNull(RQRContainerBean.getRQRSectionsBeanList());
	}

}
