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
public class TestRQRSectionsBean {

	@InjectMocks
	private RQRSectionsBean RQRBean;

	@Mock
	private List<RQRLinksBean> rQRLinksBeanList;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		RQRBean = PowerMockito.mock(RQRSectionsBean.class);
		Mockito.doCallRealMethod().when(RQRBean).setSectionName(Mockito.anyString());
		Mockito.doCallRealMethod().when(RQRBean).setRQRLinksBeanList(rQRLinksBeanList);

		Mockito.doCallRealMethod().when(RQRBean).getSectionName();
		Mockito.doCallRealMethod().when(RQRBean).getRQRLinksBeanList();

		RQRBean.setSectionName("RQR");
		RQRBean.setRQRLinksBeanList(rQRLinksBeanList);
	}

	@Test
	public void test() {
		Assert.assertTrue(RQRBean.getSectionName().equalsIgnoreCase("RQR"));
		Assert.assertNotNull(RQRBean.getRQRLinksBeanList());
	}

}
