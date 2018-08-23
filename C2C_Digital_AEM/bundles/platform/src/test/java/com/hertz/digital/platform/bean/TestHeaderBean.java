package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import junitx.util.PrivateAccessor;


@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestHeaderBean {

	@InjectMocks
    private HeaderBean headerBean= new HeaderBean();
                
    private List<LoginItemsBean> login = new ArrayList<LoginItemsBean>();
    private List<SearchItemsBean> search = new ArrayList<SearchItemsBean>();
    private List<LogoItemsBean> logo = new ArrayList<LogoItemsBean>();

    @Mock
    LoginItemsBean loginItemsBean;
    
    @Mock
    SearchItemsBean searchItemsBean;
    
    @Mock
    LogoItemsBean logoItemsBean;
    
    @Mock
    MenuItemBean menuItemBean;
    
    @Mock
    ChatBean chatBean;
    
    @Mock
    FlyoutBean flyoutBean;
    
    @Mock
    LogoutBean logoutBean;

                
    @Mock
    List<LoginItemsBean> list=new ArrayList<LoginItemsBean>();

    
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoggerFactory.class);
        headerBean=PowerMockito.mock(HeaderBean.class);
        List<MenuItemBean> mockList1 = new ArrayList<MenuItemBean>();
        List<FlyoutBean> mockList2 = new ArrayList<FlyoutBean>();
        loginItemsBean=PowerMockito.mock(LoginItemsBean.class);
        loginItemsBean.setLoginBtnTxt("Login Btn Txt");
        loginItemsBean.setLoginPlaTxt("Login Pla Txt");
        PowerMockito.when(headerBean.getLoginItems()).thenReturn(login);
        PrivateAccessor.setField(headerBean, "login", list);
        searchItemsBean=PowerMockito.mock(SearchItemsBean.class);
        searchItemsBean.setSearchIconAltText("Search Icon Alt Txt");
        PowerMockito.when(headerBean.getsearchItems()).thenReturn(search);
        PrivateAccessor.setField(headerBean, "search", list);
        logoItemsBean=PowerMockito.mock(LogoItemsBean.class);
        logoItemsBean.setLogoImagealtText("Logo Image Txt");
        PowerMockito.when(headerBean.getLogoItems()).thenReturn(logo);
        PrivateAccessor.setField(headerBean, "logo", list);
        mockList1.add(menuItemBean);
        mockList2.add(flyoutBean);
        PowerMockito.doCallRealMethod().when(headerBean).setLoginItems(loginItemsBean);
        PowerMockito.doCallRealMethod().when(headerBean).getLoginItems();
        PowerMockito.doCallRealMethod().when(headerBean).setsearchItems(searchItemsBean);
        PowerMockito.doCallRealMethod().when(headerBean).getsearchItems();
        PowerMockito.doCallRealMethod().when(headerBean).setLogoItems(logoItemsBean);
        PowerMockito.doCallRealMethod().when(headerBean).getLogoItems();
        PowerMockito.doCallRealMethod().when(headerBean).setMenus(mockList1);
        PowerMockito.doCallRealMethod().when(headerBean).getMenus();
        PowerMockito.doCallRealMethod().when(headerBean).setFlyout(mockList2);
        PowerMockito.doCallRealMethod().when(headerBean).getFlyout();
        PowerMockito.doCallRealMethod().when(headerBean).setChat(chatBean);
        PowerMockito.doCallRealMethod().when(headerBean).getChat();
        PowerMockito.doCallRealMethod().when(headerBean).setLogout(logoutBean);
        PowerMockito.doCallRealMethod().when(headerBean).getLogout();
        PowerMockito.doCallRealMethod().when(headerBean).getLogin();
        PowerMockito.doCallRealMethod().when(headerBean).getLogo();
        headerBean.setLoginItems(loginItemsBean);
        headerBean.setsearchItems(searchItemsBean);
        headerBean.setLogoItems(logoItemsBean);
        headerBean.setMenus(mockList1);
        headerBean.setFlyout(mockList2);
        headerBean.setChat(chatBean);
        headerBean.setLogout(logoutBean);
    }
                
    @Test
    public final void test(){
    	Assert.assertNotNull(headerBean.getLoginItems());
    	Assert.assertNotNull(headerBean.getsearchItems());
    	Assert.assertNotNull(headerBean.getLogoItems());
    	Assert.assertNotNull(headerBean.getLogin());
    	Assert.assertNotNull(headerBean.getLogo());
    	Assert.assertNotNull(headerBean.getMenus());
    	Assert.assertNotNull(headerBean.getFlyout());
    	Assert.assertNotNull(headerBean.getChat());
    	Assert.assertNotNull(headerBean.getLogout());
    	
    }
}
