package com.hertz.digital.platform.workflows;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.api.handler.AssetHandler;
import com.day.cq.dam.api.handler.store.AssetStore;
import com.day.cq.dam.commons.process.AbstractAssetWorkflowProcess;
import com.day.cq.dam.core.process.CreateWebEnabledImageProcess.Arguments;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;

@Component
@Service
@Property(name = "process.label", value = { "Hertz Rendition Process" })
public class HertzImageRenditionProcess extends AbstractAssetWorkflowProcess {

	private static final String SMALL_1X = "small@1x";
	private static final String LARGE_1X = "large@1x";
	private static final String SMALL_2X = "small@2x";
	private static final String LARGE_2X = "large@2x";
	private static final String RESOLUTION = "RESOLUTION";
	private static final String MAX_HEIGHT = "MAX_HEIGHT";
	private static final String MAX_WIDTH = "MAX_WIDTH";
	private static final String DAM_PHYSICALHEIGHTININCHES = "dam:Physicalheightininches";
	private static final String DAM_PHYSICALWIDTHININCHES = "dam:Physicalwidthininches";
	private static final String HERTZ_RENDITIONS_EXTENSION = "hertz.renditions.extension";
	private static final String MAXWIDTH = "MAXWIDTH";
	private static final String THRESHOLDWIDTH = "THRESHOLDWIDTH";
	private static final String MIME_TYPE_PNG = "image/png";
	private static final Logger log = LoggerFactory.getLogger(HertzImageRenditionProcess.class);
	@Reference
	protected AssetStore assetStore;

	@Reference
	HertzConfigFactory hertzConfigFactory;

	/**
	 * Constructor
	 */
	public HertzImageRenditionProcess() {
		super();
	}

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {

		double thresholdwidth = Double.valueOf(args.get(THRESHOLDWIDTH, "1536"));
		double maximum_width = Double.valueOf(args.get(MAXWIDTH, "2400"));

		String mimeType = args.get(Arguments.MIME_TYPE.name(), MIME_TYPE_PNG);

		double quality = Double.valueOf(args.get(Arguments.QUALITY.name(), "96"));

		Session session = workflowSession.getSession();
		Asset asset = getAssetFromPayload(workItem, session);
		String extension = hertzConfigFactory.getStringPropertyValue(HERTZ_RENDITIONS_EXTENSION);

		if (null != asset && asset.getMimeType().startsWith("image")) {
			try {
				ValueMap metadata = new ValueMapDecorator(asset.getMetadata());
				/*
				 * double originalWidth = ((Double)
				 * metadata.get(DAM_PHYSICALWIDTHININCHES,
				 * Double.valueOf(0.0D))) .doubleValue(); double originalHeight
				 * = ((Double) metadata.get(DAM_PHYSICALHEIGHTININCHES,
				 * Double.valueOf(0.0D))) .doubleValue(); if ((originalWidth ==
				 * 0.0D) || (originalHeight == 0.0D)) { log.debug(
				 * "The original height and width of the image is not found, please re-upload a valid image"
				 * ); return; } else if((originalWidth == -1.0D) ||
				 * (originalHeight == -1.0D)){ log.debug(
				 * "The original height and width of the image is not found, Trying to read from other properties"
				 * );
				 * 
				 * }
				 */
				long originalWidth = (long) metadata.get("tiff:ImageWidth", Long.valueOf(0L));
				long originalHeight = (long) metadata.get("tiff:ImageLength", Long.valueOf(0L));

				if (originalWidth > 0 && originalHeight > 0) {
					double maxWidth = getClamped(args, MAX_WIDTH, maximum_width, 1.0D, maximum_width);
					double maxHeight = getClamped(args, MAX_HEIGHT, maximum_width, 1.0D, maximum_width);
					double maxRes = Math.min(maxWidth / originalWidth, maxHeight / originalHeight);
					double res = getClamped(args, RESOLUTION, quality, 1.0D, maxRes);
					Rendition original = asset.getOriginal();
					AssetHandler assetHandler = this.assetStore.getAssetHandler(original.getMimeType());
					/*writeRenditionToRepository(assetHandler, originalHeight, originalWidth, res, original,
							LARGE_2X + HertzConstants.DOT + extension, session, asset, mimeType, extension);*/
					writeDependentRenditionsToRepository(thresholdwidth, maximum_width, mimeType, session, asset,
							extension, originalWidth, originalHeight, res, originalWidth, original, assetHandler);
				} else {
					log.error(
							"The original image width and height cannot be calculated. The attributes tiff:imageWidht and itff:imageHeight attriutes not found on the image");
				}

			} catch (IOException | RepositoryException e) {
				log.error("Exception occurred. {} {}", e, e.getStackTrace());
			}
		}
	}

