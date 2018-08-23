package com.hertz.digital.platform.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class TestSPATemplateBean {
	
	private SPATemplateBean spaTemplateBean;

	@Before
	public final void setup() throws Exception {

		spaTemplateBean=new SPATemplateBean();
		
	}
	@Test
	public final void test(){
		
		HtmlFragment htmlFragments = new HtmlFragment();
		htmlFragments.setHtml("Test");
		htmlFragments.setName("Name");
		spaTemplateBean.setHtmlFragments(htmlFragments);
		Assert.assertNotNull(htmlFragments.getName());
		Assert.assertNotNull(htmlFragments.getHtml());
		Assert.assertNotNull(spaTemplateBean.getHtmlFragments());
	}
}
