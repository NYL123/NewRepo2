/*
 * 
 */
package com.hertz.digital.platform.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class BreadcrumbBean.
 */
public class BreadcrumbBean {

    /** The label. */
    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("URI")
    @Expose
    private String URI;

    /**
     * Default Constructor
     */
    public BreadcrumbBean() {
        super();
    }

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
     * @return the uRI
     */
    public String getURI() {
        return URI;
    }

    /**
     * @param uRI
     *            the uRI to set
     */
    public void setURI(String uRI) {
        URI = uRI;
    }

}
