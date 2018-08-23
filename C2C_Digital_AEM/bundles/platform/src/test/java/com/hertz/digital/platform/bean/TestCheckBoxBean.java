package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

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
public class TestCheckBoxBean {


	@InjectMocks
	private CheckboxBean checkboxesBean= new CheckboxBean();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		checkboxesBean=Mockito.mock(CheckboxBean.class);
		Mockito.doCallRealMethod().when(checkboxesBean).setElementGroup("elementGroup");
		Mockito.doCallRealMethod().when(checkboxesBean).setElement("element");
		Mockito.doCallRealMethod().when(checkboxesBean).setAriaLabel("ariaLabel");
		Mockito.doCallRealMethod().when(checkboxesBean).setOrder("order");
		Mockito.doCallRealMethod().when(checkboxesBean).setText("Text");
		Mockito.doCallRealMethod().when(checkboxesBean).setDefaultSelection("true");
		List<CheckBoxesLinkBean> links = new  ArrayList<>();
		Mockito.doCallRealMethod().when(checkboxesBean).setLinks(links);
		Mockito.doCallRealMethod().when(checkboxesBean).getElementGroup();
		Mockito.doCallRealMethod().when(checkboxesBean).getElement();
		Mockito.doCallRealMethod().when(checkboxesBean).getAriaLabel();
		Mockito.doCallRealMethod().when(checkboxesBean).getDefaultSelection();
		Mockito.doCallRealMethod().when(checkboxesBean).getLinks();
		Mockito.doCallRealMethod().when(checkboxesBean).getOrder();
		Mockito.doCallRealMethod().when(checkboxesBean).getText();
		checkboxesBean.setElementGroup("elementGroup");
		checkboxesBean.setElement("element");
		checkboxesBean.setOrder("order");
		checkboxesBean.setAriaLabel("ariaLabel");
		checkboxesBean.setText("Text");
		checkboxesBean.setDefaultSelection("true");
		checkboxesBean.setLinks(links);
		
	}
	
	@Test
	public final void test(){
		Assert.assertNotNull(checkboxesBean.getElementGroup());
		Assert.assertNotNull(checkboxesBean.getElement());
		Assert.assertNotNull(checkboxesBean.getDefaultSelection());
		Assert.assertNotNull(checkboxesBean.getAriaLabel());
		Assert.assertNotNull(checkboxesBean.getLinks());
		Assert.assertNotNull(checkboxesBean.getOrder());
		Assert.assertNotNull(checkboxesBean.getText());
	}
	
	
	


}
