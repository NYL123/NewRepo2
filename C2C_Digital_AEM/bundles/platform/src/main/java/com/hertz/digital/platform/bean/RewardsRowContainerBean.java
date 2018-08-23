package com.hertz.digital.platform.bean;

import java.util.LinkedList;
import java.util.List;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class RewardsRowContainerBean {
	private String identifier;
	private String anchorid;
	private String resourceType = "hertz/components/content/rewardRowContainer";
	private String heading;
	private List<RewardsRowBean> rewardsRowList;
	private String tableParsysName = "tablePar";
	// values list
	private List<String> columnName;
	// values as json list
	private List<String> columnHeader;

	/**
	 * Default Constructor
	 */
	public RewardsRowContainerBean() {
		super();
	}
	
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public List<RewardsRowBean> getRewardsRowList() {
		return rewardsRowList;
	}

	public void setRewardsRowList(List<RewardsRowBean> rewardsRowList) {
		this.rewardsRowList = rewardsRowList;
	}

	public String getTableParsysName() {
		return tableParsysName;
	}

	public void setTableParsysName(String tableParsysName) {
		this.tableParsysName = tableParsysName;
	}

	public List<String> getColumnName() {
		return columnName;
	}

	public void setColumnName(List<String> columnName) {
		this.columnName = columnName;
	}

	public List<String> getColumnHeader() {
		return columnHeader;
	}

	public void setColumnHeader(List<String> columnHeader, String keyName) throws JSONException {
		List<String> jsonizedHeaders = new LinkedList<>();
		JSONObject jsonObj = null;
		for (int i = 0; i < columnHeader.size(); i++) {
			jsonObj = new JSONObject();
			jsonObj.put(keyName, columnHeader.get(i));			jsonizedHeaders.add(jsonObj.toString());
		}
		this.columnHeader = jsonizedHeaders;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getAnchorid() {
		return anchorid;
	}

	public void setAnchorid(String anchorid) {
		this.anchorid = anchorid;
	}

}
