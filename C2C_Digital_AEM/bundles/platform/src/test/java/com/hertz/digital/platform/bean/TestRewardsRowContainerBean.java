package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.commons.json.JSONException;
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
public class TestRewardsRowContainerBean {

	@InjectMocks
	private RewardsRowContainerBean rewardsRowContainerBean;
	
	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		rewardsRowContainerBean = Mockito.mock(RewardsRowContainerBean.class);

		Mockito.doCallRealMethod().when(rewardsRowContainerBean).setIdentifier(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).setAnchorid(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).setResourceType(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).setHeading(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).setColumnName(Mockito.any(List.class));
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).setColumnHeader(Mockito.any(List.class), Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).setTableParsysName(Mockito.anyString());
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).setRewardsRowList(Mockito.any(List.class));
		
		
		
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).getIdentifier();
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).getAnchorid();
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).getResourceType();
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).getHeading();
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).getColumnName();
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).getColumnHeader();
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).getTableParsysName();
		Mockito.doCallRealMethod().when(rewardsRowContainerBean).getRewardsRowList();

		
	}

	@Test
	public final void test() throws JSONException {
		List<String> mockList = new ArrayList<>();
		String columnHeader = "string1";
		mockList.add(columnHeader);
		rewardsRowContainerBean.setIdentifier("string");
		rewardsRowContainerBean.setAnchorid("string");
		rewardsRowContainerBean.setResourceType("string");
		rewardsRowContainerBean.setHeading("string");
		rewardsRowContainerBean.setColumnName(new ArrayList<>());
		rewardsRowContainerBean.setColumnHeader(mockList, "string");
		rewardsRowContainerBean.setTableParsysName("string");
		rewardsRowContainerBean.setRewardsRowList(new ArrayList<>());
		Assert.assertNotNull(rewardsRowContainerBean.getIdentifier());
		Assert.assertNotNull(rewardsRowContainerBean.getAnchorid());
		Assert.assertNotNull(rewardsRowContainerBean.getResourceType());
		Assert.assertNotNull(rewardsRowContainerBean.getHeading());
		Assert.assertNotNull(rewardsRowContainerBean.getColumnName());
		Assert.assertNotNull(rewardsRowContainerBean.getColumnHeader());
		Assert.assertNotNull(rewardsRowContainerBean.getTableParsysName());
		Assert.assertNotNull(rewardsRowContainerBean.getRewardsRowList());
	}
}
