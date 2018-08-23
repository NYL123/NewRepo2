package com.hertz.digital.platform.use;

import static org.powermock.api.mockito.PowerMockito.when;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
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
import org.slf4j.LoggerFactory;
import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.constants.HertzConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WCMUsePojo.class, LoggerFactory.class})
public class TestLocationDirectoryHeroImageUse {

	@InjectMocks
	private LocationDirectoryHeroImageUse locationDirectoryHeroImageUse;
	
	@Mock
	ValueMap map;
	
	@Mock
	ValueMap map1;
	
	@Mock
	Page page;
	
	@Mock
	Page parentPage;
	
	@Mock
	Resource heroImageResource;
	
	String fileReference = "/content/dam/hertz-rac/locations/img_FPO_location_detail_mobile@2x.png";
	String altTxt = "altTxt";
	
	@Before
	public final void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		locationDirectoryHeroImageUse =PowerMockito.mock(LocationDirectoryHeroImageUse.class);
		Mockito.doCallRealMethod().when(locationDirectoryHeroImageUse).activate();
		Mockito.doCallRealMethod().when(locationDirectoryHeroImageUse).setAltText(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationDirectoryHeroImageUse).setImagePath(Mockito.anyString());
		Mockito.doCallRealMethod().when(locationDirectoryHeroImageUse).getAltText();
		Mockito.doCallRealMethod().when(locationDirectoryHeroImageUse).getImagePath();
	}
	
	@Test
	public final void testActivate() throws Exception{
		when(locationDirectoryHeroImageUse.getProperties()).thenReturn(map);
		when(map.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(true);
		when(map.get(HertzConstants.FILE_REFERENCE,String.class)).thenReturn(fileReference);
		when(map.containsKey(HertzConstants.ALT_TEXT)).thenReturn(true);
		when(map.get(HertzConstants.ALT_TEXT,String.class)).thenReturn(altTxt);
		locationDirectoryHeroImageUse.activate();
		
		when(locationDirectoryHeroImageUse.getProperties()).thenReturn(map);
		when(map.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(false);
		when(locationDirectoryHeroImageUse.getResourcePage()).thenReturn(page);
		when(page.getAbsoluteParent(4)).thenReturn(parentPage);
		when(parentPage.getContentResource(HertzConstants.HERO)).thenReturn(heroImageResource);
		when(heroImageResource.getValueMap()).thenReturn(map1);
		when(map1.containsKey(HertzConstants.FILE_REFERENCE)).thenReturn(true);
		when(map1.get(HertzConstants.FILE_REFERENCE,String.class)).thenReturn(fileReference);
		when(map1.containsKey(HertzConstants.ALT_TEXT)).thenReturn(true);
		when(map1.get(HertzConstants.ALT_TEXT,String.class)).thenReturn(altTxt);
		locationDirectoryHeroImageUse.activate();
	}
	
	@Test
	public final void testGetterSetters(){
		String alttext="altText";
		String imagePath="imagePath";
		locationDirectoryHeroImageUse.setAltText(alttext);
		locationDirectoryHeroImageUse.setImagePath(imagePath);
		Assert.assertTrue(locationDirectoryHeroImageUse.getAltText().equalsIgnoreCase(alttext));
		Assert.assertTrue(locationDirectoryHeroImageUse.getImagePath().equalsIgnoreCase(imagePath));
		
	}
	
}
