package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestRewardsRowBean {

	@InjectMocks
	private RewardsRowBean rewardsRowBean;
	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		rewardsRowBean = Mockito.mock(RewardsRowBean.class);

		Mockito.doCallRealMethod().when(rewardsRowBean).setIdentifier(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowBean).setResourceType(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowBean).setLinkPath(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowBean).setLinkTitle(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowBean).setCssIdentifier(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowBean).setCity(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowBean).setEndGroup(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowBean).setStartDateText(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowBean).setEndDateText(Mockito.anyString());
		
		
		Mockito.doCallRealMethod().when(rewardsRowBean).getIdentifier();
		Mockito.doCallRealMethod().when(rewardsRowBean).getResourceType();
		Mockito.doCallRealMethod().when(rewardsRowBean).getLinkPath();
		Mockito.doCallRealMethod().when(rewardsRowBean).getLinkTitle();
		Mockito.doCallRealMethod().when(rewardsRowBean).getCssIdentifier();
		Mockito.doCallRealMethod().when(rewardsRowBean).getCity();
		Mockito.doCallRealMethod().when(rewardsRowBean).getEndGroup();
		Mockito.doCallRealMethod().when(rewardsRowBean).getStartDateText();
		Mockito.doCallRealMethod().when(rewardsRowBean).getEndDateText();

		
	}

	@Test
	public final void test() {
		rewardsRowBean.setIdentifier("identifier");
		rewardsRowBean.setResourceType("resourceType");
		rewardsRowBean.setLinkPath("linkPath");
		rewardsRowBean.setLinkTitle("linktitle");
		rewardsRowBean.setCssIdentifier("css");
		rewardsRowBean.setCity("city");
		rewardsRowBean.setEndGroup("endgroup");
		rewardsRowBean.setStartDateText("stratdate");
		rewardsRowBean.setEndDateText("enddate");
		Assert.assertNotNull(rewardsRowBean.getIdentifier());
		Assert.assertNotNull(rewardsRowBean.getResourceType());
		Assert.assertNotNull(rewardsRowBean.getLinkPath());
		Assert.assertNotNull(rewardsRowBean.getLinkTitle());
		Assert.assertNotNull(rewardsRowBean.getCssIdentifier());
		Assert.assertNotNull(rewardsRowBean.getCity());
		Assert.assertNotNull(rewardsRowBean.getEndGroup());
		Assert.assertNotNull(rewardsRowBean.getStartDateText());
		Assert.assertNotNull(rewardsRowBean.getEndDateText());
	}
}
