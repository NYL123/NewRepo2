package apps.hertz.components.pages.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.WCMMode;
import com.day.cq.wcm.commons.AbstractImageServlet;
import com.day.cq.wcm.foundation.Image;
import com.day.image.Layer;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;

public class vehicleimage_GET extends AbstractImageServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 196913257631905531L;

	private static final Logger LOGGER = LoggerFactory.getLogger(vehicleimage_GET.class);
	
	@Override
	protected Layer createLayer(ImageContext arg0) throws RepositoryException, IOException {
		// don't create the layer yet. handle everything later
		return null;
	}


	protected void writeLayer(SlingHttpServletRequest req,
            SlingHttpServletResponse resp,
            ImageContext c, Layer layer)
            		throws IOException, RepositoryException {
                        String dataConfigPath=StringUtils.EMPTY;
		LOGGER.debug("Start:- writeLayer method of img.GET");
		resp.setContentType("image/png");
        resp.setHeader("Cache-Control", "no-store");
		String [] selectors=req.getRequestPathInfo().getSelectors();
		String rendition=selectors[4];
		String sippCode=selectors[2];
		String country=selectors[1];
		String dataDirectory=selectors[0];
		SlingBindings bindings = (SlingBindings) req.getAttribute(SlingBindings.class.getName());
        SlingScriptHelper scriptHelper = bindings.getSling();
        HertzConfigFactory configFactory=scriptHelper.getService(HertzConfigFactory.class);
        String[] mappings = (String[]) configFactory.getPropertyValue("hertz.renditions.selectornamesmapping");
		String renditionName = getMatchingRenditionNameFromSelector(rendition, mappings);
        String brand=req.getRequestURI().split("/")[2];
        String [] dataPaths= (String[])configFactory.getPropertyValue("hertz.data.basepath");
        for(String string:dataPaths){
    	   if(string.contains(brand)){
    		   dataConfigPath=string;
    		   break;
    	   }
        }

        if(StringUtils.isNotEmpty(dataConfigPath)){
        	String finalDataPath=dataConfigPath+"/"+dataDirectory+"/"+country;
        	ResourceResolver resolver=req.getResourceResolver();
        	String fileReference=StringUtils.EMPTY;
        	JCRService jcrService=scriptHelper.getService(JCRService.class);
        	if(ResourceUtil.isNonExistingResource(resolver.resolve(finalDataPath))){
        		finalDataPath=finalDataPath.replace(country, "default");
        		if(ResourceUtil.isNonExistingResource(resolver.resolve(finalDataPath))){
        			resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
					return;
        		}else{
        			Map<String, String> predicateMap = new HashMap<>();
                	preparePredicateMap(predicateMap,finalDataPath,sippCode);
                	if (null != jcrService){
                		SearchResult defaultCountryResult=jcrService.searchResults(resolver, predicateMap);
                		if(defaultCountryResult.getHits().size()==0){
                			resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
    						return;
                		}else{
                			fileReference = getFileReference(fileReference, defaultCountryResult);
                		}
                	}
        		}
        	}else{
        		Map<String, String> predicateMap = new HashMap<>();
            	preparePredicateMap(predicateMap,finalDataPath,sippCode);
                LOGGER.debug("The map looks like :- "+predicateMap);
            	if (null != jcrService){
            		SearchResult result=jcrService.searchResults(resolver, predicateMap);
            		LOGGER.debug("result.getHits():-"+result.getHits().size());
            		if(result.getHits().size()==0){
            			finalDataPath=finalDataPath.replace(country, "default");
            			LOGGER.debug("final dataPath after replacing:- "+finalDataPath);
            			if(ResourceUtil.isNonExistingResource(resolver.resolve(finalDataPath))){
            				resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
    						return;
            			}else{
            				preparePredicateMap(predicateMap,finalDataPath,sippCode);
                			SearchResult defaultResult=jcrService.searchResults(resolver, predicateMap);
                			if(defaultResult.getHits().size()==0){
                				resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
        						return;
                			}else{
                				fileReference = getFileReference(fileReference, defaultResult);
                			}
            			}
            		}else{
            			fileReference = getFileReference(fileReference, result);	
            		}
            	}
        	}
        	if(StringUtils.isNotBlank(fileReference)){
        		Resource imageResource=resolver.getResource(fileReference);
            	if(null!=imageResource){
            		Asset asset = imageResource.adaptTo(Asset.class);
            		if(null != asset){
            			Rendition assetRendition = asset.getRendition(StringUtils.lowerCase(StringUtils.trim(renditionName+".png")));
            			if(null != assetRendition){
            				Resource renditionResource = assetRendition.adaptTo(Resource.class);
            				Image image = new Image(renditionResource);
        					image.set(Image.PN_REFERENCE, renditionResource.getPath());
        					layer = this.getLayer(image);
        					if (layer == null) {
    							resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
    							return;
    						}
        					layer.write("image/png", 96, resp.getOutputStream());
            			}else{
							resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
							return;
						}
            		}else{
						resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
						return;
					}
            	}else{
        		resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
				return;
        	}
        	}else{
        		resp.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
				return;
        	}
        }
        resp.flushBuffer();
		LOGGER.debug("Exit:- writeLayer method of img.GET");
	}


	/**
	 * @param fileReference
	 * @param defaultCountryResult
	 * @return
	 * @throws RepositoryException
	 */
	private String getFileReference(String fileReference, SearchResult defaultCountryResult)
			throws RepositoryException {
		for (Hit hit : defaultCountryResult.getHits()){
			Resource imagePairResource=hit.getResource();
			ValueMap map=imagePairResource.getValueMap();
			if(map.containsKey(HertzConstants.FILE_REFERENCE)){
				fileReference=map.get(HertzConstants.FILE_REFERENCE,String.class);
				break;
			}       			
		}
		return fileReference;
	}

	private void preparePredicateMap(Map<String, String> predicateMap, String finalDataPath,String sippCode) {
		LOGGER.debug("Start:- preparePredicateMap of img.GET");
		predicateMap.put(HertzConstants.TYPE, "nt:unstructured");
		predicateMap.put(HertzConstants.PATH, finalDataPath);
		predicateMap.put("property","key");
		predicateMap.put("property.value", sippCode);
		predicateMap.put(HertzConstants.LIMIT, "1");
		LOGGER.debug("Exit:- preparePredicateMap of img.GET");
		
	}
	
	/**
	 * Gets the Image layer.
	 *
	 * @param image
	 *            The Image to get the layer from
	 * @return the image's Layer
	 * @throws IOException
	 */
	private Layer getLayer(final Image image) throws IOException {
		Layer layer = null;

		if (null != image) {
			try {
				layer = image.getLayer(false, false, false);
			} catch (RepositoryException ex) {
				// LOGGER.error("Could not create layer");
			}
			if (layer == null) {
				// LOGGER.error("Could not create layer - layer is null;");
			} else {
				image.crop(layer);
				image.rotate(layer);
			}
		}
		return layer;
	}

    private String getMatchingRenditionNameFromSelector(String rendition, String[] mappings) {
		String renditionName = StringUtils.EMPTY;
		List<String> mappingsList = Arrays.asList(mappings);
		Map<String, String> mappingsMap = new HashMap<String, String>();
		for (String entry : mappingsList) {
			String[] splits = entry.split(HertzConstants.COLON);
			if (splits.length == 2) {
				mappingsMap.put(splits[0], splits[1]);
			}
		}
		if (mappingsMap.containsKey(rendition)) {
			renditionName = mappingsMap.get(rendition);
		}
		return renditionName;
	}

}
