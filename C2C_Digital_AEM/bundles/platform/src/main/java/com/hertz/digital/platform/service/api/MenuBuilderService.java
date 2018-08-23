package com.hertz.digital.platform.service.api;

import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;

import com.hertz.digital.platform.bean.MenuItemBean;

/**
 * Interface for the Menu Builder Service
 * 
 * @author puneet.soni
 *
 */
@FunctionalInterface 
public interface MenuBuilderService {
    /**
     * 
     * @param resource
     *            the resource to the Menu Pages
     * @return List of Menu item Bean
     * @throws RepositoryException
     *             Throws RepositoryExceptionn
     */
    List<MenuItemBean> getMenuJSON(Resource resource)
            throws RepositoryException;

}
