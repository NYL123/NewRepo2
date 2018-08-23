package com.hertz.digital.platform.utils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.Rendition;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class })
public class TestFooterUtils {

    @Mock
    QueryBuilder builder;

    @Mock
    Session session;

    @Mock
    Query query;

    @Mock
    SearchResult result;

    List<Hit> list = new ArrayList<>();

    @Mock
    Hit hit;

    @Mock
    Resource resource;

    @Mock
    Resource footerResource;

    @Mock
    ValueMap valueMap;

    @Mock
    Resource footerLinkChildResource;

    @Mock
    Resource emailResource;

    @Mock
    ValueMap emailMap;

    @Mock
    ValueMap valueMap2;

    @Mock
    Resource socialLinkChildResource;

    @Mock
    ValueMap valueMap3;

    @Mock
    Iterable<Resource> mockIterable;

    @Mock
    Iterator<Resource> mockIterator;

    @Mock
    Resource mockChildResource;

    @Mock
    ValueMap valueMap4;

    @Mock
    ValueMap componentProperties;

    @Mock
    Resource imageResource;

    @Mock
    ResourceResolver resolver;

    @Mock
    Asset mockAsset;

    @Mock
    private Iterator<? extends Rendition> mockRenditions;

    @Mock
    Rendition rendition;

