package com.hertz.digital.platform.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Logout JavaBean.
 *
 * @author deepak.parma
 */
public class LogoutBean {

    /**
     * Default Constructor.
     */
    public LogoutBean() {
        super();
    }

    /** The logout button text. */
    @SerializedName("title")
    private String logoutButtonText;

    /** The logout URL. */
    @SerializedName("targetLink")
    private String logoutURL;

    /**
     * Gets the logout button text.
     *
     * @return the logout button text
     */
    public String getLogoutButtonText() {
        return logoutButtonText;
    }

    /**
     * Sets the logout button text.
     *
     * @param logoutButtonText
     *            the new logout button text
     */
    public void setLogoutButtonText(String logoutButtonText) {
        this.logoutButtonText = logoutButtonText;
    }

    /**
     * Gets the logout URL.
     *
     * @return the logout URL
     */
    public String getLogoutURL() {
        return logoutURL;
    }

    /**
     * Sets the logout URL.
     *
     * @param logoutURL
     *            the new logout URL
     */
    public void setLogoutURL(String logoutURL) {
        this.logoutURL = logoutURL;
    }

}
