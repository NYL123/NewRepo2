package com.hertz.digital.platform.servlets;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;
import com.hertz.digital.platform.workflows.HertzRewardsExcelImporter;
/**
 * Servlet for importing excel list into DAM and updating
 * the components on the content pages for each category. 
 * @author himanshu.i.sharma
 *
 */

@Component(immediate = true, metatype = true, label = "Hertz - Country and State List Excel Import ", description = "Hertz - Import Country and State List Excel into Nodes")
@Service
@Properties(value = {
		@Property(name = "sling.servlet.paths", value = "/bin/hertz/importexcel", propertyPrivate = true)})
public class CountryStateListExcelImportServlet extends SlingAllMethodsServlet {
	
	/** Serial Version UID */
	private static final long serialVersionUID = -4152545271866089740L;
	
	/** Logger Instantiation */
	private static final Logger LOGGER = LoggerFactory.getLogger(CountryStateListExcelImportServlet.class);
	
	/**
	 * Default Constructors
	 */
	public CountryStateListExcelImportServlet() {
		super();
	}
	
	
	
		
	private String COUNTRY_NAME = "countryName";
	private String COUNTRY_CODE = "countryCode";
	private String ERROR_VALUE = "errorValue";
	private String ERROR_CODE = "errorCode";
	private String STATE_NAME = "stateName";
	private String STATE_CODE = "stateCode";
	private String TYPE = "type";
	private String AIRLINE_CODE = "airlineTrainCarrierCode";
	private String AIRLINE_NAME = "airlineTrainCarrierName";
	private String COUNTRY = "country";
	private String REWARDS = "rewards";
	private String AIRLINE = "airline";
	private String STATE = "state";
	private String ERROR = "errormsg"; 
	private String CONFIG_TEXT = "configtext";
	private String GENERIC_FIELD_PAIR = "genericfieldpair";
	private String RESIDENCYCOUNTRY = "residencycountry";
	private String OPTIN = "optin";
	//private String DEFAULTOPTIN = "defaultOptIn";
	
	
	private static final String PATH_TO_BINARY = "/jcr:content/renditions/original";
	
	PageManager pageManager = null;
	XSSFWorkbook workbook = null;
	XSSFSheet sheet= null;
	Session session= null;
	int nodesCreated = 0;
	
	@Reference
	ResourceResolverFactory rFactory;
	
	@Reference
    private QueryBuilder builder;
	/**
	 * This method is used to save the uploaded file in AEM DAM
	 * and create country and state/province nodes in jcr
	 *
	 *@param request,response
	 *@return void
	 *
	 *@throws ServletException
	 *@throws- IOException
	 */
	@Override
	protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		LOGGER.debug("Entering method doPost of CountryStateListExcelImportServlet");
		
