package com.hertz.digital.platform.bean;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Reward.
 *
 * @author n.kumar.singhal
 */
public class Reward {

    /** The title. */
    @SerializedName("title")
    @Expose
    private String title;

    /** The rewards data. */
    @SerializedName("rewardsData")
    @Expose
    private List<RewardsDatum> rewardsData;

    /**
     * Instantiates a new reward.
     */
    public Reward() {
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
     * Gets the rewards data.
     *
     * @return the rewards data
     */
    public List<RewardsDatum> getRewardsData() {
        return rewardsData;
    }

    /**
     * Sets the rewards data.
     *
     * @param rewardsData
     *            the new rewards data
     */
    public void setRewardsData(List<RewardsDatum> rewardsData) {
        this.rewardsData = rewardsData;
    }

}
