package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class TestRewardsDatum {

	@InjectMocks
	private RewardsDatum reward;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		reward = PowerMockito.mock(RewardsDatum.class);
		Mockito.doCallRealMethod().when(reward).setTitle(Mockito.anyString());
		Mockito.doCallRealMethod().when(reward).setDescription(Mockito.anyString());
		Mockito.doCallRealMethod().when(reward).setPoints(Mockito.anyString());
		Mockito.doCallRealMethod().when(reward).setPointsText(Mockito.anyString());
		Mockito.doCallRealMethod().when(reward).getTitle();
		Mockito.doCallRealMethod().when(reward).getDescription();
		Mockito.doCallRealMethod().when(reward).getPoints();
		Mockito.doCallRealMethod().when(reward).getPointsText();
		reward.setTitle("tile");
		reward.setDescription("description");
		reward.setPointsText("points text");
		reward.setPoints("points");
	}

	@Test
	public final void test() {
		Assert.assertNotNull(reward.getTitle());
		Assert.assertNotNull(reward.getDescription());
		Assert.assertNotNull(reward.getPoints());
		Assert.assertNotNull(reward.getPointsText());
	}
}
