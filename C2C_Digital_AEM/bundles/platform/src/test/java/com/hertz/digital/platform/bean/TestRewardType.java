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
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class TestRewardType {

	@InjectMocks
	private RewardType reward;

	@Mock
	List<Reward> mockList;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		reward = PowerMockito.mock(RewardType.class);
		Mockito.doCallRealMethod().when(reward).setRewardType(Mockito.anyString());
		Mockito.doCallRealMethod().when(reward).setRewardDescription(Mockito.anyString());
		Mockito.doCallRealMethod().when(reward).setRewards(mockList);
		Mockito.doCallRealMethod().when(reward).getRewardDescription();
		Mockito.doCallRealMethod().when(reward).getRewardType();
		Mockito.doCallRealMethod().when(reward).getRewards();
		reward.setRewardType("tile");
		reward.setRewardDescription("description");
		reward.setRewards(mockList);
	}

	@Test
	public final void test() {
		Assert.assertNotNull(reward.getRewardType());
		Assert.assertNotNull(reward.getRewardDescription());
		Assert.assertNotNull(reward.getRewards());
	}
}
