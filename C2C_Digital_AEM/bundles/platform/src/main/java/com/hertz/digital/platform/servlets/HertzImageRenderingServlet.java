package com.hertz.digital.platform.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.commons.util.DamUtil;
import com.day.cq.wcm.foundation.Image;
import com.day.image.Layer;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.workflows.HertzImageRenditionProcess;

/*
 * This class generate image renditions with different quality/depth.
 * 
 * */
@Component(metatype = true, immediate = true, description = "Hertz Image Rendition Servlet", label = "Hertz Image Rendition Servlet")
@Service(Servlet.class)
@Properties({ @Property(name = "sling.servlet.resourceTypes", value = "sling/servlet/default"),
		@Property(name = "sling.servlet.selectors", value = "enscale"),
		@Property(name = "sling.servlet.extensions", value = "png"),
		@Property(name = "sling.servlet.methods", value = "GET") })
public class HertzImageRenderingServlet extends SlingSafeMethodsServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MIME_TYPE_PNG = "image/png";
	private static final Logger LOGGER = LoggerFactory.getLogger(HertzImageRenditionProcess.class);
	@Reference
	HertzConfigFactory hConfigFactory;

	/**
	 * Constructor
	 */
	public HertzImageRenderingServlet() {
		super();
	}
	
	@Override
	protected final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		// Get the quality
		final double quality = Double.valueOf(hConfigFactory.getStringPropertyValue("hertz.renditions.quality"));
		final Image image = this.resolveImage(request);
		final String mimeType = this.getMimeType(request, image);
		response.setContentType(mimeType);
		Layer layer = this.getLayer(image);

		if (layer == null) {
			response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
			return;
		}

		layer.write(mimeType, quality, response.getOutputStream());
		response.flushBuffer();
	}

	/**
	 * Intelligently determines how to find the Image based on the associated
	 * SlingRequest.
	 *
	 * @param request
	 *            the SlingRequest Obj
	 * @return the Image object configured w the info of where the image to
	 *         render is stored in CRX
	 */
	protected final Image resolveImage(final SlingHttpServletRequest request) {
		final String path = request.getRequestPathInfo().getResourcePath();
		String[] selectors = request.getRequestPathInfo().getSelectors();
		String[] mappings = (String[]) hConfigFactory.getPropertyValue("hertz.renditions.selectornamesmapping");
		String extension = hConfigFactory.getStringPropertyValue("hertz.renditions.extension");

		String renditionName = getMatchingRenditionNameFromSelector(selectors, mappings);
		final ResourceResolver resourceResolver;
		Resource resource;
		Image image = null;
		if (null != selectors && selectors.length == 2 && StringUtils.isNotEmpty(renditionName)) {
			resourceResolver = request.getResourceResolver();
			resource = resourceResolver.getResource(path);

			if (null == resource) {
				LOGGER.debug("The hit path can't be resolved as a resource. {} ", path);
			} else {
				String reference = (String) resource.getValueMap().get("fileReference");
				if (null == reference) {
					LOGGER.debug(
							"The reference is nul at the resolved path. There is no attribute found with the name fileReference . {} ",
							resource.getValueMap());
				} else {
					final Asset asset = DamUtil.resolveToAsset(resourceResolver.getResource(reference));
					if (null != asset) {
						Rendition rendition = asset.getRendition(
								renditionName + HertzConstants.DOT + StringUtils.defaultIfEmpty(extension, "png"));
						if (rendition == null) {
							LOGGER.warn("Could not find rendition [ {} ] for [ {} ]", renditionName,
									resource.getPath());
							rendition = asset.getOriginal();
						}
						final Resource renditionResource = request.getResourceResolver()
								.getResource(rendition.getPath());
						image = new Image(renditionResource);
						image.set(Image.PN_REFERENCE, renditionResource.getPath());
					}
					return image;
				}
			}
		}

		return image;
	}

	/**
	 * @param selectors
	 * @param mappings
	 * @return
	 */
	private String getMatchingRenditionNameFromSelector(String[] selectors, String[] mappings) {
		String renditionName = StringUtils.EMPTY;
		List<String> mappingsList = Arrays.asList(mappings);
		Map<String, String> mappingsMap = new HashMap<String, String>();
		for (String entry : mappingsList) {
			String[] splits = entry.split(HertzConstants.COLON);
			if (splits.length == 2) {
				mappingsMap.put(splits[0], splits[1]);
			}
		}
		if (mappingsMap.containsKey(selectors[1])) {
			renditionName = mappingsMap.get(selectors[1]);
		}
		return renditionName;
	}

	/**
	 * Gets the mimeType of the image. - The last segments suffix is looked at
	 * first and used - if the last suffix segment's "extension" is .orig or
	 * .original then use the underlying resources mimeType - else look up the
	 * mimeType to use based on this "extension" - default to the resource's
	 * mimeType if the requested mimeType by extension is not supported.
	 *
	 * @param image
	 *            the image to get the mimeType for
	 * @return the string representation of the image's mimeType
	 */
	private String getMimeType(final SlingHttpServletRequest request, final Image image) {

		try {
			if (null == image) {
				return MIME_TYPE_PNG;
			} else {
				return image.getMimeType();
			}

		} catch (final RepositoryException e) {
			return MIME_TYPE_PNG;
		}

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
				LOGGER.error("Could not create layer");
			}
			if (layer == null) {
				LOGGER.error("Could not create layer - layer is null;");
			} else {
				image.crop(layer);
				image.rotate(layer);
			}
		}
		return layer;
	}

}
