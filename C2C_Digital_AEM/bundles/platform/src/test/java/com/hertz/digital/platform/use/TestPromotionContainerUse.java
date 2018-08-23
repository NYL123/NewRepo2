package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.bean.MenuItemBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.MenuBuilderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WCMUsePojo.class, LoggerFactory.class})
public class TestPromotionContainerUse {
	
	@InjectMocks
	private PromotionContainerUse promotionContainerUse;
	private static final String PROMO_CARD_NUMBER = "promocardnumber";
	
	
	Logger log;
	
	@Mock
	ValueMap  valuemap;  
	
	@Mock
	Integer  promoCardInteger= new Integer(2);
	ArrayList<String> promoCardArrayList = new ArrayList<String>(); 
	
	
	
	
	@Before
	public final void setup() throws Exception{
		promotionContainerUse= PowerMockito.mock(PromotionContainerUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log=Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		Mockito.doCallRealMethod().when(promotionContainerUse).activate();
		Mockito.doCallRealMethod().when(promotionContainerUse).getPromoCardArrayList();
		Mockito.doCallRealMethod().when(promotionContainerUse).setPromoCardArrayList(promoCardArrayList);;

	}
	
	@Test
	public final void testActivate() throws Exception{
		promotionContainerUse.setPromoCardArrayList(promoCardArrayList);
		when(promotionContainerUse.getProperties()).thenReturn(valuemap);
		when(valuemap.get(PROMO_CARD_NUMBER, Integer.class)).thenReturn(promoCardInteger);
		promotionContainerUse.activate();
		Assert.assertNotNull(promotionContainerUse.getPromoCardArrayList());		
	}
}
