package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
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

/**
 * @author himanshu.i.sharma
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestCheckboxesUse {

	@InjectMocks
	private CheckboxesUse checkboxesUse;
	
	private Logger log;
	
	@Mock
	Node node;
	
	@Mock
	
	Property checkboxesUseLinkProperty;
	
	@Mock
	Resource resource;
	
	@Mock
	Iterable<Resource> iterable;
	
	@Mock
	Iterator<Resource> iterator;
	
	@Mock
	Resource childPageResource;
	
	@Mock
	Map<String, Object> mockMap;
	
	Value[] values = new Value [2];
	
	@Mock
	Value checkboxesUseLinkPropertyValues ;
	
	private String links1 = "{'elementGroup':'LinkElementGroup','element':'LinkElement1','linkText':'LinkText1','ariaLabel':'LinkAriaLabel1','targetType':'TargetType1','targetURL':TargetURL1'}";
	private String links2 = "{'elementGroup':'LinkElementGroup','element':LinkElement2','linkText':'LinkText2','ariaLabel':'LinkAriaLabel2','targetType':'TargetType2','targetURL':'TargetURL2'}";
	
	@Before
	public final void setup() throws Exception {
		checkboxesUse=PowerMockito.mock(CheckboxesUse.class);
		PowerMockito.mockStatic(LoggerFactory.class);
		log = Mockito.mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
		MockitoAnnotations.initMocks(this);
		Mockito.doCallRealMethod().when(checkboxesUse).activate();
	}
	
	@Test
	public final void testActivate() throws Exception{
		when(checkboxesUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(node.getProperty(Mockito.anyString())).thenReturn(checkboxesUseLinkProperty);
		when(checkboxesUseLinkProperty.isMultiple()).thenReturn(true);
		when(checkboxesUseLinkProperty.getValues()).thenReturn(values);
		Value value1=PowerMockito.mock(Value.class);
		Value value2=PowerMockito.mock(Value.class);
		values[0]=value1;
		values[1]=value2;
		when(value1.toString()).thenReturn(links1);
		when(value2.toString()).thenReturn(links2);
		
		checkboxesUse.activate();
	}
	
	@Test
	public final void testActivateForSingleUse() throws Exception{
		when(checkboxesUse.getResource()).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(node.getProperty(Mockito.anyString())).thenReturn(checkboxesUseLinkProperty);
		when(checkboxesUseLinkProperty.isMultiple()).thenReturn(false);
		when(checkboxesUseLinkProperty.getValue()).thenReturn(checkboxesUseLinkPropertyValues);
/*		Value value1=PowerMockito.mock(Value.class);
		Value value2=PowerMockito.mock(Value.class);
		values[0]=value1;
		values[1]=value2;*/
		when(checkboxesUseLinkPropertyValues.toString()).thenReturn(links1);
		//when(value2.toString()).thenReturn(links2);
		
		checkboxesUse.activate();
	}
	
}
