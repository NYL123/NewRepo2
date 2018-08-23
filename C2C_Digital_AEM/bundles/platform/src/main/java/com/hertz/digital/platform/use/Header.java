package com.hertz.digital.platform.use;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.hertz.digital.platform.constants.HertzConstants;

/**
 * This Class checks if the TopNav page has been created below the header page
 * if not then creates a new one.
 * 
 * @author puneet.soni
 * 
 */

public class Header extends WCMUsePojo {
	/**
	 * Default Constructor
	 */
	public Header() {
		super();
	}

	/**
	 * LOGGER instantiation.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Header.class);
	private static final String TOPNAV_PAGE_NAME = "topnav";
	private static final String TOPNAV_TEMPLATE = "/apps/hertz/templates/topnavigation";
	private static final String TOPNAV_PAGE_TITLE = TOPNAV_PAGE_NAME;
	

	private Session session;

	/**
	 * 
	 * Activate method for Header Class
	 * 
	 * @param 
	 * @return 
	 * @throws WCMException
	 * 
	 * */
	@Override
	public void activate() throws WCMException {
		logger.debug("******Entering activate method of Header*******");
	
			session= getResourceResolver().adaptTo(Session.class);
			createTopnavPage();
		
		logger.debug("******Exiting activate method of Header*******");
	}

	/**
	 * Get the path of current Page and create a Page TopNav if the Page already
	 * doesn't exist.
	 * 
	 * @throws WCMException
	 * @throws RepositoryException
	 */
	private void createTopnavPage() throws WCMException {
		logger.debug("******Entering createTopnavPage method of Header*******");
		if (session != null) {
			PageManager pageManager = getResourceResolver()
					.adaptTo(PageManager.class);
			
			String path = getCurrentPage().getPath();
			
			// Check if TopNav Page is created
			Page page = pageManager.getPage(path + HertzConstants.SLASH
					+ TOPNAV_PAGE_NAME);
			
			// if TopNav Page doesn't exists then create a new Page.
			if (null == page) {
				pageManager.create(path + HertzConstants.SLASH,
						TOPNAV_PAGE_NAME, TOPNAV_TEMPLATE, TOPNAV_PAGE_TITLE,
						true);
			}

		}
		logger.debug("******Exiting createTopnavPage method of Header*******");
	}
}
