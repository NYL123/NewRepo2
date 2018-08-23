package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Java Bean for menu in HeaderBean
 * 
 * @author puneet.soni
 */
public class MenuBean {

	/**
	 * Default Constructors
	 */
	public MenuBean() {
		super();
	}

	/**
	 * List of Heading in the menus
	 */
	@SerializedName("menus")
	private final List<MenuItemBean> menus = new ArrayList<MenuItemBean>();

	/**
	 * Getter of the Menu List
	 * 
	 * @return List of MenuItemBean
	 */
	public List<MenuItemBean> getMenu() {
		return menus;
	}

	/**
	 * Setter of the menu List
	 * 
	 * @param menuItem
	 *            Object of MenuItemBean That contains the properties of Menu
	 */
	public void setMenu(MenuItemBean menuItem) {
		menus.add(menuItem);
	}
}
