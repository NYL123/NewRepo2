package com.hertz.digital.platform.bean;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class RewardType.
 */
public class RewardType {

    /** The reward type. */
    @SerializedName("rewardType")
    @Expose
    private String rewardType;

    /** The reward description. */
    @SerializedName("rewardDescription")
    @Expose
    private String rewardDescription;

    /** The rewards. */
    @SerializedName("rewards")
    @Expose
    private List<Reward> rewards;

    /**
     * Instantiates a new reward type.
     */
    public RewardType() {
        super();
    }

    /**
     * Gets the reward type.
     *
     * @return the reward type
     */
    public String getRewardType() {
        return rewardType;
    }

    /**
     * Sets the reward type.
     *
     * @param rewardType
     *            the new reward type
     */
    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    /**
     * Gets the reward description.
     *
     * @return the reward description
     */
    public String getRewardDescription() {
        return rewardDescription;
    }

    /**
     * Sets the reward description.
     *
     * @param rewardDescription
     *            the new reward description
     */
    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    /**
     * Gets the rewards.
     *
     * @return the rewards
     */
    public List<Reward> getRewards() {
        return rewards;
    }

    /**
     * Sets the rewards.
     *
     * @param rewards
     *            the new rewards
     */
    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

}
