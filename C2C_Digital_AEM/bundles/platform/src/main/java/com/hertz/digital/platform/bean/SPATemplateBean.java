package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Bean class for the SPA template Servlet
 * @author puneet.soni
 *
 */
public class SPATemplateBean {
	

	/**
	 * Default Constructor
	 */
	public SPATemplateBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SerializedName("html-fragments")
    @Expose
    private final List<HtmlFragment> htmlFragments= new  ArrayList<HtmlFragment>();

    public List<HtmlFragment> getHtmlFragments() {
        return htmlFragments;
    }

    public void setHtmlFragments(HtmlFragment htmlFragments) {
        this.htmlFragments.add(htmlFragments);
    }

	
}
