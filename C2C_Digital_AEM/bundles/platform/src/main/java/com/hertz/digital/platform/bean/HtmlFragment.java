
package com.hertz.digital.platform.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Bean class for the SPA Template 
 * @author puneet.soni
 *
 */
public class HtmlFragment {

    /**
	 * Default Constructors
	 */
	public HtmlFragment() {
		super();
	}

	@SerializedName("name")
    @Expose
    private String name="";
    @SerializedName("html")
    @Expose
    private String html="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

}
