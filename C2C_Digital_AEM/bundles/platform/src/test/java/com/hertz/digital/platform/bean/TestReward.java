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
public class TestReward {

	@InjectMocks
	private Reward reward;

	@Mock
	List<RewardsDatum> mockList;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		reward = PowerMockito.mock(Reward.class);
		Mockito.doCallRealMethod().when(reward).setTitle(Mockito.anyString());
		Mockito.doCallRealMethod().when(reward).setRewardsData(mockList);
		Mockito.doCallRealMethod().when(reward).getRewardsData();
		Mockito.doCallRealMethod().when(reward).getTitle();
		reward.setTitle("tile");
		reward.setRewardsData(mockList);
	}

	@Test
	public final void test() {
		Assert.assertNotNull(reward.getTitle());
		Assert.assertNotNull(reward.getRewardsData());
	}
}
