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
public class TestChatBean {

	@InjectMocks
	private ChatBean chatBean;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		chatBean=PowerMockito.mock(ChatBean.class);
		Mockito.doCallRealMethod().when(chatBean).setChatAltText(Mockito.anyString());
		Mockito.doCallRealMethod().when(chatBean).getChatAltText();
		chatBean.setChatAltText("chatAltText");	
		
	}
	
	@Test
	public void test(){
		Assert.assertTrue(chatBean.getChatAltText().equalsIgnoreCase("chatAltText"));
	}
	
}
