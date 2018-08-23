package com.hertz.digital.platform.bean;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestImageCompoentBean {
	
	@Test
	public void test(){
		ImageComponentBean imageBean= new ImageComponentBean();
		imageBean.setAltText("test");
		//imageBean.setSources("sources");
		List<Map<String, Object>> sources = new LinkedList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key ", new ImageComponentBean());
		sources.add(map);
		imageBean.setSources(sources);
		
		Assert.assertTrue(imageBean.getAltText().equalsIgnoreCase("test"));
		Assert.assertNotNull(imageBean.getSources());
		
	}

}
