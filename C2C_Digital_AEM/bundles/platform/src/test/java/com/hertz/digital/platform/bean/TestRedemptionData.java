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
public class TestRedemptionData {

	@InjectMocks
	private RedemptionData redemption;

	@Mock
	List<RewardType> mockList;

	@Before
	public final void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		redemption = PowerMockito.mock(RedemptionData.class);
		Mockito.doCallRealMethod().when(redemption).setCountryName(Mockito.anyString());
		Mockito.doCallRealMethod().when(redemption).setRewardTypes(mockList);
		Mockito.doCallRealMethod().when(redemption).getCountryName();
		Mockito.doCallRealMethod().when(redemption).getRewardTypes();
		redemption.setCountryName("India");
		redemption.setRewardTypes(mockList);
	}

	@Test
	public final void test() {
		Assert.assertNotNull(redemption.getCountryName());
		Assert.assertNotNull(redemption.getRewardTypes());
	}
}