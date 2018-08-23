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
public class TestSecretsBean {

	@InjectMocks
	private SecretsBean secretsBean;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		secretsBean = PowerMockito.mock(SecretsBean.class);
		Mockito.doCallRealMethod().when(secretsBean).setKey(Mockito.anyString());
		Mockito.doCallRealMethod().when(secretsBean).setValue(Mockito.anyString());

		Mockito.doCallRealMethod().when(secretsBean).getKey();
		Mockito.doCallRealMethod().when(secretsBean).getValue();

		secretsBean.setKey("Key");
		secretsBean.setValue("Value");
	}

	@Test
	public void test() {
		Assert.assertTrue(secretsBean.getKey().equalsIgnoreCase("Key"));
		Assert.assertNotNull(secretsBean.getValue());
	}

}