    @Before
    public final void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public final void testGetFooterJSON()
            throws RepositoryException, LoginException, JSONException {

        /*when(builder.createQuery(any(PredicateGroup.class), eq(session)))
                .thenReturn(query);
        when(query.getResult()).thenReturn(result);
        list.add(hit);
        when(result.getHits()).thenReturn(list);
        when(hit.getResource()).thenReturn(resource);*/
    	when(resolver.getResource(Mockito.anyString())).thenReturn(resource);
        when(resource.getChild(HertzConstants.FOOTER))
                .thenReturn(footerResource);
        when(footerResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
        when(valueMap.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap.get(HertzConstants.LEGAL_DESRIPTION_TEXT, String.class))
                .thenReturn("legalDescriptionText");
        when(valueMap.get(HertzConstants.LEGAL_LINK_LABEL_1, String.class))
                .thenReturn("legalLinkLabel1");
        when(valueMap.get(HertzConstants.LEGAL_LINK_LABEL_2, String.class))
                .thenReturn("legalLinkLable2");
        when(valueMap.get(HertzConstants.LEGAL_LINK_URL_1, String.class))
                .thenReturn("legalurl1");
        when(valueMap.get(HertzConstants.LEGAL_LINK_URL_2, String.class))
                .thenReturn("legalurl2");
        when(valueMap.get(HertzConstants.OPEN_LEGAL_NEW_WINDOW_1,
                Boolean.class)).thenReturn(true);
        when(valueMap.get(HertzConstants.OPEN_LEGAL_NEW_WINDOW_2,
                Boolean.class)).thenReturn(true);
        when(footerResource.getChild(HertzConstants.EMAIL_SIGNUP))
                .thenReturn(emailResource);
        when(emailResource.adaptTo(ValueMap.class)).thenReturn(emailMap);
        when(emailMap.containsKey(Mockito.anyString())).thenReturn(true);
        when(emailMap.get(HertzConstants.SIGNUP_EMAIL_TITLE, String.class))
                .thenReturn("emailTitle");
        when(emailMap.get(HertzConstants.SIGNUP_EMAIL_SUBTITLE, String.class))
                .thenReturn("emailSubtitle");
        when(emailMap.get(HertzConstants.SIGNUP_EMAIL_PLACEHOLDER_TEXT,
                String.class)).thenReturn("placeholdertext");
        when(emailMap.get(HertzConstants.SIGNUP_EMAIL_BUTTON_TEXT,
                String.class)).thenReturn("signupemailbuttontext");
        when(emailMap.get(HertzConstants.TARGET_URL, String.class))
                .thenReturn("targetURL");
        when(emailMap.get(HertzConstants.OPEN_URL_NEW_WINDOW, Boolean.class))
                .thenReturn(true);
        when(footerResource.getChild("footerlinks"))
                .thenReturn(footerLinkChildResource);
        Iterable<Resource> iterableComponents = Mockito.mock(Iterable.class);
        when(footerLinkChildResource.getChildren())
                .thenReturn(iterableComponents);
        Iterator<Resource> iterableComponentsIterator = Mockito
                .mock(Iterator.class);
        when(iterableComponents.iterator())
                .thenReturn(iterableComponentsIterator);
        when(iterableComponentsIterator.hasNext()).thenReturn(true, false);
        Resource columnContainerResource = Mockito.mock(Resource.class);
        when(iterableComponentsIterator.next())
                .thenReturn(columnContainerResource);
        Iterable<Resource> iterableColumnheadingResource = Mockito
                .mock(Iterable.class);
        when(columnContainerResource.getChildren())
                .thenReturn(iterableColumnheadingResource);
        Iterator<Resource> iterableColumnheadingResourceIterator = Mockito
                .mock(Iterator.class);
        when(iterableColumnheadingResource.iterator())
                .thenReturn(iterableColumnheadingResourceIterator);
        when(iterableColumnheadingResourceIterator.hasNext()).thenReturn(true,
                false);
        Resource columnheadingResource = Mockito.mock(Resource.class);
        when(iterableColumnheadingResourceIterator.next())
                .thenReturn(columnheadingResource);
        when(columnheadingResource.getValueMap())
                .thenReturn(componentProperties);
        componentProperties.put(HertzConstants.LINKS, HertzConstants.LINKS);

        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_header = new AbstractMap.SimpleEntry<
                String, Object>(HertzConstants.HEADING, "Heading");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("links",
                        "[{linktext: \"About Hertz\",linkurl: \"/support\",rel: \"No\",targetwindow: \"Yes\",displayDesktopLink: true,displayTabletLink: true,displayMobileLink: true,displayAppLink: false}]");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        properties.add(entry_header);
        when(componentProperties.entrySet()).thenReturn(properties);
        when(componentProperties.get(HertzConstants.LINKS)).thenReturn(
                "{linktext: \"About Hertz\",linkurl: \"/support\",rel: \"No\",targetwindow: \"Yes\",displayDesktopLink: true,displayTabletLink: true,displayMobileLink: true,displayAppLink: false}");
        when(footerResource.getChild("sociallinks"))
                .thenReturn(socialLinkChildResource);
        when(socialLinkChildResource.adaptTo(ValueMap.class))
                .thenReturn(valueMap3);
        when(valueMap3.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap3.get(HertzConstants.SOCIAL_LINKS_SUBTITLE))
                .thenReturn(HertzConstants.SOCIAL_LINKS_SUBTITLE);
        when(socialLinkChildResource.getChild("socialLinks"))
                .thenReturn(socialLinkChildResource);
        when(socialLinkChildResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);// 64
        when(mockIterator.next()).thenReturn(mockChildResource);
        when(mockChildResource.getValueMap()).thenReturn(valueMap4);
        when(valueMap4.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap4.get("socialSite", String.class))
                .thenReturn("socialSite");
        when(valueMap4.get("socialIconImageReference", String.class))
                .thenReturn("/content/hertz/us/en/abc.jpg");
        when(resolver.getResource("/content/hertz/us/en/abc.jpg"))
                .thenReturn(imageResource);
        when(imageResource.adaptTo(Asset.class)).thenReturn(mockAsset);
        PowerMockito.doReturn(mockRenditions).when(mockAsset).listRenditions();
        when(mockRenditions.hasNext()).thenReturn(true, false);
        PowerMockito.doReturn(rendition).when(mockRenditions).next();
        when(rendition.getName()).thenReturn("cq5dam.thumbnail.48.48.png");
        when(rendition.getPath()).thenReturn(
                "/content/dam/projects/outdoors/cover/jcr:content/renditions/cq5dam.thumbnail.48.48.png");
        when(valueMap4.get("openIconNewWindow", Boolean.class))
                .thenReturn(true);
        when(valueMap4.get("socialURL", String.class))
                .thenReturn("https://www.hertz.com");
        when(valueMap4.get("altText", String.class))
                .thenReturn("Image not available");
        when(resolver.isLive()).thenReturn(true);
        when(session.isLive()).thenReturn(true);
        FooterUtils.getFooterJSON(TestConstants.PARTNER_PAGE_PATH, resolver);
    }

    @Test
    public final void testGetFooterJSON1()
            throws RepositoryException, LoginException, JSONException {
        when(builder.createQuery(any(PredicateGroup.class), eq(session)))
                .thenReturn(query);
        when(query.getResult()).thenReturn(result);
        list.add(hit);
        when(result.getHits()).thenReturn(list);
        when(hit.getResource()).thenReturn(resource);
        when(resource.getChild(HertzConstants.FOOTER))
                .thenReturn(footerResource);
        when(footerResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
        when(valueMap.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap.get(HertzConstants.LEGAL_DESRIPTION_TEXT, String.class))
                .thenReturn("legalDescriptionText");
        when(valueMap.get(HertzConstants.LEGAL_LINK_LABEL_1, String.class))
                .thenReturn("legalLinkLabel1");
        when(valueMap.get(HertzConstants.LEGAL_LINK_LABEL_2, String.class))
                .thenReturn("legalLinkLable2");
        when(valueMap.get(HertzConstants.LEGAL_LINK_URL_1, String.class))
                .thenReturn("legalurl1");
        when(valueMap.get(HertzConstants.LEGAL_LINK_URL_2, String.class))
                .thenReturn("legalurl2");
        when(valueMap.get(HertzConstants.OPEN_LEGAL_NEW_WINDOW_1,
                Boolean.class)).thenReturn(true);
        when(valueMap.get(HertzConstants.OPEN_LEGAL_NEW_WINDOW_2,
                Boolean.class)).thenReturn(true);
        when(footerResource.getChild(HertzConstants.EMAIL_SIGNUP))
                .thenReturn(emailResource);
        when(emailResource.adaptTo(ValueMap.class)).thenReturn(emailMap);
        when(emailMap.containsKey(Mockito.anyString())).thenReturn(true);
        when(emailMap.get(HertzConstants.SIGNUP_EMAIL_TITLE, String.class))
                .thenReturn("emailTitle");
        when(emailMap.get(HertzConstants.SIGNUP_EMAIL_SUBTITLE, String.class))
                .thenReturn("emailSubtitle");
        when(emailMap.get(HertzConstants.SIGNUP_EMAIL_PLACEHOLDER_TEXT,
                String.class)).thenReturn("placeholdertext");
        when(emailMap.get(HertzConstants.SIGNUP_EMAIL_BUTTON_TEXT,
                String.class)).thenReturn("signupemailbuttontext");
        when(emailMap.get(HertzConstants.TARGET_URL, String.class))
                .thenReturn("targetURL");
        when(emailMap.get(HertzConstants.OPEN_URL_NEW_WINDOW, Boolean.class))
                .thenReturn(true);
        when(footerResource.getChild("footerlinks"))
                .thenReturn(footerLinkChildResource);
        Iterable<Resource> iterableComponents = Mockito.mock(Iterable.class);
        when(footerLinkChildResource.getChildren())
                .thenReturn(iterableComponents);
        Iterator<Resource> iterableComponentsIterator = Mockito
                .mock(Iterator.class);
        when(iterableComponents.iterator())
                .thenReturn(iterableComponentsIterator);
        when(iterableComponentsIterator.hasNext()).thenReturn(true, false);
        Resource columnContainerResource = Mockito.mock(Resource.class);
        when(iterableComponentsIterator.next())
                .thenReturn(columnContainerResource);
        Iterable<Resource> iterableColumnheadingResource = Mockito
                .mock(Iterable.class);
        when(columnContainerResource.getChildren())
                .thenReturn(iterableColumnheadingResource);
        Iterator<Resource> iterableColumnheadingResourceIterator = Mockito
                .mock(Iterator.class);
        when(iterableColumnheadingResource.iterator())
                .thenReturn(iterableColumnheadingResourceIterator);
        when(iterableColumnheadingResourceIterator.hasNext()).thenReturn(true,
                false);
        Resource columnheadingResource = Mockito.mock(Resource.class);
        when(iterableColumnheadingResourceIterator.next())
                .thenReturn(columnheadingResource);
        when(columnheadingResource.getValueMap())
                .thenReturn(componentProperties);
        componentProperties.put(HertzConstants.LINKS, HertzConstants.LINKS);
        when(footerResource.getChild("sociallinks"))
                .thenReturn(socialLinkChildResource);
        when(socialLinkChildResource.adaptTo(ValueMap.class))
                .thenReturn(valueMap3);
        when(valueMap3.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap3.get(HertzConstants.SOCIAL_LINKS_SUBTITLE))
                .thenReturn(HertzConstants.SOCIAL_LINKS_SUBTITLE);
        when(socialLinkChildResource.getChild("socialLinks"))
                .thenReturn(socialLinkChildResource);
        when(socialLinkChildResource.getChildren()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);// 64
        when(mockIterator.next()).thenReturn(mockChildResource);
        when(mockChildResource.getValueMap()).thenReturn(valueMap4);
        when(valueMap4.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap4.get("socialSite", String.class))
                .thenReturn("socialSite");
        when(valueMap4.get("socialIconImageReference", String.class))
                .thenReturn("/content/hertz/us/en/abc.jpg");
        when(resolver.getResource("/content/hertz/us/en/abc.jpg"))
                .thenReturn(imageResource);
        when(imageResource.adaptTo(Asset.class)).thenReturn(mockAsset);
        PowerMockito.doReturn(mockRenditions).when(mockAsset).listRenditions();
        when(mockRenditions.hasNext()).thenReturn(true, false);
        PowerMockito.doReturn(rendition).when(mockRenditions).next();
        when(rendition.getName()).thenReturn("cq5dam.thumbnail.48.48.png");
        when(rendition.getPath()).thenReturn(
                "/content/dam/projects/outdoors/cover/jcr:content/renditions/cq5dam.thumbnail.48.48.png");
        when(valueMap4.get("openIconNewWindow", Boolean.class))
                .thenReturn(true);
        when(valueMap4.get("socialURL", String.class))
                .thenReturn("https://www.hertz.com");
        when(valueMap4.get("altText", String.class))
                .thenReturn("Image not available");
        when(resolver.isLive()).thenReturn(true);
        when(session.isLive()).thenReturn(true);
        FooterUtils.getFooterJSON(TestConstants.DATA_TEMPLATE_PATH,resolver);
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<FooterUtils> constructor = FooterUtils.class
                .getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
