package com.hertz.digital.platform.workflows;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.crx.JcrConstants;
import com.hertz.digital.platform.bean.RewardsRowBean;
import com.hertz.digital.platform.bean.RewardsRowContainerBean;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.utils.HertzUtils;


public class HertzRewardsExcelImporter{
	

	/**
	 * Default Constructor
	 */
	public HertzRewardsExcelImporter() {
		super();
	}

	private static final String BASEPATH = "basepath";

	private static final Logger log = LoggerFactory.getLogger(HertzImageRenditionProcess.class);

	private static int cityCounter;
	private static String cssIdentifier = "odd";
	

		
	public static String executeIngestion(ResourceResolver resolver, Session session, String excelPath, String ingestionPath) {

		//Session session = workflowSession.getSession();
		//String basePath = args.get(BASEPATH, "/content/dam/hertz-rac/rewards-import-excel");
		//ResourceResolver resolver = null;
		String response = "Successfully Imported";
		try {
			//resolver = rFactory.getServiceResourceResolver();
			if (StringUtils.isNotEmpty(excelPath) && null != resolver) {
				//String excelPath = getLatestExcelPath(resolver, basePath);
				//String ingestionPath = workItem.getWorkflowData().getPayload().toString();
				Map<String, RewardsRowContainerBean> tbiMap = processExcel(excelPath, ingestionPath, session);

				//perform clean operation initially
				Resource parentResource = resolver.getResource(ingestionPath);
				if (null != parentResource) {
					Page parentPage = parentResource.adaptTo(Page.class);
					if (null != parentPage) {
						Resource gridResource = parentPage.getContentResource().getChild("responsivegrid");
						HertzUtils.clean(gridResource, resolver, "hertz/components/content/rewardRowContainer");
					}
				}
				ingestData(tbiMap, ingestionPath, resolver, session);
			}
		} catch (RepositoryException | PersistenceException e) {
			response = "Error occured " + e.getMessage() + ". Please try again";
			log.error("Error while import: - {}.", e);
		} 
		return response;
	}

	/**
	 * This method takes care of the ingestion of the passed map and underlying
	 * nodes.
	 * 
	 * @param tbiMap
	 *            The map containing all the nodes to be ingested.
	 * @param ingestionPath
	 *            The ingestion page path.
	 * @param resolver
	 *            The resolver object.
	 * @param session
	 *            The session object.
	 * 
	 * @throws PersistenceException
	 *             The exception.
	 * @throws RepositoryException
	 *             The repository exception.
	 */
	private static void ingestData(Map<String, RewardsRowContainerBean> tbiMap, String ingestionPath,
			ResourceResolver resolver, Session session) throws PersistenceException, RepositoryException {
		for (Map.Entry<String, RewardsRowContainerBean> entry : tbiMap.entrySet()) {
			Resource parentResource = resolver.getResource(ingestionPath);
			if (null != parentResource) {
				Page parentPage = parentResource.adaptTo(Page.class);
				if (null != parentPage) {
					Resource gridResource = parentPage.getContentResource().getChild("responsivegrid");
					ingest(gridResource, entry.getValue(), resolver, session);
					session.save();
				}
			} else {
				log.debug("The ingestionPath does not exist in AEM. Please create the parent page first.");
			}
		}
	}

