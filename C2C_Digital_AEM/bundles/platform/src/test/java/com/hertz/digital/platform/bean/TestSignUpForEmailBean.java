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
public class TestSignUpForEmailBean {

	@InjectMocks
	private SignUpForEmailBean signUpForEmailBean;
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		signUpForEmailBean=Mockito.mock(SignUpForEmailBean.class);
		Mockito.doCallRealMethod().when(signUpForEmailBean).setOpenUrlNewWindow(Mockito.anyBoolean());
		Mockito.doCallRealMethod().when(signUpForEmailBean).setSignupForEmailButtonText(Mockito.anyString());
		Mockito.doCallRealMethod().when(signUpForEmailBean).setSignupForEmailPlaceholderText(Mockito.anyString());
		Mockito.doCallRealMethod().when(signUpForEmailBean).setSignupForEmailSubtitle(Mockito.anyString());
		Mockito.doCallRealMethod().when(signUpForEmailBean).setSignupForEmailTitle(Mockito.anyString());
		Mockito.doCallRealMethod().when(signUpForEmailBean).setTargetURL(Mockito.anyString());
		Mockito.doCallRealMethod().when(signUpForEmailBean).getOpenUrlNewWindow();
		Mockito.doCallRealMethod().when(signUpForEmailBean).getSignupForEmailButtonText();
		Mockito.doCallRealMethod().when(signUpForEmailBean).getSignupForEmailPlaceholderText();
		Mockito.doCallRealMethod().when(signUpForEmailBean).getSignupForEmailSubtitle();
		Mockito.doCallRealMethod().when(signUpForEmailBean).getSignupForEmailTitle();
		Mockito.doCallRealMethod().when(signUpForEmailBean).getTargetURL();
		Mockito.doCallRealMethod().when(signUpForEmailBean).getSeoNoFollow();
		Mockito.doCallRealMethod().when(signUpForEmailBean).setSeoNoFollow(true);
		signUpForEmailBean.setOpenUrlNewWindow(true);
		signUpForEmailBean.setSeoNoFollow(true);
		signUpForEmailBean.setSignupForEmailButtonText("Sign up");
		signUpForEmailBean.setSignupForEmailPlaceholderText("Enter you email here");
		signUpForEmailBean.setSignupForEmailSubtitle("For offers, please sign up");
		signUpForEmailBean.setSignupForEmailTitle("Sign up for Email");
		signUpForEmailBean.setTargetURL("https://www.hertz.com");
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(signUpForEmailBean.getSignupForEmailButtonText());
		Assert.assertNotNull(signUpForEmailBean.getSignupForEmailPlaceholderText());
		Assert.assertNotNull(signUpForEmailBean.getSignupForEmailSubtitle());
		Assert.assertNotNull(signUpForEmailBean.getSignupForEmailTitle());
		Assert.assertNotNull(signUpForEmailBean.getTargetURL());
		Assert.assertTrue(signUpForEmailBean.getOpenUrlNewWindow());
		Assert.assertTrue(signUpForEmailBean.getSeoNoFollow());
	}
}
