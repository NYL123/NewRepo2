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
public class TestPasswordGrantBean {

	@InjectMocks
	private PasswordGrantBean passwordGrantBean;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		passwordGrantBean = PowerMockito.mock(PasswordGrantBean.class);
		Mockito.doCallRealMethod().when(passwordGrantBean).setAccessToken(Mockito.anyString());
		Mockito.doCallRealMethod().when(passwordGrantBean).setExpiresIn(Mockito.anyInt());
		Mockito.doCallRealMethod().when(passwordGrantBean).setJti(Mockito.anyString());
		Mockito.doCallRealMethod().when(passwordGrantBean).setScope(Mockito.anyString());
		Mockito.doCallRealMethod().when(passwordGrantBean).setTokenType(Mockito.anyString());

		Mockito.doCallRealMethod().when(passwordGrantBean).getAccessToken();
		Mockito.doCallRealMethod().when(passwordGrantBean).getExpiresIn();
		Mockito.doCallRealMethod().when(passwordGrantBean).getJti();
		Mockito.doCallRealMethod().when(passwordGrantBean).getScope();
		Mockito.doCallRealMethod().when(passwordGrantBean).getTokenType();

		passwordGrantBean.setAccessToken("access-token");
		passwordGrantBean.setExpiresIn(5);
		passwordGrantBean.setJti("jti");
		passwordGrantBean.setScope("scope");
		passwordGrantBean.setTokenType("token-type");

	}

	@Test
	public void test() {
		Assert.assertTrue(passwordGrantBean.getAccessToken().equalsIgnoreCase("access-token"));
		Assert.assertTrue(passwordGrantBean.getExpiresIn().equals(5));
		Assert.assertTrue(passwordGrantBean.getJti().equalsIgnoreCase("jti"));
		Assert.assertTrue(passwordGrantBean.getScope().equalsIgnoreCase("scope"));
		Assert.assertTrue(passwordGrantBean.getTokenType().equalsIgnoreCase("token-type"));

	}

}
