package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * The Class HertzLinkDropdownBean.
 *
 * @author a.dhingra
 */
public class HertzLinkDropdownBean {

    /**
     * Default constructor.
     */
    public HertzLinkDropdownBean() {
        super();
    }

    /** The label. */
    private String label;

    /** The link type. */
    @SerializedName("LinkType")
    private String linkType;

    /** The sequence id. */
    @SerializedName("id")
    private String sequenceId;

    /** The target type. */
    @SerializedName("TargetType")
    private String targetType;

    /** The cdp code. */
    @SerializedName("CDP")
    private String cdpCode;

    /** The rq code. */
    @SerializedName("RQ")
    private String rqCode;

    /** The pc code. */
    @SerializedName("PC")
    private String pcCode;

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the linkType
     */
    public String getLinkType() {
        return linkType;
    }

    /**
     * @param linkType
     *            the linkType to set
     */
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    /**
     * @return the sequenceId
     */
    public String getSequenceId() {
        return sequenceId;
    }

    /**
     * @param sequenceId
     *            the sequenceId to set
     */
    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    /**
     * @return the targetType
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * @param targetType
     *            the targetType to set
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    /**
     * @return the cdpCode
     */
    public String getCdpCode() {
        return cdpCode;
    }

    /**
     * @param cdpCode
     *            the cdpCode to set
     */
    public void setCdpCode(String cdpCode) {
        this.cdpCode = cdpCode;
    }

    /**
     * @return the rqCode
     */
    public String getRqCode() {
        return rqCode;
    }

    /**
     * @param rqCode
     *            the rqCode to set
     */
    public void setRqCode(String rqCode) {
        this.rqCode = rqCode;
    }

    /**
     * @return the pcCode
     */
    public String getPcCode() {
        return pcCode;
    }

    /**
     * @param pcCode
     *            the pcCode to set
     */
    public void setPcCode(String pcCode) {
        this.pcCode = pcCode;
    }
}