	/**
	 * This method would employ business rules to generate dependent renditions.
	 * 
	 * @param thresholdwidth
	 *            The minimum threshhold which would logically drive the small
	 *            renditions.
	 * @param maximum_width
	 *            THe maximum width.
	 * @param mimeType
	 *            THe image's mime type
	 * @param session
	 *            The session object
	 * @param asset
	 *            The asset object
	 * @param extension
	 *            The extension
	 * @param originalWidth
	 *            The original width of the image.
	 * @param originalHeight
	 *            The original width of the image.
	 * @param res
	 *            The default resolution of the image.
	 * @param orignalWidthInPixels
	 *            The original width in pixels.
	 * @param original
	 *            The original rendition object.
	 * @param assetHandler
	 *            The asset handler object.
	 * @throws IOException
	 *             The IO exception.
	 * @throws RepositoryException
	 *             The Repository exception.
	 */
	protected void writeDependentRenditionsToRepository(double thresholdwidth, double maximum_width, String mimeType,
			Session session, Asset asset, String extension, double originalWidth, double originalHeight, double res,
			long orignalWidthInPixels, Rendition original, AssetHandler assetHandler)
			throws IOException, RepositoryException {
		
		/*
		 * Large Rendition Calculations
		 * */
		/*Large-2x
		 * 
		 * large 2x - 100% of original; if the original is greater than 2400 px wide, 
		 * restrict the image to a maximum of 2400 px wide with the height being 
		 * proportional to the restricted width.
		 * */
		if(orignalWidthInPixels > maximum_width){
			double adjustedWidth = maximum_width;
			double adjustedHeight = adjustedWidth * (originalHeight / originalWidth);
			writeRenditionToRepository(assetHandler, adjustedHeight, adjustedWidth, res, original,
					LARGE_2X + HertzConstants.DOT + extension, session, asset, mimeType, extension);
		} else {
			writeRenditionToRepository(assetHandler, originalHeight, originalWidth, res, original,
					LARGE_2X + HertzConstants.DOT + extension, session, asset, mimeType, extension);
		}
		/*Large-1x 
		 * 
		 * large 1x - 50% of original; if original is greater than 1200 px wide,
		 * restrict the image to a maximum of 1200 px wide with the height being 
		 * proportional to the restricted width*/
		if(orignalWidthInPixels > 1200){
			double adjustedWidth = 1200;
			double adjustedHeight = adjustedWidth * (originalHeight / originalWidth);
			writeRenditionToRepository(assetHandler, adjustedHeight, adjustedWidth, res, original,
					LARGE_1X + HertzConstants.DOT + extension, session, asset, mimeType, extension);
		} else {
			double adjustedWidth = originalWidth / 2;
			double adjustedHeight = adjustedWidth * (originalHeight / originalWidth);
			writeRenditionToRepository(assetHandler, adjustedHeight, adjustedWidth, res, original,
					LARGE_1X + HertzConstants.DOT + extension, session, asset, mimeType, extension);
		}
		/*
		 * Small Rendition Calculations
		 * 
		 * */
		/*Small-2x
		 * 
		 * small 2x - 100% of original; if 50% of the original is greater than 1536 px wide, 
		 * restrict the image to a maximum of 1536 px wide with the height being proportional 
		 * to the restricted width
		 * */
		if((orignalWidthInPixels / 2) > thresholdwidth){
			double adjustedWidth = thresholdwidth;
			double adjustedHeight = adjustedWidth * (originalHeight / originalWidth);
			writeRenditionToRepository(assetHandler, adjustedHeight, adjustedWidth, res, original,
					SMALL_2X + HertzConstants.DOT + extension, session, asset, mimeType, extension);
		} else {
			double adjustedWidth = originalWidth;
			double adjustedHeight = adjustedWidth * (originalHeight / originalWidth);
			writeRenditionToRepository(assetHandler, adjustedHeight, adjustedWidth, res, original,
					SMALL_2X + HertzConstants.DOT + extension, session, asset, mimeType, extension);
		}
		/*Small-1x
		 * 
		 * small 1x - 50% of original; if 50 greater than 768 px wide, 
		 * restrict the image to a maximum of 768 px wide with the height 
		 * being proportional to the restricted width*/
		if((orignalWidthInPixels / 2) > (thresholdwidth / 2)){
			double adjustedWidth = thresholdwidth / 2;
			double adjustedHeight = adjustedWidth * (originalHeight / originalWidth);
			writeRenditionToRepository(assetHandler, adjustedHeight, adjustedWidth, res, original,
					SMALL_1X + HertzConstants.DOT + extension, session, asset, mimeType, extension);
		} else {
			double adjustedWidth = originalWidth / 2;
			double adjustedHeight = adjustedWidth * (originalHeight / originalWidth);
			writeRenditionToRepository(assetHandler, adjustedHeight, adjustedWidth, res, original,
					SMALL_1X + HertzConstants.DOT + extension, session, asset, mimeType, extension);
		}
	}

