package com.hertz.digital.platform.bean;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class RedemptionData.
 *
 * @author n.kumar.singhal
 */
public class RedemptionData {

    /** The country name. */
    @SerializedName("countryName")
    @Expose
    private String countryName;

    /** The reward types. */
    @SerializedName("rewardTypes")
    @Expose
    private List<RewardType> rewardTypes;

    /**
     * Instantiates a new redemption data.
     */
    public RedemptionData() {
        super();
    }

    /**
     * Gets the country name.
     *
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the country name.
     *
     * @param countryName
     *            the new country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets the reward types.
     *
     * @return the reward types
     */
    public List<RewardType> getRewardTypes() {
        return rewardTypes;
    }

    /**
     * Sets the reward types.
     *
     * @param rewardTypes
     *            the new reward types
     */
    public void setRewardTypes(List<RewardType> rewardTypes) {
        this.rewardTypes = rewardTypes;
    }

}
