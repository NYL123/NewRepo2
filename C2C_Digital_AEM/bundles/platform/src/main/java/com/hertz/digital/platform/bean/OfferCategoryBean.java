package com.hertz.digital.platform.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Bean class for OfferCategoryUse
 * 
 * @author puneet.soni
 *
 */
public class OfferCategoryBean {

    /**
     * Default Constructor
     */
    public OfferCategoryBean() {
        super();
    }

    /**
     * Holds the value of Rank
     */
    private String rank;

    /**
     * Holds the value of headline
     */
    @SerializedName("headlineFirstLine")
    private String headline;

    /**
     * Holds the value of headlineSecondLine
     */
    private String headlineSecondLine;
    /**
     * Holds the value of subhead
     */
    String subhead;

    /**
     * Holds the value of badge
     */
    ImageInfoBean badge;

    /**
     * Holds the value of logo
     */
    ImageInfoBean logo;

    /**
     * Holds the value of image
     */
    ImageInfoBean image;

    /**
     * Holds the value of ctaLabel
     */
    String ctaLabel;

    /**
     * Holds the value of ctaAction
     */
    @SerializedName("ctaHref")
    String ctaAction;
    
    /**
     * Constant for mapping of category with db2Category
     * */
    String db2Category;
    
    /**
     * The path of the category
     * */
    String contentPath;

    /**
     * List of offers associated with a category
     */
    private List<OfferDetailsBean> offers;

    /**
     * Getter for Badge
     * 
     * @return Bean that contains info about Badge
     */
    public ImageInfoBean getBadge() {
        return badge;
    }

    /**
     * Setter for Badge
     * 
     * @param badge
     *            Bean that contains info about Badge
     */
    public void setBadge(ImageInfoBean badge) {
        this.badge = badge;
    }

    /**
     * Getter for Logo
     * 
     * @return Bean that contains info about Logo
     */
    public ImageInfoBean getLogo() {
        return logo;
    }

    /**
     * setter for logo
     * 
     * @param logo
     *            Bean that contains info about Logo
     */
    public void setLogo(ImageInfoBean logo) {
        this.logo = logo;
    }

    /**
     * Getter for Image
     * 
     * @return Bean that contains info about Image
     */
    public ImageInfoBean getImage() {
        return image;
    }

    /**
     * Setter for Image
     * 
     * @param image
     *            String that contains image
     */
    public void setImage(ImageInfoBean image) {
        this.image = image;
    }

    /**
     * Getter for Rank
     * 
     * @return String that contains Rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Setter for Rank
     * 
     * @param rank
     *            String that contains Rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * Getter for Headline
     * 
     * @return String that contains headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * Setter for headline
     * 
     * @param headline
     *            String that contains headline
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * Getter for subhead
     * 
     * @return String that contains subhead
     */
    public String getSubhead() {
        return subhead;
    }

    /**
     * Setter for SubHead
     * 
     * @param subhead
     *            String that contains subhead
     */
    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    /**
     * Getter for CTALabel
     * 
     * @return String that contains ctaLabel
     */
    public String getCtaLabel() {
        return ctaLabel;
    }

    /**
     * Setter for CTALabel
     * 
     * @param ctaLabel
     *            String that contains ctaLabel
     */
    public void setCtaLabel(String ctaLabel) {
        this.ctaLabel = ctaLabel;
    }

    /**
     * Getter for CTA Action
     * 
     * @return String that contains ctaAction
     */
    public String getCtaAction() {
        return ctaAction;
    }

    /**
     * Setter for ctaAction
     * 
     * @param ctaAction
     *            String that contains ctaAction
     */
    public void setCtaAction(String ctaAction) {
        this.ctaAction = ctaAction;
    }

    /**
     * @return the offers
     */
    public List<OfferDetailsBean> getOffers() {
        return offers;
    }

    /**
     * @param offers
     *            the offers to set
     */
    public void setOffers(List<OfferDetailsBean> offers) {
        this.offers = offers;
    }

    /**
     * @return the headlineSecondLine
     */
    public String getHeadlineSecondLine() {
        return headlineSecondLine;
    }

    /**
     * @param headlineSecondLine
     *            the headlineSecondLine to set
     */
    public void setHeadlineSecondLine(String headlineSecondLine) {
        this.headlineSecondLine = headlineSecondLine;
    }

	/**
	 * @return
	 */
	public String getDb2Category() {
		return db2Category;
	}

	/**
	 * @param db2Category
	 */
	public void setDb2Category(String db2Category) {
		this.db2Category = db2Category;
	}

	/**
	 * @return
	 */
	public String getContentPath() {
		return contentPath;
	}

	/**
	 * @param contentPath
	 */
	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

}