	/**
	 * This method does the actual writing o the renditions to the repository.
	 * 
	 * @param assetHandler
	 *            The asset handler object.
	 * @param originalHeight
	 *            The original height of the image.
	 * @param originalWidth
	 *            The original width of the image.
	 * @param res
	 *            The resolution.
	 * @param original
	 *            The original rendition.
	 * @param renditionName
	 *            The rendition name which has to be used.
	 * @param session
	 *            The session object.
	 * @param asset
	 *            The asset object.
	 * @param mimeType
	 *            The mime type associated.
	 * @param extension
	 *            The extension to be used.
	 * @throws IOException
	 *             The IO Exception.
	 * @throws RepositoryException
	 *             The repository exception.
	 */
	protected void writeRenditionToRepository(AssetHandler assetHandler, double originalHeight, double originalWidth,
			double res, Rendition original, String renditionName, Session session, Asset asset, String mimeType,
			String extension) throws IOException, RepositoryException {
		Dimension dim = new Dimension((int) Math.round(originalWidth), (int) Math.round(originalHeight));
		BufferedImage image = assetHandler.getImage(original, dim);
		log.debug("Rasterized to an image with dim {}x{}", image.getWidth(), image.getHeight());

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		boolean w = ImageIO.write(image, extension, out);
		if (w) {
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			asset.setBatchMode(true);
			asset.addRendition(renditionName, in, mimeType);
			session.save();
			in.close();
		}
		out.close();
	}

	/**
	 * The method scraped from the PDF Thumbnail Process service, responsible
	 * for taking care of boundary conditions w.r.t quality, height and width.
	 * 
	 * @param args
	 *            The metadata map.
	 * @param option
	 *            The Reference within the metadata map.
	 * @param def
	 *            The default configuration.
	 * @param min
	 *            The minimum value.
	 * @param max
	 *            The maximum value.
	 * @return The clamped value.
	 */
	protected double getClamped(MetaDataMap args, String option, double def, double min, double max) {
		double v = ((Double) args.get(option, Double.valueOf(def))).doubleValue();
		return Math.min(Math.max(v, min), max);
	}

	/**
	 * Computes the quality based on the "synthetic" Image Quality transform
	 * params
	 *
	 * Image Quality does not "transform" in the usual manner (it is not a
	 * simple layer manipulation) thus this ad-hoc method is required to handle
	 * quality manipulation transformations.
	 *
	 * If "quality" key is no available in "transforms" the default of 82 is
	 * used (magic AEM Product quality setting)
	 *
	 * @param mimeType
	 *            the desired image mimeType
	 * @param transforms
	 *            the map of image transform params
	 * @return
	 */
	protected double getQuality(final String mimeType, final ValueMap transforms) {
		final String key = "quality";
		final int defaultQuality = 96;
		final int maxQuality = 100;
		final int minQuality = 0;
		final int maxQualityGIF = 255;
		final double oneHundred = 100D;

		log.debug("Transforming with [ quality ]");

		double quality = transforms.get(key, defaultQuality);

		if (quality > maxQuality || quality < minQuality) {
			quality = defaultQuality;
		}

		quality = quality / oneHundred;

		if (StringUtils.equals("image/gif", mimeType)) {
			quality = quality * maxQualityGIF;
		}

		return quality;
	}

}
