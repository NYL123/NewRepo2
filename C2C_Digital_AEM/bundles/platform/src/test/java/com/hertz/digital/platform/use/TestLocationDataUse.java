package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.jcr.Node;
import javax.jcr.Property;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.constants.HertzConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestLocationDataUse {
	
	@InjectMocks
	private LocationDataUse dataUse ; 
	Logger log;
	
	private static final String HOURS_OF_OPERATION_1 = "hoursOfOperation1";
	private static final String HOURS_OF_OPERATION_2 = "hoursOfOperation2";
	private static final String HOURS_OF_OPERATION_3 = "hoursOfOperation3";
	
	@Mock
	Page page;
	
	@Mock
	Node locatonDataNode;
	
	@Mock
	Node locatonDataJcrNode;
	
	@Mock
	Property hoursOfOperation1Property;
	
	@Mock
	Property hoursOfOperation2Property;
	
	@Mock
	Property hoursOfOperation3Property;
	
	@Mock
	 Node cityNode;
	
	@Mock
	 Node cityJCRNode ;
	@Mock
	Property cityTitleProperty;
	
	@Mock
	Node stateNode;
	
	@Mock
	Node countryNode ;
	
	@Mock
	Node regionNode;
	
	@Mock
	Node languageNode;
	
	@Before
	public final void setup() throws Exception {
		dataUse =PowerMockito.mock(LocationDataUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(dataUse).activate();
		Mockito.doCallRealMethod().when(dataUse).getJsonString();
		Mockito.doCallRealMethod().when(dataUse).setJsonString("Test");
	}
	
	@Test
	public final void testActivate() throws Exception {
		when(dataUse.getCurrentPage()).thenReturn(page);
		Mockito.doCallRealMethod().when(dataUse).prepareLocationJson(page);
		when(page.adaptTo(Node.class)).thenReturn(locatonDataNode);
		when(locatonDataNode.getNode(HertzConstants.JCR_CONTENT)).thenReturn(locatonDataJcrNode);
		when(locatonDataJcrNode.hasProperty(HOURS_OF_OPERATION_1)).thenReturn(true);
		when(locatonDataJcrNode.getProperty(HOURS_OF_OPERATION_1)).thenReturn(hoursOfOperation1Property);
		when(hoursOfOperation1Property.getString()).thenReturn("Test");

		when(locatonDataJcrNode.hasProperty(HOURS_OF_OPERATION_2)).thenReturn(true);
		when(locatonDataJcrNode.getProperty(HOURS_OF_OPERATION_2)).thenReturn(hoursOfOperation2Property);
		when(hoursOfOperation2Property.getString()).thenReturn("Test");

		when(locatonDataJcrNode.hasProperty(HOURS_OF_OPERATION_3)).thenReturn(true);
		when(locatonDataJcrNode.getProperty(HOURS_OF_OPERATION_3)).thenReturn(hoursOfOperation3Property);
		when(hoursOfOperation3Property.getString()).thenReturn("Test");
		when(locatonDataNode.getParent()).thenReturn(cityNode);
		when(cityNode.getNode(HertzConstants.JCR_CONTENT)).thenReturn(cityJCRNode);
		when(cityJCRNode.hasProperty(HertzConstants.JCR_TITLE_PROPERTY)).thenReturn(true);
		when(cityJCRNode.getProperty(HertzConstants.JCR_TITLE_PROPERTY)).thenReturn(cityTitleProperty);
		when(cityTitleProperty.getString()).thenReturn("Test");
		when(cityNode.getParent()).thenReturn(stateNode);
		when(stateNode.getName()).thenReturn("Test");
		
		when(stateNode.getParent()).thenReturn(countryNode);
		when(countryNode.getName()).thenReturn("Test");
		
		when(countryNode.getParent()).thenReturn(regionNode);
		when(regionNode.getName()).thenReturn("Test");
		
		when(regionNode.getParent()).thenReturn(languageNode);
		when(languageNode.getName()).thenReturn("Test");
		
		dataUse.setJsonString("Test");
		dataUse.activate();	
		Assert.assertNotNull(dataUse.getJsonString());
	}

}
