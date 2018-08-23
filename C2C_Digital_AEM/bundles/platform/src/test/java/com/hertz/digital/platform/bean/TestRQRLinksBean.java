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
public class TestRQRLinksBean {

	@InjectMocks
	private RQRLinksBean RQRLinksBeanObj;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		RQRLinksBeanObj = PowerMockito.mock(RQRLinksBean.class);
		Mockito.doCallRealMethod().when(RQRLinksBeanObj).setLinkAltText(Mockito.anyString());
		Mockito.doCallRealMethod().when(RQRLinksBeanObj).setLinkName(Mockito.anyString());
		Mockito.doCallRealMethod().when(RQRLinksBeanObj).setLinkURL(Mockito.anyString());
		Mockito.doCallRealMethod().when(RQRLinksBeanObj).getLinkAltText();
		Mockito.doCallRealMethod().when(RQRLinksBeanObj).getLinkName();
		Mockito.doCallRealMethod().when(RQRLinksBeanObj).getLinkURL();

		RQRLinksBeanObj.setLinkAltText("href");
		RQRLinksBeanObj.setLinkName("linktext");
		RQRLinksBeanObj.setLinkURL("linkURL");
	}

	@Test
	public void test() {
		Assert.assertTrue(RQRLinksBeanObj.getLinkAltText().equalsIgnoreCase("href"));
		Assert.assertTrue(RQRLinksBeanObj.getLinkName().equalsIgnoreCase("linktext"));
		Assert.assertTrue(RQRLinksBeanObj.getLinkURL().equalsIgnoreCase("linkURL"));
	}

}
