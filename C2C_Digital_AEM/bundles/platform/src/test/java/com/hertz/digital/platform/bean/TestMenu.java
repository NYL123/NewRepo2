package com.hertz.digital.platform.bean;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import junitx.util.PrivateAccessor;


@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestMenu {

	@InjectMocks
    private MenuBean menu= new MenuBean();
                
    private List<MenuItemBean> menus = new ArrayList<MenuItemBean>();
                
    @Mock
    MenuItemBean menuItem;
    
    @Mock
    HeadingBean heading;
                
    @Mock
    List<MenuItemBean> list=new ArrayList<MenuItemBean>();
    
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoggerFactory.class);
        menu=PowerMockito.mock(MenuBean.class);
        menuItem=PowerMockito.mock(MenuItemBean.class);
        menuItem.setHeading(heading);
        PowerMockito.when(menu.getMenu()).thenReturn(menus);
        PrivateAccessor.setField(menu, "menus", list);
        PowerMockito.doCallRealMethod().when(menu).setMenu(menuItem);
        PowerMockito.doCallRealMethod().when(menu).getMenu();
        menu.setMenu(menuItem);
    }
                
    @Test
    public final void test(){
    	Assert.assertNotNull(menu.getMenu());
    }
}
