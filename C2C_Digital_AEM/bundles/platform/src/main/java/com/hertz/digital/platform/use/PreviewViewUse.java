package com.hertz.digital.platform.use;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.factory.FedServicesConfigurationFactory;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * This class will be called to get the preview link url for the preview link
 * button only in the preview mode.
 * 
 * @author a.dhingra
 *
 */
public class PreviewViewUse extends WCMUsePojo {

	/** The Constant COLON_SLASH. */
	private static final String COLON_SLASH = "://";

	/** Logger instantiation. */
	private static final Logger logger = LoggerFactory.getLogger(PreviewViewUse.class);

	/** The default locale. */
	private static final String DEFAULT_LOCALE = "en-us";

	/** The hyphen. */
	private static final String HYPHEN = "-";

	/**
	 * Default Constructor.
	 */
	public PreviewViewUse() {
		super();
	}

	/** The preview link target. */
	private String previewLinkTarget;

	/**
	 * The activate method is used to fetch and set the preview path value.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void activate() throws Exception {
		logger.debug("Entering activate() method of PreviewViewUse");
		ValueMap map = getPageProperties();
		Locale locale = getCurrentPage().getLanguage(false);
		String currentProtocol = getRequest().getScheme();
		StringBuilder formattedLocale = new StringBuilder(HertzConstants.SLASH);
		if (null == locale) {
			formattedLocale.append(DEFAULT_LOCALE);
		} else {
			formattedLocale.append(locale.getLanguage()).append(HYPHEN).append(locale.getCountry().toLowerCase());
		}

		String previewModePath = PropertiesUtil.toString(map.get(HertzConstants.PREVIEW_MODE_PATH), StringUtils.EMPTY);
		String fedUrl = getSlingScriptHelper().getService(FedServicesConfigurationFactory.class)
				.getStringPropertyValue(HertzConstants.HERTZ_FED_BASEPATH);
		if (StringUtils.isNotBlank(previewModePath)) {
			logger.debug("preview mode path :-  " + previewModePath);
			setPreviewLinkTarget(currentProtocol + COLON_SLASH + fedUrl + formattedLocale + previewModePath);
		} else {
			String template = map.get(HertzConstants.JCR_CQ_TEMPLATE, String.class);
			if (StringUtils.isNotBlank(template) && template.equalsIgnoreCase(HertzConstants.DATA_TEMPLATE_PATH)) {
				String fedHomePagePath = getSlingScriptHelper().getService(FedServicesConfigurationFactory.class)
						.getStringPropertyValue(HertzConstants.HERTZ_FED_HOMEPAGE_PATH);
				logger.debug("fed home page path :- " + fedHomePagePath);
				setPreviewLinkTarget(currentProtocol + COLON_SLASH + fedUrl + formattedLocale + fedHomePagePath);
			} else {
				String pagePath = getCurrentPage().getPath();
				String shortededPath = HertzUtils.shortenIfPath(pagePath);
				logger.debug("shortened path :- " + shortededPath);
				setPreviewLinkTarget(currentProtocol + COLON_SLASH + fedUrl + formattedLocale + shortededPath);
			}
		}
		logger.debug("Exit activate() method of PreviewViewUse");
	}

	/**
	 * This method is used to get the value of previewLinkTarget variable.
	 *
	 * @return the previewLinkTarget
	 */
	public String getPreviewLinkTarget() {
		return previewLinkTarget;
	}

	/**
	 * This method sets the value of the previewLinkTarge variable.
	 * 
	 * @param previewLinkTarget
	 *            the previewLinkTarget to set
	 */
	private void setPreviewLinkTarget(String previewLinkTarget) {
		this.previewLinkTarget = previewLinkTarget;
	}
}