		String excelFileDAMPath = StringUtils.EMPTY;
		String filePathInDam = StringUtils.EMPTY;
		String damPath = StringUtils.EMPTY;
		String contentPath = StringUtils.EMPTY;
		session = req.getResourceResolver().adaptTo(Session.class);
		pageManager  = req.getResourceResolver().adaptTo(PageManager.class);
		ResourceResolver resourceResolver = null;
		 try
         {
		 resourceResolver = req.getResourceResolver();
         final boolean isMultipart = ServletFileUpload.isMultipartContent(req);
         if (isMultipart) {
        	 final java.util.Map<String, RequestParameter[]> params = req.getRequestParameterMap();
             for (final java.util.Map.Entry<String, RequestParameter[]> pairs : params.entrySet()) {

               final RequestParameter[] pArr = pairs.getValue();
               final RequestParameter param = pArr[0];
               final InputStream stream = param.getInputStream();
               String fileName = param.getFileName();
               damPath = req.getParameter("damPath");
               contentPath = req.getParameter("contentPath");
               if(!"".equalsIgnoreCase(damPath) && !"".equalsIgnoreCase(contentPath)){
	               excelFileDAMPath =  damPath+"/"+fileName;
	               filePathInDam = writeToDam(stream, excelFileDAMPath, resourceResolver);
	               importDataToContentNode(resp, fileName, filePathInDam, contentPath, resourceResolver);
               }
             }
           }
         }
         catch (Exception e) {
        	 LOGGER.error("Exception Occured :  {}, {}", e, e.getStackTrace());
         }finally {
 			HertzUtils.closeResolverSession(resourceResolver, session);
 		}
		nodesCreated= 0;
		LOGGER.debug("Exiting method doPost of CountryStateListExcelImportServlet");
	
	}
	
	/**
	 * This method saves the uploaded file into the AEM DAM using AssetManager APIs
	 * @param is
	 * @param resourceResolver2 
	 * @param newFile
	 * @return
	 */
	private String writeToDam(InputStream is, String excelFileDAMPath, ResourceResolver resourceResolver){
		LOGGER.debug("Entering method writeToDam of CountryStateListExcelImportServlet");
		
		try
		{
		    //Use AssetManager to place the file into the AEM DAM
		    AssetManager assetMgr = resourceResolver.adaptTo(AssetManager.class); 
		    assetMgr.createAsset(excelFileDAMPath, is, "application/vnd.ms-excel", true);
		    // Return the path to the file was stored
		    return excelFileDAMPath;
		}
		catch(Exception e)
		{
			LOGGER.error("Exception Occured :  {}, {}", e, e.getStackTrace());
		} 
		
		LOGGER.debug("Exiting method writeToDam of CountryStateListExcelImportServlet");
		return null;
	}
	
	public void importDataToContentNode(SlingHttpServletResponse resp, String fileName, String pathToExcelInDam, String contentPath, ResourceResolver resourceResolver) throws IOException, LoginException{
		String message = StringUtils.EMPTY;
		try {
			LOGGER.debug("Entering method importDataToContentNode of CountryStateListExcelImportServlet");
			
			Node contentNode = null;
			Page dataPage = null;
			dataPage = pageManager.getPage(contentPath);
			contentNode = dataPage.getContentResource().adaptTo(Node.class);
			Node parNode = getParNode(contentNode);
			Iterator<Row> rowIterator = getExcelRows(pathToExcelInDam);
			 if (! fileName.toLowerCase().contains(RESIDENCYCOUNTRY) && fileName.toLowerCase().contains(COUNTRY) ){
				 updateCountryListContent(rowIterator, parNode);
				 resp.getWriter().write("Total : " + nodesCreated + " country nodes created." );
			 }
			 else if (fileName.toLowerCase().contains(AIRLINE)){
				 updateAirlineListContent(rowIterator, parNode);
            	 resp.getWriter().write("Total : " + nodesCreated + " airline nodes created." );
             }
             else if (fileName.toLowerCase().contains(STATE)){
            	 updateStateListContent(rowIterator, parNode);
            	 resp.getWriter().write("Total : " + nodesCreated + " state nodes created." );
             } else if (fileName.toLowerCase().contains(ERROR)){
            	 updateErrorMsgListContent(rowIterator, parNode);
            	 resp.getWriter().write("Total : " + nodesCreated + " ERROR nodes created." );
             }else if (fileName.toLowerCase().contains(CONFIG_TEXT)){
            	 Node configParNode = getConfigParNode(contentNode);
            	 updateConfigTextListContent(rowIterator, configParNode);
            	 resp.getWriter().write("Total : " + nodesCreated + " ERROR nodes created." );
             }else if (fileName.startsWith(REWARDS)){
            	 
            	 message = HertzRewardsExcelImporter.executeIngestion(resourceResolver, resourceResolver.adaptTo(Session.class), pathToExcelInDam, contentPath);
            	 resp.getWriter().write(message);
            	 
             }
             else if (fileName.toLowerCase().contains(RESIDENCYCOUNTRY)){
            	 updateResidencyCountryContent(rowIterator, parNode);
            	 resp.getWriter().write("Total : " + nodesCreated + " residency nodes created." );
             }
			 
			 session.save();
			 session.logout();
		}catch (RepositoryException repoException) {
			LOGGER.error("RepositoryException Occured :  {}, {}", repoException, repoException.getStackTrace());
			resp.getWriter().write("Error Occured !! " + repoException.getMessage());
			
		} 
		LOGGER.debug("Exiting method importDataToContentNode of CountryStateListExcelImportServlet");
	}
	
	/**
	 * This method reads the rows in excel and creates country nodes in jcr
	 * @param rowIterator
	 * @param parNode
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	public void updateCountryListContent(Iterator<Row> rowIterator, Node parNode) throws PathNotFoundException, RepositoryException{
		LOGGER.debug("Entering method updateCountryListContent of CountryStateListExcelImportServlet");
		
		String countryCode = StringUtils.EMPTY;
		String countryName = StringUtils.EMPTY;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() > 0) {
				LOGGER.debug("Excel Row Found " + row.getRowNum() + "nodesCreated " + nodesCreated);
				if (row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL) != null) {
					Cell col0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					countryCode = col0.getStringCellValue();
					countryName = col1.getStringCellValue();
					int checkIfComponentNodeExists = HertzUtils.doesNodeExist(parNode, "countrylist"+"_"+row.getRowNum());
					if(checkIfComponentNodeExists == 0){
						Node componentNode = parNode.addNode("countrylist"+"_"+row.getRowNum());
						componentNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, HertzConstants.COMPONENT_COUNTRY_LIST);
						componentNode.setProperty(COUNTRY_CODE, countryCode);
						componentNode.setProperty(COUNTRY_NAME, countryName);
					}
					else{
						Node componentNode = parNode.getNode("countrylist"+"_"+row.getRowNum());
						componentNode.setProperty(COUNTRY_CODE, countryCode);
						componentNode.setProperty(COUNTRY_NAME, countryName);
					}
					nodesCreated++;
				}
			}
		}
		LOGGER.debug("Exiting method updateCountryListContent of CountryStateListExcelImportServlet");
	}
	
	/**
	 * This method reads the rows in excel and creates country nodes in jcr
	 * @param rowIterator
	 * @param parNode
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	public void updateResidencyCountryContent(Iterator<Row> rowIterator, Node parNode) throws PathNotFoundException, RepositoryException{
		LOGGER.debug("Entering method updateCountryListContent of CountryStateListExcelImportServlet");
		
		String countryCode = StringUtils.EMPTY;
		String countryName = StringUtils.EMPTY;
		String optIn = StringUtils.EMPTY;
		String defaultOptIn = StringUtils.EMPTY;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() > 0) {
				LOGGER.debug("Excel Row Found " + row.getRowNum() + "nodesCreated " + nodesCreated);
				if (row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL) != null) {
					Cell col0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col2 = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					//Cell col3 = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					countryCode = col0.getStringCellValue();
					countryName = col1.getStringCellValue();
					optIn = col2.getStringCellValue();
					
					//LOGGER.info("******************** OPTIN ROHIT" + col3.getBooleanCellValue());
					//defaultOptIn = col3.getStringCellValue().toLowerCase();
					int checkIfComponentNodeExists = HertzUtils.doesNodeExist(parNode, "residencycountrylist"+"_"+row.getRowNum());
					if(checkIfComponentNodeExists == 0){
						Node componentNode = parNode.addNode("residencycountrylist"+"_"+row.getRowNum());
						componentNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, HertzConstants.COMPONENT_RESIDENCY_COUNTRY_LIST);
						componentNode.setProperty(COUNTRY_CODE, countryCode);
						componentNode.setProperty(COUNTRY_NAME, countryName);
						componentNode.setProperty(OPTIN, optIn);
						//componentNode.setProperty(DEFAULTOPTIN, defaultOptIn);
					}
					else{
						Node componentNode = parNode.getNode("residencycountrylist"+"_"+row.getRowNum());
						componentNode.setProperty(COUNTRY_CODE, countryCode);
						componentNode.setProperty(COUNTRY_NAME, countryName);
						componentNode.setProperty(OPTIN, optIn);
						//componentNode.setProperty(DEFAULTOPTIN, defaultOptIn);
					}
					nodesCreated++;
				}
			}
		}
		LOGGER.debug("Exiting method updateResidencyCountryContent of CountryStateListExcelImportServlet");
	}
	/**
	 * This method reads the rows in excel and creates state nodes in jcr
	 * @param rowIterator
	 * @param parNode
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	public void updateStateListContent(Iterator<Row> rowIterator, Node parNode) throws PathNotFoundException, RepositoryException{
		LOGGER.debug("Entering method updateStateListContent of CountryStateListExcelImportServlet");
		
		String stateCode = StringUtils.EMPTY;
		String stateName = StringUtils.EMPTY;
		String type = StringUtils.EMPTY;
		String countryCode = StringUtils.EMPTY;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() > 0) {
				LOGGER.debug("Excel Row Found " + row.getRowNum() + "nodesCreated " + nodesCreated);
				if (row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL) != null) {
					Cell col0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col2 = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col3 = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					stateCode = col0.getStringCellValue();
					stateName = col1.getStringCellValue();
					type = col2.getStringCellValue();
					countryCode = col3.getStringCellValue();
					int checkIfComponentNodeExists = HertzUtils.doesNodeExist(parNode, "statelist"+"_"+row.getRowNum());
					if(checkIfComponentNodeExists == 0){
						Node componentNode = parNode.addNode("statelist"+"_"+row.getRowNum());
				        componentNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, HertzConstants.COMPONENT_STATE_LIST);
				        componentNode.setProperty(STATE_CODE, stateCode);
				        componentNode.setProperty(STATE_NAME, stateName);
				        componentNode.setProperty(TYPE, type);
				        componentNode.setProperty(COUNTRY_CODE, countryCode);
					}
					else{
						Node componentNode = parNode.getNode("statelist"+"_"+row.getRowNum());
						componentNode.setProperty(STATE_CODE, stateCode);
				        componentNode.setProperty(STATE_NAME, stateName);
				        componentNode.setProperty(TYPE, type);
				        componentNode.setProperty(COUNTRY_CODE, countryCode);
					}
					nodesCreated++;
				}
			}
		}
		LOGGER.debug("Exiting method updateStateListContent of CountryStateListExcelImportServlet");
	}
	
	/**
	 * This method reads the rows in excel and creates airline nodes in jcr
	 * @param rowIterator
	 * @param parNode
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	public void updateAirlineListContent(Iterator<Row> rowIterator, Node parNode) throws PathNotFoundException, RepositoryException{
		LOGGER.debug("Entering method updateAirlineListContent of CountryStateListExcelImportServlet");
		
		String airlineCode = StringUtils.EMPTY;
		String airlineName = StringUtils.EMPTY;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() > 0) {
				LOGGER.debug("Excel Row Found " + row.getRowNum() + "nodesCreated " + nodesCreated);
				if (row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL) != null) {
					Cell col0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					airlineCode = col0.getStringCellValue();
					airlineName = col1.getStringCellValue();						
					int checkIfComponentNodeExists = HertzUtils.doesNodeExist(parNode, "airlinetraincarrier"+"_"+row.getRowNum());
					if(checkIfComponentNodeExists == 0){
						Node componentNode = parNode.addNode("airlinetraincarrier"+"_"+row.getRowNum());
				        componentNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, HertzConstants.COMPONENT_AIRLINE_TRAIN_CARRIER);
				        componentNode.setProperty(AIRLINE_CODE, airlineCode);
				        componentNode.setProperty(AIRLINE_NAME, airlineName);
					}
					else{
						Node componentNode = parNode.getNode("airlinetraincarrier"+"_"+row.getRowNum());
				        componentNode.setProperty(AIRLINE_CODE, airlineCode);
				        componentNode.setProperty(AIRLINE_NAME, airlineName);
					}
					nodesCreated++;
				}
			}
		}
		LOGGER.debug("Exiting method updateAirlineListContent of CountryStateListExcelImportServlet");
	}
	
	/**
	 * This method gets the excel sheet and returns the rows iterator
	 * @param pathToExcel
	 * @return sheet iterator
	 */
	public Iterator<Row> getExcelRows(String pathToExcel) {
		LOGGER.debug("Entering method getExcelRows of CountryStateListExcelImportServlet");
		
		Node ntFileNode;
		try {
			ntFileNode = session
					.getNode(pathToExcel +PATH_TO_BINARY);
			Node ntResourceNode = ntFileNode.getNode("jcr:content");
			InputStream is = ntResourceNode.getProperty("jcr:data").getBinary().getStream();
			
				workbook = new XSSFWorkbook(is);
				is.close();
		} catch (PathNotFoundException pathNotFoundException) {
			LOGGER.error("PathNotFoundException Occured :  {}, {}", pathNotFoundException, pathNotFoundException.getStackTrace());
		} catch (RepositoryException repositoryException) {
			LOGGER.error("RepositoryException Occured :  {}, {}", repositoryException, repositoryException.getStackTrace());
		}catch (IOException iOException) {
			LOGGER.error("IOException Occured :  {}, {}", iOException, iOException.getStackTrace());
		}
		
		sheet = workbook.getSheetAt(0);
		
		LOGGER.debug("Exiting method getExcelRows of CountryStateListExcelImportServlet");
		return sheet.iterator();
	}
	
	/**
	 * This method reads the rows in excel and creates errormsg nodes in jcr
	 * @param rowIterator
	 * @param parNode
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	public void updateConfigTextListContent(Iterator<Row> rowIterator, Node parNode) throws PathNotFoundException, RepositoryException{
		LOGGER.debug("Entering method updateErrorMsgListContent of CountryStateListExcelImportServlet");
		
		String value1 = StringUtils.EMPTY;
		String value2 = StringUtils.EMPTY;
		String value3 = StringUtils.EMPTY;
		String value4 = StringUtils.EMPTY;
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() > 0) {
				LOGGER.debug("Excel Row Found " + row.getRowNum() + "nodesCreated " + nodesCreated);
				if (row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL) != null) {
					Cell col0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col2 = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col3 = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					value1 = col0.getStringCellValue();
					value2 = col1.getStringCellValue();
					value3 = col2.getStringCellValue();
					value4 = col3.getStringCellValue();
					int checkIfComponentNodeExists = HertzUtils.doesNodeExist(parNode, GENERIC_FIELD_PAIR+"_"+row.getRowNum());
					if(checkIfComponentNodeExists == 0){
						Node componentNode = parNode.addNode(GENERIC_FIELD_PAIR+"_"+row.getRowNum());
						componentNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, GENERIC_FIELD_PAIR);
						componentNode.setProperty(HertzConstants.KEY, value1);
						componentNode.setProperty(HertzConstants.LABEL, value2);
						componentNode.setProperty("ariaLabel", value3);
						componentNode.setProperty("defaultValue", value4);
					}
					else{
						Node componentNode = parNode.addNode(GENERIC_FIELD_PAIR+"_"+row.getRowNum());
						componentNode.setProperty(HertzConstants.KEY, value1);
						componentNode.setProperty(HertzConstants.LABEL, value2);
						componentNode.setProperty("ariaLabel", value3);
						componentNode.setProperty("defaultValue", value4);
					}
					nodesCreated++;
				}
			}
		}
		LOGGER.debug("Exiting method updateErrorMsgListContent of CountryStateListExcelImportServlet");
	}
	
	/**
	 * This method reads the rows in excel and creates errormsg nodes in jcr
	 * @param rowIterator
	 * @param parNode
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	public void updateErrorMsgListContent(Iterator<Row> rowIterator, Node parNode) throws PathNotFoundException, RepositoryException{
		LOGGER.debug("Entering method updateErrorMsgListContent of CountryStateListExcelImportServlet");
		
		String errorCode = StringUtils.EMPTY;
		String errorValue = StringUtils.EMPTY;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() > 0) {
				LOGGER.debug("Excel Row Found " + row.getRowNum() + "nodesCreated " + nodesCreated);
				if (row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL) != null) {
					Cell col0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell col1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					errorCode = col0.getStringCellValue();
					errorValue = col1.getStringCellValue();
					int checkIfComponentNodeExists = HertzUtils.doesNodeExist(parNode, ERROR+"_"+row.getRowNum());
					if(checkIfComponentNodeExists == 0){
						Node componentNode = parNode.addNode(ERROR+"_"+row.getRowNum());
						componentNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, HertzConstants.COMPONENT_ERROR_MSG);
						componentNode.setProperty(ERROR_CODE, errorCode);
						componentNode.setProperty(ERROR_VALUE, errorValue);
					}
					else{
						Node componentNode = parNode.getNode(ERROR+"_"+row.getRowNum());
						componentNode.setProperty(ERROR_CODE, errorCode);
						componentNode.setProperty(ERROR_VALUE, errorValue);
					}
					nodesCreated++;
				}
			}
		}
		LOGGER.debug("Exiting method updateErrorMsgListContent of CountryStateListExcelImportServlet");
	}
	/**
	 * This method checks if par node exists. If yes then returns the existing node
	 * else creates a new par node and returns the same.
	 * @param contentNode
	 * @return
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	public Node getParNode(Node contentNode) throws PathNotFoundException, RepositoryException{
		Node parNode = null;
		int checkIfParExists = HertzUtils.doesNodeExist(contentNode, HertzConstants.PAR);
		if(checkIfParExists == 0){
			parNode = contentNode.addNode(HertzConstants.PAR);
			parNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, HertzConstants.TOUCH_PARSYS_RES_TYPE);
		}
		else{
			parNode = contentNode.getNode(HertzConstants.PAR);
		}
		return parNode;
	}
	
	/**
	 * This method checks if Config par node exists. If yes then returns the existing node
	 * else creates a new par node and returns the same.
	 * @param contentNode
	 * @return
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 */
	public Node getConfigParNode(Node contentNode) throws PathNotFoundException, RepositoryException{
		Node parNode = null;
		int checkIfParExists = HertzUtils.doesNodeExist(contentNode, HertzConstants.CONFIG_TEXT_PARSYS);
		if(checkIfParExists == 0){
			parNode = contentNode.addNode(HertzConstants.CONFIG_TEXT_PARSYS);
			parNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, HertzConstants.TOUCH_PARSYS_RES_TYPE);
		}
		else{
			parNode = contentNode.getNode(HertzConstants.CONFIG_TEXT_PARSYS);
		}
		return parNode;
	}
	
	@Override
    /**
     * The GET Method uses the AEM QueryBuilder API to retrieve DAM Assets, places them in a ZIP and returns it
     * in the HTTP output stream
     */
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
       
     try
     {
        //Invoke the adaptTo method to create a Session
    	ResourceResolver resourceResolver = request.getResourceResolver();
        session = resourceResolver.adaptTo(Session.class);
        String fileNameContains = request.getParameter("name");
    
        // create query description as hash map (simplest way, same as form post)
        Map<String, String> map = new HashMap<String, String>();
      
        //set QueryBuilder search criteria                   
        map.put("type", "dam:Asset");
        map.put("path", "/content/dam/hertz-rac"); 
        map.put("property", "jcr:content/renditions/original/jcr:content/@jcr:mimeType");
        map.put("property.value", "application/vnd.ms-excel");
        map.put("fulltext", fileNameContains);
        
        builder= resourceResolver.adaptTo(QueryBuilder.class);
         
        //INvoke the Search query
        Query query = builder.createQuery(PredicateGroup.create(map), session);
         
        SearchResult sr= query.getResult();
         
        //write out to the AEM Log file
        LOGGER.debug("Search Results: " +sr.getTotalMatches() ) ;
         
        //Create a MAP to store results
        Map<String, InputStream> dataMap = new HashMap<String, InputStream>();
     
        // iterating over the results
        for (Hit hit : sr.getHits()) {
             
            //Convert the HIT to an asset - each asset will be placed into a ZIP for downloading
            String path = hit.getPath();
            Resource rs = resourceResolver.getResource(path);
            Asset asset = rs.adaptTo(Asset.class);   
               
            //We have the File Name and the inputstream
            InputStream data = asset.getOriginal().getStream();
            String name =asset.getName(); 
                         
           //Add to map
            dataMap.put(name, data); // key is fileName and value is inputStream - this will all be placed in ZIP file
       }
                    
        //ZIP up the AEM DAM Assets
        byte[] zip = zipFiles(dataMap);
         
        //
        // Sends the response back to the user / browser. The
        // content for zip file type is "application/zip". We
        // also set the content disposition as attachment for
        // the browser to show a dialog that will let user 
        // choose what action will he do to the sent content.
        //
         
        ServletOutputStream sos = response.getOutputStream();
         
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename="+fileNameContains+".zip");
         
         
        // Write bytes to tmp file.
        sos.write(zip);
        sos.flush(); 
        session.logout();
        LOGGER.debug("The ZIP is sent" ) ;    
     }
     catch(Exception e)
     {
    	 LOGGER.error("AN EXCEPTION OCCURED: " +e.getMessage() );
     }
   }
        
    /**
     * Create the ZIP with AEM DAM Assets.
     * @param data
     * @return
     * @throws IOException
     */
    private byte[] zipFiles(Map<String, InputStream> data) throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];
        Iterator<Map.Entry<String, InputStream>> entries = data.entrySet().iterator();
         
        while (entries.hasNext()) {
            Map.Entry<String, InputStream> entry = entries.next();
             
            String fileName =(String) entry.getKey(); 
            InputStream is1 =(InputStream) entry.getValue(); 
             
            BufferedInputStream bis = new BufferedInputStream(is1);
 
            //populate the next entry of the ZIP with the AEM DAM asset
            zos.putNextEntry(new ZipEntry(fileName));
 
            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
                
            }
            zos.closeEntry();
            bis.close();
            is1.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();
 
        return baos.toByteArray();
    }
}
