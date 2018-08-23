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
public class TestCheckboxLinkBean {

	@InjectMocks
	private CheckBoxesLinkBean checkboxesBean= new CheckBoxesLinkBean();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		checkboxesBean=Mockito.mock(CheckBoxesLinkBean.class);
		Mockito.doCallRealMethod().when(checkboxesBean).setElementGroup("elementGroup");
		Mockito.doCallRealMethod().when(checkboxesBean).setElement("element");
		Mockito.doCallRealMethod().when(checkboxesBean).setLinkText("linkText");
		Mockito.doCallRealMethod().when(checkboxesBean).setAriaLabel("ariaLabel");
		Mockito.doCallRealMethod().when(checkboxesBean).setTargetType("targetType");
		Mockito.doCallRealMethod().when(checkboxesBean).setTargetURL("targetUrl");
		Mockito.doCallRealMethod().when(checkboxesBean).getElementGroup();
		Mockito.doCallRealMethod().when(checkboxesBean).getElement();
		Mockito.doCallRealMethod().when(checkboxesBean).getLinkText();
		Mockito.doCallRealMethod().when(checkboxesBean).getAriaLabel();
		Mockito.doCallRealMethod().when(checkboxesBean).getTargetType();
		Mockito.doCallRealMethod().when(checkboxesBean).getTargetURL();
		checkboxesBean.setElementGroup("elementGroup");
		checkboxesBean.setElement("element");
		checkboxesBean.setLinkText("linkText");
		checkboxesBean.setAriaLabel("ariaLabel");
		checkboxesBean.setTargetType("targetType");
		checkboxesBean.setTargetURL("targetUrl");
		
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(checkboxesBean.getElementGroup());
		Assert.assertNotNull(checkboxesBean.getElement());
		Assert.assertNotNull(checkboxesBean.getLinkText());
		Assert.assertNotNull(checkboxesBean.getAriaLabel());
		Assert.assertNotNull(checkboxesBean.getTargetType());
		Assert.assertNotNull(checkboxesBean.getTargetURL());
	}
	
	
	
}
