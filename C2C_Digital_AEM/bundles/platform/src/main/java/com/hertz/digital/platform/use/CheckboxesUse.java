package com.hertz.digital.platform.use;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hertz.digital.platform.bean.CheckBoxesLinkBean;
import com.hertz.digital.platform.utils.HertzUtils;

/**
 * Use class for displaying the fields the Multifield of the Checkboxes
 * Component in the HTML
 * 
 * @author puneet.soni
 *
 */
public class CheckboxesUse extends WCMUsePojo {

	/**
	 * Default Constructor Declaration
	 */
	public CheckboxesUse() {
		super();
	}

	/**
	 * LOGGER instantiation
	 */

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckboxesUse.class);
	private static final String LINKS = "links";

	/**
	 * Multi-field values are set in this list
	 */
	private List<CheckBoxesLinkBean> checkBoxBeans;

	/**
	 * Getter for List
	 * 
	 * @return List of CheckboxesBean
	 */
	public List<CheckBoxesLinkBean> getCheckBoxBeans() {
		return checkBoxBeans;
	}

	/**
	 * Activate method for CheckboxesUse
	 */
	@Override
	public void activate() throws Exception {
		LOGGER.debug("************* Entering activate method of CheckboxesUse **************");
		Gson gson = HertzUtils.initGsonBuilder(true, true, true);
		checkBoxBeans = new ArrayList<CheckBoxesLinkBean>();
		Node node = (Node) getResource().adaptTo(Node.class);

		Property checkboxesUseLinkProperty = null;
		if (node != null) {
			checkboxesUseLinkProperty = node.getProperty(LINKS);
		}
		if (null != checkboxesUseLinkProperty) {getChecboxProps(checkboxesUseLinkProperty, gson);}
		LOGGER.debug("************* Exiting activate method of CheckboxesUse **************");
	}

	private void getChecboxProps(Property checkboxesUseLinkProperty, Gson gson) {


		try {
			if (checkboxesUseLinkProperty.isMultiple()) {
				Value[] checkboxesUseLinkPropertyValues = checkboxesUseLinkProperty.getValues();
				multiCheckBoxProp(checkboxesUseLinkPropertyValues, gson);
			} else {
				Value checkboxesUseLinkPropertyValues = checkboxesUseLinkProperty.getValue();
				CheckBoxesLinkBean checkboxesBean = gson.fromJson(checkboxesUseLinkPropertyValues.toString(),
						CheckBoxesLinkBean.class);
				if (!StringUtils.isEmpty(checkboxesBean.getElement())) {
					checkBoxBeans.add(checkboxesBean);
				}
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			LOGGER.error("JsonSyntaxException: {} : {}", e.getMessage(), e);
		} catch (ValueFormatException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ValueFormatException: {} : {}", e.getMessage(), e);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOGGER.error("RepositoryException: {} : {}", e.getMessage(), e);
		}
	
		
	}

	private void multiCheckBoxProp(Value[] checkboxesUseLinkPropertyValues, Gson gson) {
		// TODO Auto-generated method stub
		for (Value value : checkboxesUseLinkPropertyValues) {
			CheckBoxesLinkBean checkboxesBean = gson.fromJson(value.toString(), CheckBoxesLinkBean.class);
			if (!StringUtils.isEmpty(checkboxesBean.getElement())) {
				checkBoxBeans.add(checkboxesBean);
			}
		}
		
	}
	


}
