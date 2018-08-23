package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Java Bean for Search in Header
 * 
 * @author himanshu.i.sharma
 */
public class HeaderBean {
    /**
     * Default Constructor
     */
    public HeaderBean() {
        super();
    }

    /**
     * List of Heading in the menus
     */
    @SerializedName("menus")
    private List<MenuItemBean> menus = new ArrayList<MenuItemBean>();

    /**
     * List of Heading for Search
     */
    @SerializedName("search")
    private final List<
            SearchItemsBean> search = new ArrayList<SearchItemsBean>();

    /**
     * Chat Item
     */
    @SerializedName("chat")
    private ChatBean chat;

    /**
     * Logout Item
     */
    @SerializedName("logout")
    private LogoutBean logout;

    /**
     * List of Heading for Logo
     */
    @SerializedName("logo")
    private final List<LogoItemsBean> logo = new ArrayList<LogoItemsBean>();

    /**
     * List of Heading for Login
     */
    @SerializedName("login")
    private final List<LoginItemsBean> login = new ArrayList<LoginItemsBean>();

    /**
     * Flyout Item
     */
    @SerializedName("flyout")
    List<FlyoutBean> flyoutList = new ArrayList<>();

    /**
     * @return the login
     */
    public List<LoginItemsBean> getLogin() {
        return login;
    }

    /**
     * Getter of the Search Items
     * 
     * @return
     */
    public List<SearchItemsBean> getsearchItems() {
        return search;
    }

    /**
     * Setter of the Search Items
     * 
     * @param menuItems
     */
    public void setsearchItems(SearchItemsBean searchItems) {
        search.add(searchItems);
    }

    /**
     * Getter of the Logo List
     * 
     * @return
     */
    public List<LogoItemsBean> getLogoItems() {
        return logo;
    }

    /**
     * Setter of the Logo List
     * 
     * @param menuItems
     */
    public void setLogoItems(LogoItemsBean logoItems) {
        logo.add(logoItems);
    }

    /**
     * Getter of the Login List
     * 
     * @return
     */
    public List<LoginItemsBean> getLoginItems() {
        return login;
    }

    /**
     * Setter of the Login List
     * 
     * @param menuItems
     */
    public void setLoginItems(LoginItemsBean loginItems) {
        login.add(loginItems);
    }

    /**
     * Getter for List of MenuItemBean
     * 
     * @return List of MenuItemBean That is the Bean that contains all
     *         properties of Menu
     */
    public List<MenuItemBean> getMenus() {
        return menus;
    }

    /**
     * Setter for List of MenuItemBean
     * 
     * @param menus
     *            List of MenuItemBean That is the Bean that contains all
     *            properties of Menu
     */
    public void setMenus(List<MenuItemBean> menus) {
        this.menus = menus;
    }

    /**
     * Getter for Chat
     * 
     * @return ChatBean ChatBean contains the properties of chat in Header
     */
    public ChatBean getChat() {
        return chat;
    }

    /**
     * Setter for Chat
     * 
     * @param chat
     *            chat contains the properties of chat in Header
     */
    public void setChat(ChatBean chat) {
        this.chat = chat;
    }

    /**
     * Getter of the Logo List
     * 
     * @return the logo
     */
    public List<LogoItemsBean> getLogo() {
        return logo;
    }

    /**
     * Setter of the Flyout List
     * 
     * @param the
     *            Flyout
     */

    public List<FlyoutBean> getFlyout() {
        return flyoutList;
    }

    /**
     * Getter of the Flyout List
     * 
     * @return the Flyout
     */

    public void setFlyout(List<FlyoutBean> flyoutList) {
        this.flyoutList = flyoutList;
    }

    /**
     * @return the logout
     */
    public LogoutBean getLogout() {
        return logout;
    }

    /**
     * @param logout
     *            the logout to set
     */
    public void setLogout(LogoutBean logout) {
        this.logout = logout;
    }

}
