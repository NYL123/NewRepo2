package com.hertz.digital.platform.bean;

public class RewardsRowBean {
    private String identifier;
    private String resourceType = "hertz/components/content/rewardRowsComp";
    private String linkPath;
    private String linkTitle;
    private String cssIdentifier = "odd";
    private String city;
    private String endGroup = "false";
    private String startDateText;
    private String endDateText;

    /**
     * Default Constructor
     */
    public RewardsRowBean() {
        super();
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEndGroup() {
        return endGroup;
    }

    public void setEndGroup(String endGroup) {
        this.endGroup = endGroup;
    }

    public String getStartDateText() {
        return startDateText;
    }

    public void setStartDateText(String startDateText) {
        this.startDateText = startDateText;
    }

    public String getEndDateText() {
        return endDateText;
    }

    public void setEndDateText(String endDateText) {
        this.endDateText = endDateText;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCssIdentifier() {
        return cssIdentifier;
    }

    public void setCssIdentifier(String cssIdentifier) {
        this.cssIdentifier = cssIdentifier;
    }

}
