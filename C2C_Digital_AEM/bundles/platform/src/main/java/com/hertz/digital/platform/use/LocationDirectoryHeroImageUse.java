/**
 * 
 */
package com.hertz.digital.platform.use;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.constants.HertzConstants;

/**
 * @author a.dhingra
 *
 */
public class LocationDirectoryHeroImageUse extends WCMUsePojo{
	
	/**
	 * Default Constructor Declaration
	 */
	public LocationDirectoryHeroImageUse(){
		super();
	}
	
	/**
	 * LOGGER instantiation.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LocationDirectoryHeroImageUse.class);
	
	/**
	 * Field for Path of Image
	 */
	private String imagePath;
	/**
	 * Field for altText
	 */
	private String altText;
	
	@Override
	public void activate() throws Exception {
		logger.debug("******Entering activate method of LocationDirectoryHeroImageUse*******");
		ValueMap map=getProperties();
		
		if(map.containsKey(HertzConstants.FILE_REFERENCE)){
			setImagePath(map.get(HertzConstants.FILE_REFERENCE,String.class));
			if(map.containsKey(HertzConstants.ALT_TEXT)){
				setAltText(map.get(HertzConstants.ALT_TEXT,String.class));
			}
		}else{
			Page page=getResourcePage();
			Page parentPage = page.getAbsoluteParent(4);
			if(null!=parentPage){
				Resource heroImageResource = parentPage.getContentResource(HertzConstants.HERO);
				ValueMap map1 = heroImageResource.getValueMap();
				if (map1.containsKey(HertzConstants.FILE_REFERENCE)) {
					setImagePath(map1.get(HertzConstants.FILE_REFERENCE,String.class));
					setAltText(map1.get(HertzConstants.ALT_TEXT,String.class));
				}
			}
			
		}
		
		logger.debug("******Exiting activate method of LocationDirectoryHeroImageUse*******");
	}
	
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the altText
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * @param altText the altText to set
	 */
	public void setAltText(String altText) {
		this.altText = altText;
	}
	
	

}