	/**
	 * This method creates the node structure under the responsive grid and
	 * commits the response.
	 * 
	 * @param gridResource
	 *            The grid resource.
	 * @param container
	 *            The container to be created.
	 * @param resolver
	 *            The resolver object.
	 * @param session
	 *            The session object.
	 * @throws RepositoryException
	 *             THe eception thrown.
	 */
	private static void ingest(Resource gridResource, RewardsRowContainerBean container, ResourceResolver resolver,
			Session session) throws RepositoryException {
		Node containerNode = JcrUtils.getOrCreateByPath(
				gridResource.getPath() + HertzConstants.FRWD_SLASH + container.getIdentifier(),
				JcrConstants.NT_UNSTRUCTURED, session);
		if (null != containerNode) {
			containerNode.setProperty("columnheader",
					container.getColumnHeader().toArray(new String[container.getColumnHeader().size()]));
			containerNode.setProperty("columnname",
					container.getColumnName().toArray(new String[container.getColumnName().size()]));
			containerNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, container.getResourceType());
			containerNode.setProperty("heading", container.getHeading());
			containerNode.setProperty("anchorid", container.getAnchorid());

			Node parsys = JcrUtils.getOrCreateByPath(containerNode.getPath() + HertzConstants.FRWD_SLASH + "tablePar",
					JcrConstants.NT_UNSTRUCTURED, session);
			parsys.setProperty("sling:resourceType", "wcm/foundation/components/parsys");

			if (null != parsys) {
				List<RewardsRowBean> rowBeanList = container.getRewardsRowList();
				Iterator<RewardsRowBean> iterator = rowBeanList.iterator();
				while (iterator.hasNext()) {
					RewardsRowBean row = iterator.next();
					Node rowNode = parsys.addNode(row.getIdentifier(), JcrConstants.NT_UNSTRUCTURED);
					rowNode.setProperty(HertzConstants.SLING_RESOURCE_TYPE, row.getResourceType());
					if (StringUtils.isNotEmpty(row.getCity())) {
						rowNode.setProperty("city", row.getCity());
					}
					if (StringUtils.isNotEmpty(row.getLinkTitle())) {
						rowNode.setProperty("linkTitle", row.getLinkTitle());
					}
					if (StringUtils.isNotEmpty(row.getLinkPath())) {
						rowNode.setProperty("linkPath", row.getLinkPath());
					}
					rowNode.setProperty("endGroup", row.getEndGroup());
					rowNode.setProperty("cssIdentifier", row.getCssIdentifier());
					
					if (StringUtils.isNotEmpty(row.getStartDateText())) {
						rowNode.setProperty("startDateText", HertzUtils.dateFormatter(row.getStartDateText()));
					}
					if (StringUtils.isNotEmpty(row.getEndDateText())) {
						rowNode.setProperty("endDateText", HertzUtils.dateFormatter(row.getEndDateText()));
					}
				}
			}
		}
	}

	/**
	 * Removes the content package.
	 *
	 * @param session
	 *            the session
	 * @param nodePath
	 *            the node path
	 */
	private static final String getLatestExcelPath(ResourceResolver resolver, String basePath) {

		Map<Long, String> packageMap = new TreeMap<>();
		List<Map.Entry<Long, String>> revSortedPackage = null;
		Resource resource = resolver.getResource(basePath);
		if (null != resource) {
			Iterable<Resource> resourceChildren = resource.getChildren();
			for (Resource childResource : resourceChildren) {
				if (!HertzConstants.JCR_CONTENT.equals(childResource.getName())) {
					long creationTime = ((GregorianCalendar) childResource.getValueMap().get(JcrConstants.JCR_CREATED))
							.getTimeInMillis();

					log.debug("The creation Time of resource {} is {} :-", childResource.getPath(), creationTime);
					if (creationTime > 1) {
						packageMap.put(creationTime, childResource.getPath());
						log.debug("resource at {} is added to the map", childResource.getPath());
					}
				}
			}
		}
		log.debug("No. of excel sheets in the system: {}", packageMap.size());
		revSortedPackage = sortDescendingByKey(packageMap);

		return revSortedPackage.get(0).getValue();
	}

	/**
	 * <p>
	 * This method processes the uploaded excel and unmarshalls each row in the
	 * spread sheet in a custom POJO. Hence, complete spreadsheet is
	 * unmarshalled into a list of POJO's which is processed for page creation.
	 * </p>
	 *
	 * @param filePath
	 *            the file path {@link String}
	 * @param the
	 *            session {@link Session}
	 * @return the list< product page> {@link List}
	 */
	private static Map<String, RewardsRowContainerBean> processExcel(String filePath, String ingestionPath, Session session) {
		log.debug("Inside method: processExcel()");

		Map<String, RewardsRowContainerBean> containerMap = new LinkedHashMap<>();

		try (InputStream file = HertzUtils.readFileNode(filePath, session)) {
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// loop sheets
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				RewardsRowContainerBean container = new RewardsRowContainerBean();
				container.setIdentifier("rewardrowcontainer_" + HertzUtils.generateRandom());

				XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

				container.setAnchorid(StringUtils.replace(sheet.getSheetName(), " ", "_"));
				// Iterate through each rows one by one
				Iterator<Row> rowIterator = sheet.iterator();
				List<RewardsRowBean> rewardsRowList = new LinkedList<>();
				RewardsRowBean prevRowBean = null;
				prevRowBean = iterateRow(container, rowIterator, rewardsRowList, prevRowBean);
				if (null != prevRowBean) {
					// toggling for the last element
					prevRowBean.setEndGroup("true");
					rewardsRowList.add(prevRowBean);
				}
				cityCounter = 0;
				cssIdentifier = "odd";
				container.setRewardsRowList(rewardsRowList);
				if (log.isDebugEnabled()) {
					log.debug("No. of containers to be created for {} state is {}", sheet.getSheetName(),
							container.getRewardsRowList().size());
				}
				// using anchor-id as key, since sheet name can't be duplicate.
				containerMap.put(container.getAnchorid(), container);
			}
		} catch (IOException | JSONException e) {
			log.error("Error occured while processing excel sheet {} {}", e, e.getStackTrace());
		} finally {
			cityCounter = 0;
			cssIdentifier = "odd";
		}
		log.debug("Exiting method: processExcel()");
		return containerMap;
	}

	/**
	 * Iterate Rows
	 * @param rowContainerBean
	 * @param rowIterator
	 * @param rewardsRowList
	 * @param rewardsRowBean
	 * @return
	 * @throws JSONException
	 */
	private static RewardsRowBean iterateRow(RewardsRowContainerBean rowContainerBean, Iterator<Row> rowIterator,
			List<RewardsRowBean> rewardsRowList, RewardsRowBean rewardsRowBean) throws JSONException {
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			switch (row.getRowNum()) {
			case 0:
				// set the header test in container from cell 2
				// set the row data in rowbean
				Iterator<Cell> cell0Iterator = row.cellIterator();
				while (cell0Iterator.hasNext()) {
					Cell cell = cell0Iterator.next();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					switch (cell.getColumnIndex()) {
					case 1:
						rowContainerBean.setHeading(StringUtils.trim(cell.getStringCellValue()));
						break;
					default:
						break;

					}
				}
				break;
			case 1:
				List<String> columnHeader = new LinkedList<>();
				// set the columns in the container
				Iterator<Cell> cell1Iterator = row.cellIterator();
				while (cell1Iterator.hasNext()) {
					Cell cell = cell1Iterator.next();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					switch (cell.getColumnIndex()) {
					case 2:
						break;
					default:
						columnHeader.add(StringUtils.trim(cell.getStringCellValue()));
						break;
					}
				}
				rowContainerBean.setColumnHeader(columnHeader, "columnname");
				rowContainerBean.setColumnName(columnHeader);

				break;
			default:

				RewardsRowBean rowBean = null;
				// set the row data in rowbean
				Iterator<Cell> cellDefIterator = row.cellIterator();
				rowBean = new RewardsRowBean();
				rowBean.setIdentifier("rewardrowscomp_" + HertzUtils.generateRandom());
				while (cellDefIterator.hasNext()) {
					String inputFormat = "E MMM dd HH:mm:ss Z yyyy";
					String outputFormat = "yyyy-MM-dd'T'HH:mm:ssZZZ";
					Cell cell = cellDefIterator.next();
					switch (cell.getColumnIndex()) {
					case 0:
						rowBean.setCity(StringUtils.trim(cell.getStringCellValue()));
						break;
					case 1:
						rowBean.setLinkTitle(StringUtils.trim(cell.getStringCellValue()));
						break;
					case 2:
						rowBean.setLinkPath(StringUtils.trim(cell.getStringCellValue()));
						break;
					case 3:
						if (null != cell.getDateCellValue()) {
							rowBean.setStartDateText(HertzUtils.formatDate(cell.getDateCellValue().toString(),
									inputFormat, outputFormat));
						}

						break;
					case 4:

						if (null != cell.getDateCellValue()) {
							rowBean.setEndDateText(HertzUtils.formatDate(cell.getDateCellValue().toString(),
									inputFormat, outputFormat));
						}
						break;
					default:
						break;
					}
				}
				
				if(StringUtils.isNotEmpty(rowBean.getCity())){
					updateCssIdentifier();
				}
				rowBean.setCssIdentifier(cssIdentifier);
				
				if (null != rewardsRowBean) {
					if (StringUtils.isNotEmpty(rowBean.getCity())) {
						rewardsRowBean.setEndGroup("true");
					}
					rewardsRowList.add(rewardsRowBean);
				}

				rewardsRowBean = rowBean;

				break;
			}
		}
		return rewardsRowBean;
	}

	private static void updateCssIdentifier() {
		cityCounter++;
		if(cityCounter % 2 == 0){
			cssIdentifier = "even";
		} else {
			cssIdentifier = "odd";
		}
		
	}
	
	/**
	 * This method sorts the treemap provided in to descending order by key and
	 * returns a list of reverse sorted entry sets.
	 * 
	 * @param map
	 *            The provided map.
	 * @return The list of entry sets in desc. sorted order.
	 */
	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> sortDescendingByKey(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}
}
