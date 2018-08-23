package com.hertz.digital.platform.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class RewardsDatum.
 */
public class RewardsDatum {

    /** The title. */
    @SerializedName("title")
    @Expose
    private String title;

    /** The description. */
    @SerializedName("description")
    @Expose
    private String description;

    /** The points. */
    @SerializedName("points")
    @Expose
    private String points;

    /** The points text. */
    @SerializedName("pointsText")
    @Expose
    private String pointsText;

    /**
     * Instantiates a new rewards datum.
     */
    public RewardsDatum() {
        super();
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the points.
     *
     * @return the points
     */
    public String getPoints() {
        return points;
    }

    /**
     * Sets the points.
     *
     * @param points
     *            the new points
     */
    public void setPoints(String points) {
        this.points = points;
    }

    /**
     * Gets the points text.
     *
     * @return the points text
     */
    public String getPointsText() {
        return pointsText;
    }

    /**
     * Sets the points text.
     *
     * @param pointsText
     *            the new points text
     */
    public void setPointsText(String pointsText) {
        this.pointsText = pointsText;
    }

}
