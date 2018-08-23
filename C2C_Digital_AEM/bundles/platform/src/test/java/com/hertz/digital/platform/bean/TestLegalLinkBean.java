/**
 * 
 */
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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

/**
 * @author a.dhingra
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestLegalLinkBean {
	@InjectMocks
	private LegalLinksBean linkBean;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		linkBean=Mockito.mock(LegalLinksBean.class);
		Mockito.doCallRealMethod().when(linkBean).setLegalLinkURL(Mockito.anyString());
		Mockito.doCallRealMethod().when(linkBean).setLegalLinkLabel(Mockito.anyString());
		Mockito.doCallRealMethod().when(linkBean).setOpenLegalLinkURLInNewWindow(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(linkBean).setSeoNoFollow(Mockito.anyBoolean());
		linkBean.setLegalLinkLabel("legalLinkLabel");
		linkBean.setLegalLinkURL("legalLinkURL");
		linkBean.setOpenLegalLinkURLInNewWindow(true);
		linkBean.setSeoNoFollow(true);
		Mockito.doCallRealMethod().when(linkBean).getLegalLinkLabel();
		Mockito.doCallRealMethod().when(linkBean).getLegalLinkURL();
		Mockito.doCallRealMethod().when(linkBean).getOpenLegalLinkURLInNewWindow();
		Mockito.doCallRealMethod().when(linkBean).getSeoNoFollow();
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(linkBean.getLegalLinkLabel());
		Assert.assertNotNull(linkBean.getLegalLinkURL());
		Assert.assertTrue(linkBean.getOpenLegalLinkURLInNewWindow());
		Assert.assertTrue(linkBean.getSeoNoFollow());
	}
	
}
