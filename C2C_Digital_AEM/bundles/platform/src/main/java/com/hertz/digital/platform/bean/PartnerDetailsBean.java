package com.hertz.digital.platform.bean;

public class PartnerDetailsBean {

	/**
	 * Default constructor 
	 * */
	public PartnerDetailsBean(){
		super();
	}
	
	/**
	 * Variable for name of partner
	 * */
	private String partnerName;
    private String partner;
    private ImageInfoBean partnerImage;

    /**
     * @return the partnerImage
     */
    public ImageInfoBean getPartnerImage() {
        return partnerImage;
    }

    /**
     * @param sources
     *            the partnerImage to set
     */
    public void setPartnerImage(ImageInfoBean sources) {
        this.partnerImage = sources;
    }

    /**
     * @return the partnerName
     */
	public String getPartnerName() {
		return partnerName;
	}
	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
