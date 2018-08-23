package com.hertz.digital.platform.use;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.commons.json.JSONException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.commons.Externalizer;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.constants.TestConstants;
import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.service.api.JCRService;
import com.hertz.digital.platform.service.impl.CountryLanguageSelectorServiceImpl;
import com.hertz.digital.platform.utils.HertzUtils;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WCMUsePojo.class, LoggerFactory.class, HertzUtils.class })
public class TestDataUse {

    String HOME_JCR_CONTENT_PATH = "/content/hertz-rac/rac-web/us/en/home";
    private static final String LOCATION_INFORMATION_O = "locationinformationo_multifieldpair";

    @InjectMocks
    DataUse dataUse;

    Logger log;

    @Mock
    JCRService jcrService;

    @Mock
    Externalizer externalizer;

    @Mock
    Page currentPage;

    @Mock
    Resource headerJcrContentResource;

    @Mock
    Resource headerParResource;

    @Mock
    Resource textfieldParResource;

    @Mock
    ValueMap valueMap;

    @Mock
    ValueMap valueMap1;

    @Mock
    ValueMap componentProperties;

    @Mock
    SlingHttpServletRequest mockRequest;

    @Mock
    Resource resource;

    @Mock
    Resource countryLevelResource;

    @Mock
    Resource subResource;

    @Mock
    ValueMap textfieldResourceProperties;

    @Mock
    CountryLanguageSelectorServiceImpl countryLanguageSelectorServiceImpl;

    @Mock
    Iterable<Resource> countryLevelResources;

    @Mock
    Iterable<Resource> resourceIterator;

    @Mock
    Iterator<Resource> mockIterator;

    @Mock
    Iterator<Resource> mockResourceIterator;

    @Mock
    Iterable<Resource> iterableChildren;

    @Mock
    Iterable<Resource> iterableComponents;

    @Mock
    Iterable<Resource> resourceIterable;

    @Mock
    Iterator<Resource> mocksubResourceIterator;

    @Mock
    Iterator<Resource> mockComponentsIterator;

    @Mock
    Iterator<Resource> parChildrenIterator;

    @Mock
    Resource componentChildren;

    @Mock
    Resource parChild;

    @Mock
    Iterable<Resource> parChildren;

    @Mock
    SlingScriptHelper slingScriptHelper;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    SearchResult searchResults;

    @Mock
    Resource hitResource;

    @Mock
    Resource componentResource;

    @Mock
    Iterator<Resource> mockComponentIterator2;

    @Mock
    HertzConfigFactory hertzConfigFactory;

    @Before
    public final void setup() throws Exception {
        dataUse = PowerMockito.mock(DataUse.class);
        PowerMockito.mockStatic(LoggerFactory.class);
        log = Mockito.mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
        MockitoAnnotations.initMocks(this);
        Mockito.doCallRealMethod().when(dataUse).activate();
        Mockito.doCallRealMethod().when(dataUse)
                .setJsonString(Mockito.anyString());
        Mockito.doCallRealMethod().when(dataUse).getJsonString();
        when(dataUse.getResourceResolver()).thenReturn(resourceResolver);
        when(dataUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);

    }

    @Test
    public final void testActivate1() throws Exception {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        String requestPath = HOME_JCR_CONTENT_PATH;
        when(mockRequest.getPathInfo()).thenReturn(requestPath);
        Node node = PowerMockito.mock(Node.class);
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

        when(currentPage.getProperties()).thenReturn(valueMap);
        String str = "/apps/hertz/templates/usecaseconfig1";
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(str);
        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair","selectradiofieldpair", "badgeimagecomponent" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);

        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
        when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
        when(mocksubResourceIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

        when(componentChildren.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn("simplepair , imagepair");
        when(parChild.getResourceType())
        .thenReturn("hertz/components/content/imagepair");
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        // List<Hit> hitList = new ArrayList();;
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn(LOCATION_INFORMATION_O,
                HertzConstants.SELECT_RADIO_FIELD_PAIR,
                HertzConstants.IMAGE_PAIR);
        when(componentResource.getValueMap()).thenReturn(componentProperties);
        when(componentProperties.containsKey(Mockito.anyString()))
                .thenReturn(true);
        when(componentProperties.get(Mockito.anyString(),
                Mockito.eq(String.class))).thenReturn("Test");
        String[] optionsList = { "1", "2" };
        when(componentProperties.get(HertzConstants.OPTIONS_LIST))
                .thenReturn(optionsList);
        when(componentResource.getResourceType())
                .thenReturn("/app/content/hertz");
        when(resource.getName()).thenReturn("DummyName");
        dataUse.activate();

    }
    @Test
    public final void testActivate7() throws Exception {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        String requestPath = HOME_JCR_CONTENT_PATH;
        when(mockRequest.getPathInfo()).thenReturn(requestPath);
        Node node = PowerMockito.mock(Node.class);
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

        when(currentPage.getProperties()).thenReturn(valueMap);
        String str = "/apps/hertz/templates/usecaseconfig1";
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(str);
        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);

        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
        when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
        when(mocksubResourceIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

        when(componentChildren.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn("simplepair , imagepair");
        when(parChild.getResourceType())
        .thenReturn("hertz/components/content/imagepair");
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        // List<Hit> hitList = new ArrayList();;
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn(HertzConstants.SELECT_RADIO_FIELD_PAIR,
                HertzConstants.IMAGE_PAIR);
        when(componentResource.getValueMap()).thenReturn(componentProperties);
        when(componentProperties.containsKey(Mockito.anyString()))
                .thenReturn(true);
        when(componentProperties.get(Mockito.anyString(),
                Mockito.eq(String.class))).thenReturn("Test");
        String[] optionsList = { "1", "2" };
        when(componentProperties.get(HertzConstants.OPTIONS_LIST))
                .thenReturn(optionsList);
        when(componentResource.getResourceType())
                .thenReturn("/app/content/hertz");
        when(resource.getName()).thenReturn("DummyName");
        dataUse.activate();

    }
    
    @Test
    public final void testActivate8() throws Exception {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        String requestPath = HOME_JCR_CONTENT_PATH;
        when(mockRequest.getPathInfo()).thenReturn(requestPath);
        Node node = PowerMockito.mock(Node.class);
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

        when(currentPage.getProperties()).thenReturn(valueMap);
        String str = "/apps/hertz/templates/usecaseconfig1";
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(str);
        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);

        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
        when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
        when(mocksubResourceIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

        when(componentChildren.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn("simplepair , imagepair");
        when(parChild.getResourceType())
        .thenReturn("hertz/components/content/imagepair");
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        // List<Hit> hitList = new ArrayList();;
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn("");
        when(componentResource.getValueMap()).thenReturn(componentProperties);
        when(componentProperties.containsKey(Mockito.anyString()))
                .thenReturn(true);
        when(componentProperties.get(Mockito.anyString(),
                Mockito.eq(String.class))).thenReturn("Test");
        String[] optionsList = { "1", "2" };
        when(componentProperties.get(HertzConstants.OPTIONS_LIST))
                .thenReturn(optionsList);
        when(componentResource.getResourceType())
                .thenReturn("/app/content/hertz");
        when(resource.getName()).thenReturn("DummyName");
        dataUse.activate();

    }

   @Test
    public final void testActivate2() throws Exception {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        String requestPath = HOME_JCR_CONTENT_PATH;
        when(mockRequest.getPathInfo()).thenReturn(requestPath);
        Node node = PowerMockito.mock(Node.class);
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

        when(currentPage.getProperties()).thenReturn(valueMap);
        String str = "/apps/hertz/templates/usecaseconfig1";
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(str);
        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);

        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
        when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
        when(mocksubResourceIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

        when(componentChildren.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn("simplepair , imagepair");
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        // List<Hit> hitList = new ArrayList();;
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn(HertzConstants.IMAGE_PAIR);
        when(componentResource.getValueMap()).thenReturn(componentProperties);
        when(componentProperties.containsKey(Mockito.anyString()))
                .thenReturn(true);
        when(componentProperties.get(Mockito.anyString(),
                Mockito.eq(String.class))).thenReturn("Test");
        String[] optionsList = { "1", "2" };
        when(componentProperties.get(HertzConstants.OPTIONS_LIST))
                .thenReturn(optionsList);
        when(componentResource.getResourceType())
                .thenReturn("/app/content/hertz");
        when(resource.getName()).thenReturn("DummyName");
        dataUse.activate();

    }

   @Test
   public final void testActivate9() throws Exception {
       when(dataUse.getRequest()).thenReturn(mockRequest);
       String requestPath = HOME_JCR_CONTENT_PATH;
       when(mockRequest.getPathInfo()).thenReturn(requestPath);
       Node node = PowerMockito.mock(Node.class);
       when(dataUse.getResource()).thenReturn(resource);
       when(resource.adaptTo(Node.class)).thenReturn(node);
       when(dataUse.getCurrentPage()).thenReturn(currentPage);
       when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

       when(currentPage.getProperties()).thenReturn(valueMap);
       String str = "/apps/hertz/templates/usecaseconfig1";
       when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
               .thenReturn(str);
       when(resource.getChildren()).thenReturn(resourceIterator);
       when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
       when(mockResourceIterator.hasNext()).thenReturn(true, false);
       when(mockResourceIterator.next()).thenReturn(subResource);
       when(slingScriptHelper.getService(HertzConfigFactory.class))
               .thenReturn(hertzConfigFactory);
       String[] dataAllowedComponents = { "simplepair", "imagepair",
               "linkpair","badgeimagecomponent", "selectradiofieldpair"};
       when(hertzConfigFactory
               .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                       .thenReturn(dataAllowedComponents);

       when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
       when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
       ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
       when(subResource.getResourceResolver()).thenReturn(resolver2);
       when(subResource.getChildren()).thenReturn(iterableChildren);
       when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
       when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
       when(mocksubResourceIterator.next()).thenReturn(componentChildren);
       when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

       when(componentChildren.getChildren()).thenReturn(parChildren);
       when(parChildren.iterator()).thenReturn(parChildrenIterator);
       when(parChildrenIterator.hasNext()).thenReturn(true, false);
       when(parChildrenIterator.next()).thenReturn(parChild);
       when(parChild.getName()).thenReturn("simplepair , imagepair");
       when(parChild.getResourceType())
       .thenReturn("hertz/components/content/imagepair");
       when(slingScriptHelper.getService(JCRService.class))
               .thenReturn(jcrService);
       when(slingScriptHelper.getService(Externalizer.class))
               .thenReturn(externalizer);
       when(jcrService.searchResults(Mockito.eq(resolver2),
               Mockito.anyMap())).thenReturn(searchResults);
       // List<Hit> hitList = new ArrayList();;
       List<Hit> hitList = new ArrayList<>();
       Hit hit = Mockito.mock(Hit.class);
       hitList.add(hit);
       when(searchResults.getHits()).thenReturn(hitList);
       when(hit.getResource()).thenReturn(hitResource);
       when(hitResource.getChildren()).thenReturn(iterableComponents);
       when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
       when(mockComponentsIterator.hasNext()).thenReturn(true, false);
       when(mockComponentsIterator.next()).thenReturn(componentResource);
       when(componentResource.getName()).thenReturn("badgeimagecomponent", "selectradiofieldpair");
       when(componentResource.getValueMap()).thenReturn(componentProperties);
       when(componentProperties.containsKey(Mockito.anyString()))
               .thenReturn(true);
       when(componentProperties.get(Mockito.anyString(),
               Mockito.eq(String.class))).thenReturn("Test");
       String[] optionsList = { "1", "2" };
       when(componentProperties.get(HertzConstants.OPTIONS_LIST))
               .thenReturn(optionsList);
       when(componentResource.getResourceType())
               .thenReturn("/app/content/hertz");
       when(resource.getName()).thenReturn("DummyName");
       dataUse.activate();

   }
   @Test
   public final void testActivate10() throws Exception {
       when(dataUse.getRequest()).thenReturn(mockRequest);
       String requestPath = HOME_JCR_CONTENT_PATH;
       when(mockRequest.getPathInfo()).thenReturn(requestPath);
       Node node = PowerMockito.mock(Node.class);
       when(dataUse.getResource()).thenReturn(resource);
       when(resource.adaptTo(Node.class)).thenReturn(node);
       when(dataUse.getCurrentPage()).thenReturn(currentPage);
       when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

       when(currentPage.getProperties()).thenReturn(valueMap);
       String str = "/apps/hertz/templates/usecaseconfig1";
       when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
               .thenReturn(str);
       when(resource.getChildren()).thenReturn(resourceIterator);
       when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
       when(mockResourceIterator.hasNext()).thenReturn(true, false);
       when(mockResourceIterator.next()).thenReturn(subResource);
       when(slingScriptHelper.getService(HertzConfigFactory.class))
               .thenReturn(hertzConfigFactory);
       String[] dataAllowedComponents = { "simplepair", "imagepair",
               "linkpair","badgeimagecomponent"};
       when(hertzConfigFactory
               .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                       .thenReturn(dataAllowedComponents);

       when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
       when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
       ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
       when(subResource.getResourceResolver()).thenReturn(resolver2);
       when(subResource.getChildren()).thenReturn(iterableChildren);
       when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
       when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
       when(mocksubResourceIterator.next()).thenReturn(componentChildren);
       when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

       when(componentChildren.getChildren()).thenReturn(parChildren);
       when(parChildren.iterator()).thenReturn(parChildrenIterator);
       when(parChildrenIterator.hasNext()).thenReturn(true, false);
       when(parChildrenIterator.next()).thenReturn(parChild);
       when(parChild.getName()).thenReturn("simplepair , imagepair");
       when(parChild.getResourceType())
       .thenReturn("hertz/components/content/imagepair");
       when(slingScriptHelper.getService(JCRService.class))
               .thenReturn(jcrService);
       when(slingScriptHelper.getService(Externalizer.class))
               .thenReturn(externalizer);
       when(jcrService.searchResults(Mockito.eq(resolver2),
               Mockito.anyMap())).thenReturn(searchResults);
       // List<Hit> hitList = new ArrayList();;
       List<Hit> hitList = new ArrayList<>();
       Hit hit = Mockito.mock(Hit.class);
       hitList.add(hit);
       when(searchResults.getHits()).thenReturn(hitList);
       when(hit.getResource()).thenReturn(hitResource);
       when(hitResource.getChildren()).thenReturn(iterableComponents);
       when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
       when(mockComponentsIterator.hasNext()).thenReturn(true, false);
       when(mockComponentsIterator.next()).thenReturn(componentResource);
       when(componentResource.getName()).thenReturn("badgeimagecomponent");
       when(componentResource.getValueMap()).thenReturn(componentProperties);
       when(componentProperties.containsKey(Mockito.anyString()))
               .thenReturn(true);
       when(componentProperties.get(Mockito.anyString())).thenReturn("Test");
       String[] optionsList = { "1", "2" };
       when(componentProperties.get(HertzConstants.OPTIONS_LIST))
               .thenReturn(optionsList);
       when(componentResource.getResourceType())
               .thenReturn("/app/content/hertz");
       when(resource.getName()).thenReturn("DummyName");
       dataUse.activate();

   }
   @Test
    public final void testActivate3() throws Exception {
    	
        when(dataUse.getRequest()).thenReturn(mockRequest);
        String requestPath = HOME_JCR_CONTENT_PATH;
        when(mockRequest.getPathInfo()).thenReturn(requestPath);
        Node node = PowerMockito.mock(Node.class);
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

        when(currentPage.getProperties()).thenReturn(valueMap);
        String str = "/apps/hertz/templates/usecaseconfig1";
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(str);
        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);

        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
        when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
        when(mocksubResourceIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

        when(componentChildren.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn("simplepair , imagepair");
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        // List<Hit> hitList = new ArrayList();;
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn("badgeimagecomponent");
        when(componentResource.getValueMap()).thenReturn(componentProperties);
        when(componentProperties.containsKey(Mockito.anyString()))
                .thenReturn(true);
        when(componentProperties.get(Mockito.anyString())).thenReturn("Test");
        when(componentResource.hasChildren()).thenReturn(true);
        Resource fileImageResource = Mockito.mock(Resource.class);
        when(componentResource.getChild(HertzConstants.IMAGE))
                .thenReturn(fileImageResource);
        ValueMap imageProperties = Mockito.mock(ValueMap.class);
        when(fileImageResource.getValueMap()).thenReturn(imageProperties);
        when(imageProperties.get("fileReference", String.class))
                .thenReturn("fileReference");
        
         /* String[] optionsList ={"1","2"};
         * when(componentProperties.get(HertzConstants.OPTIONS_LIST)).thenReturn
         * (optionsList); when(componentResource.getResourceType()).thenReturn(
         * "/app/content/hertz");*/
        
        when(resource.getName()).thenReturn("DummyName");
        when(resource.getValueMap()).thenReturn(valueMap);
        dataUse.activate();

    }

  @Test
    public final void testActivate() throws Exception {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        String requestPath = HOME_JCR_CONTENT_PATH;
        when(mockRequest.getPathInfo()).thenReturn(requestPath);
        Node node = PowerMockito.mock(Node.class);
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

        when(currentPage.getProperties()).thenReturn(valueMap);
        String str = "/apps/hertz/templates/usecaseconfig1";
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(str);
        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);

        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
        when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
        when(mocksubResourceIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

        when(componentChildren.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn("simplepair , imagepair");
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        // List<Hit> hitList = new ArrayList();;
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn("another component");
        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("testvalue", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap1.entrySet()).thenReturn(properties);
        when(componentResource.getValueMap()).thenReturn(valueMap1);
        when(componentResource.hasChildren()).thenReturn(true);
        Resource fileImageResource = Mockito.mock(Resource.class);
        when(componentResource.getChild(HertzConstants.IMAGE))
                .thenReturn(fileImageResource);
        ValueMap imageProperties = Mockito.mock(ValueMap.class);
        when(fileImageResource.getValueMap()).thenReturn(imageProperties);
        when(imageProperties.get("fileReference", String.class))
                .thenReturn("fileReference");
        when(componentResource.getResourceType())
                .thenReturn("/app/content/hertz");
        when(resource.getName()).thenReturn("DummyName");
       dataUse.activate();
    }

    @Test
    public final void testActivateFileReference() throws Exception {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        String requestPath = HOME_JCR_CONTENT_PATH;
        when(mockRequest.getPathInfo()).thenReturn(requestPath);
        Node node = PowerMockito.mock(Node.class);
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

        when(currentPage.getProperties()).thenReturn(valueMap);
        String str = "/apps/hertz/templates/usecaseconfig1";
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(str);
        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);

        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mocksubResourceIterator);
        when(mocksubResourceIterator.hasNext()).thenReturn(true, false);
        when(mocksubResourceIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn(HertzConstants.PAR);

        when(componentChildren.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn("simplepair , imagepair");
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);

        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        // List<Hit> hitList = new ArrayList();;
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn("another component");
        Set<Entry<String,
                Object>> properties = new HashSet<Entry<String, Object>>();
        Map.Entry<String, Object> entry_ignore = new AbstractMap.SimpleEntry<
                String, Object>("jcr:lastModified", "jcr:lastModified");
        Map.Entry<String, Object> entry_accept = new AbstractMap.SimpleEntry<
                String, Object>("fileReference", "testvalue");
        properties.add(entry_ignore);
        properties.add(entry_accept);
        when(valueMap1.entrySet()).thenReturn(properties);
        when(componentResource.getValueMap()).thenReturn(valueMap1);
        when(componentResource.hasChildren()).thenReturn(false);
        when(valueMap1.containsKey("fileReference")).thenReturn(true);
        when(componentResource.getResourceType())
                .thenReturn("/app/content/hertz");
        
      dataUse.activate();
    }

   @Test
    public final void testActivateRQCode() throws Exception {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        when(mockRequest.getPathInfo())
                .thenReturn(TestConstants.DEFAULT_RQ_CODE_PATH);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);
        when(currentPage.getProperties()).thenReturn(valueMap);
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(TestConstants.DATA_TEMPLATE_PATH);
        when(resource.getChildren()).thenReturn(resourceIterable);
        when(resourceIterable.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);
        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn(HertzConstants.PAR);
        when(parChild.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn("defaultrqcode");
        when(subResource.getPath()).thenReturn(
                "/content/hertz-rac/data/rates-config/default-rq-codes/us/us/jcr:content");
        when(dataUse.getResourceResolver()).thenReturn(resourceResolver);
        when(dataUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentIterator2);
        when(mockComponentIterator2.hasNext()).thenReturn(true, false);
        when(mockComponentIterator2.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn("defaultrqcode");
        componentProperties.put("availableToUseOnWeb", "yes");
        componentProperties.put("defaultRQcode", "CTA");
        when(componentResource.getValueMap()).thenReturn(componentProperties);
        when(componentResource.getResourceType())
                .thenReturn("hertz/components/content/defaultrqcode");
        dataUse.activate();
    }

    @Test
    public void testGetterSetterRQCode() {
        String json = "{'datacontent':{'us':[{'us':[{'components':[{'defaultrqcode':{'availableToUseOnWeb':'yes','defaultRQcode':'BEST'}}],'textfields':[{'availableToUseOnWeb':'false','defaultRQcode':'BEST'}]}]}],'ca':[{'us':[{'components':[{'defaultrqcode':{'availableToUseOnWeb':'yes','defaultRQcode':'CTA'}}]}]}]}}";
        dataUse.setJsonString(json);
        Assert.assertTrue(dataUse.getJsonString().equals(json));
    }

    @Test
    public final void testActivateLocationCode() throws Exception {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        when(mockRequest.getPathInfo())
                .thenReturn(TestConstants.DEFAULT_RQ_CODE_PATH);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);
        when(currentPage.getProperties()).thenReturn(valueMap);
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(TestConstants.DATA_TEMPLATE_PATH);
        when(resource.getChildren()).thenReturn(resourceIterable);
        when(resourceIterable.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);
        when(subResource.getName()).thenReturn(HertzConstants.JCR_CONTENT);
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(true, false);
        when(parChildrenIterator.next()).thenReturn(parChild);
        when(parChild.getName()).thenReturn(HertzConstants.PAR);
        when(parChild.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn("defaultrqcode");
        when(subResource.getPath()).thenReturn(
                "/content/hertz-rac/data/rates-config/default-rq-codes/us/us/jcr:content");
        when(dataUse.getResourceResolver()).thenReturn(resourceResolver);
        when(dataUse.getSlingScriptHelper()).thenReturn(slingScriptHelper);
        when(slingScriptHelper.getService(JCRService.class))
                .thenReturn(jcrService);
        when(slingScriptHelper.getService(Externalizer.class))
                .thenReturn(externalizer);
        when(jcrService.searchResults(Mockito.eq(resolver2),
                Mockito.anyMap())).thenReturn(searchResults);
        List<Hit> hitList = new ArrayList<>();
        Hit hit = Mockito.mock(Hit.class);
        hitList.add(hit);
        when(searchResults.getHits()).thenReturn(hitList);
        when(hit.getResource()).thenReturn(hitResource);
        when(hitResource.getChildren()).thenReturn(iterableComponents);
        when(iterableComponents.iterator()).thenReturn(mockComponentIterator2);
        when(mockComponentIterator2.hasNext()).thenReturn(true, false);
        when(mockComponentIterator2.next()).thenReturn(componentResource);
        when(componentResource.getName()).thenReturn("location");
        componentProperties.put("availableToUseOnWeb", "yes");
        componentProperties.put("defaultRQcode", "CTA");
        when(componentResource.getValueMap()).thenReturn(componentProperties);
        when(componentResource.getResourceType())
                .thenReturn("hertz/components/content/location");
       dataUse.activate();
    }

  @Test
    public void testGetterSetterLocationCode() {
        String json = "{\"datacontent\":{\"na\":[{\"us\":[{\"il\":[{\"chicago\":[{\"ROCS05\":[{\"components\":[{\"location\":{\"hoursofoperation2AriaLabel\":\"Sat-Sun Closed\",\"hoursofoperationAriaLabel\":\"Mon-Fri 8:00AM-5:00PM\",\"hoursofoperation\":\"Mon-Fri 8:00AM-5:00PM\",\"hoursofoperation2\":\"Sat-Sun Closed\"}}]}],\"DFWX11\":[{\"components\":[{\"location\":{\"hoursofoperation3AriaLabel\":\"Sun 1:00PM-4:00PM\",\"hoursofoperation2AriaLabel\":\"Sat 8:00AM-2:00PM\",\"hoursofoperationAriaLabel\":\"Mon-Fri 8:00AM-5:00PM\",\"hoursofoperation3\":\"Sun 1:00PM-4:00PM\",\"hoursofoperation\":\"Mon-Fri 8:00AM-5:00PM\",\"hoursofoperation2\":\"Sat 8:00AM-2:00PM\"}}]}],\"RMAT50\":[{\"components\":[{\"location\":{\"hoursofoperation3AriaLabel\":\"Sat: 0830-1200 Sun: 1430-1800\",\"hoursofoperation2AriaLabel\":\"Thu: 0730-1830 Fri: 0900-1730\",\"hoursofoperationAriaLabel\":\"Mon Wed 0700-1730 Tue 0700-1700\",\"hoursofoperation3\":\"Sat: 0830-1200 Sun: 1430-1800\",\"hoursofoperation\":\"Mon Wed 0700-1730 Tue 0700-1700\",\"hoursofoperation2\":\"Thu: 0730-1830 Fri: 0900-1730\"}}]}],\"GRBT01\":[{\"components\":[{\"location\":{\"hoursofoperation3AriaLabel\":\"Sun 8:00AM-10:30PM\",\"hoursofoperation2AriaLabel\":\"Sat 8:00AM-9:30PM\",\"hoursofoperationAriaLabel\":\"Mon-Sun 6:00 AM-1:00 AM\",\"hoursofoperation3\":\"Sun 8:00AM-10:30PM\",\"hoursofoperation\":\"Mon-Sun 6:00 AM-1:00 AM\",\"hoursofoperation2\":\"Sat 8:00AM-9:30PM\"}}]}],\"AUST15\":[{\"components\":[{\"location\":{\"hoursofoperationAriaLabel\":\"Mon-Sun 6:00AM-1:00AM\",\"hoursofoperation\":\"Mon-Sun 6:00AM-1:00AM\"}}]}],\"DALT11\":[{\"components\":[{\"location\":{\"hoursofoperationAriaLabel\":\"Mon-Sun 6:00AM-11:00PM\",\"hoursofoperation\":\"Mon-Sun 6:00AM-11:00PM\"}}]}],\"RBDT16\":[{\"components\":[{\"location\":{\"hoursofoperation3AriaLabel\":\"Sun Closed\",\"hoursofoperation2AriaLabel\":\"Sat 9:00AM-12:00PM\",\"hoursofoperationAriaLabel\":\"Mon-Fri 8:00AM-6:00PM\",\"hoursofoperation3\":\"Sun Closed\",\"hoursofoperation\":\"Mon-Fri 8:00AM-6:00PM\",\"hoursofoperation2\":\"Sat 9:00AM-12:00PM\"}}]}],\"ABCD01\":[{\"components\":[{\"location\":{\"hoursofoperation2AriaLabel\":\"Sat-Sun Closed\",\"hoursofoperationAriaLabel\":\"Mon-Fri 8:00AM-5:00PM\",\"hoursofoperation\":\"Mon-Fri 8:00AM-5:00PM\",\"hoursofoperation2\":\"Sat-Sun Closed\"}}]}]}]}]}]}]}}\r\n";
        dataUse.setJsonString(json);
        Assert.assertTrue(dataUse.getJsonString().equals(json));
    }

  @Test
    public void testActivate4() throws RepositoryException, JSONException,
            ServletException, IOException {
        Node node = PowerMockito.mock(Node.class);
        PropertyIterator iterator = PowerMockito.mock(PropertyIterator.class);
        Property property = PowerMockito.mock(Property.class);
        NodeIterator nIterator = PowerMockito.mock(NodeIterator.class);
        when(dataUse.getRequest()).thenReturn(mockRequest);
        when(mockRequest.getPathInfo())
                .thenReturn(TestConstants.OFFER_LANDING_PATH + "/jcr:content");
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(node.getProperties()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.nextProperty()).thenReturn(property);
        when(property.getType()).thenReturn(2);
        when(property.getName()).thenReturn("name");
        when(property.isMultiple()).thenReturn(true);
        when(property.getLength()).thenReturn(new Long(1));
        when(property.getLengths()).thenReturn(new long[] { new Long(1) });
        when(node.getNodes()).thenReturn(nIterator);
        when(nIterator.hasNext()).thenReturn(false);
        dataUse.activate();
    }

  @Test
    public void testActivate5() throws JSONException, RepositoryException {
        String countryLanguageResPath = HertzConstants.JCR_CONTENT + "/"
                + HertzConstants.PAR + "/" + HertzConstants.COUNTRYLANGUAGE;
        Resource countryLanguageResource = PowerMockito.mock(Resource.class);
        Resource languageResource = PowerMockito.mock(Resource.class);
        Resource jcrContentResource = PowerMockito.mock(Resource.class);
        ValueMap valueMap1 = PowerMockito.mock(ValueMap.class);
        when(dataUse.getRequest()).thenReturn(mockRequest);
        when(mockRequest.getPathInfo())
                .thenReturn(TestConstants.DEFAULT_RQ_CODE_PATH);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);
        when(currentPage.getProperties()).thenReturn(valueMap);
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(HertzConstants.COUNTRY_LANGUAGE_TEMPLATE_PATH);
        when(resource.getChild(countryLanguageResPath))
                .thenReturn(countryLanguageResource);
        when(countryLanguageResource.getValueMap())
                .thenReturn(componentProperties);
        when(componentProperties.get("countryLabel", String.class))
                .thenReturn("countryLabel");
        when(componentProperties.get("languageLabel", String.class))
                .thenReturn("languageLabel");
        when(componentProperties.get("buttonLabel", String.class))
                .thenReturn("buttonLabel");
        when(componentProperties.get("updateMessage", String.class))
                .thenReturn("updateMessage");

        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(subResource);
        when(subResource.getName()).thenReturn("us");
        when(resource.getChild("us")).thenReturn(subResource);
        when(subResource.getName()).thenReturn("us");
        when(subResource.getPath()).thenReturn(TestConstants.SOURCE_COUNTRY_PAGE_PATH);
        ResourceResolver resolver2=Mockito.mock(ResourceResolver.class);
        when(subResource.getResourceResolver()).thenReturn(resolver2);
        when(subResource.adaptTo(Page.class)).thenReturn(currentPage);
        when(currentPage.getProperties()).thenReturn(valueMap);
        when(valueMap.get(HertzConstants.JCR_TITLE_PROPERTY, String.class))
                .thenReturn("United States");
        when(subResource.getChild(Mockito.anyString()))
                .thenReturn(languageResource);
        when(languageResource.getChild(HertzConstants.JCR_CONTENT))
                .thenReturn(jcrContentResource);
        when(jcrContentResource.getChild(HertzConstants.PAR))
                .thenReturn(parChild);
        when(parChild.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(mockComponentIterator2);
        when(mockComponentIterator2.hasNext()).thenReturn(true, false);
        when(mockComponentIterator2.next()).thenReturn(componentResource);
        when(componentResource.getValueMap()).thenReturn(valueMap1);
        when(valueMap1.containsKey(Mockito.anyString())).thenReturn(true);
        when(valueMap1.get("locale", String.class)).thenReturn("locale");
        when(valueMap1.get("languageName", String.class))
                .thenReturn("languageName");
        when(valueMap1.get("languageCode", String.class))
                .thenReturn("languageCode");
        when(valueMap1.get("isDefaultLanguage", Boolean.class))
                .thenReturn(true);
        when(valueMap1.get(HertzConstants.IRACLINK,String.class)).thenReturn("irac link");
        when(valueMap1.get("url", String.class)).thenReturn("url");
        dataUse.activate();

    }

  @Test
    public void testActivate6() throws JSONException, RepositoryException {
        when(dataUse.getRequest()).thenReturn(mockRequest);
        String requestPath = HOME_JCR_CONTENT_PATH;
        when(mockRequest.getPathInfo()).thenReturn(requestPath);
        Node node = PowerMockito.mock(Node.class);
        when(dataUse.getResource()).thenReturn(resource);
        when(resource.adaptTo(Node.class)).thenReturn(node);
        when(dataUse.getCurrentPage()).thenReturn(currentPage);
        when(currentPage.adaptTo(Resource.class)).thenReturn(resource);

        when(currentPage.getProperties()).thenReturn(valueMap);
        String str = "/apps/hertz/templates/usecaseconfig1";
        when(valueMap.get(HertzConstants.JCR_CQ_TEMPLATE, String.class))
                .thenReturn(str);
        when(resource.getChildren()).thenReturn(resourceIterator);
        when(resourceIterator.iterator()).thenReturn(mockResourceIterator);
        when(mockResourceIterator.hasNext()).thenReturn(true, false);
        when(mockResourceIterator.next()).thenReturn(subResource);
        when(slingScriptHelper.getService(HertzConfigFactory.class))
                .thenReturn(hertzConfigFactory);
        String[] dataAllowedComponents = { "simplepair", "imagepair",
                "linkpair" };
        when(hertzConfigFactory
                .getPropertyValue(HertzConstants.Hertz_DATA_ALLOWED_COMPONENTS))
                        .thenReturn(dataAllowedComponents);

        when(subResource.getName()).thenReturn(HertzConstants.IMAGE);
        when(subResource.getChildren()).thenReturn(iterableChildren);
        when(iterableChildren.iterator()).thenReturn(mockComponentsIterator);
        when(mockComponentsIterator.hasNext()).thenReturn(true, false);
        when(mockComponentsIterator.next()).thenReturn(componentChildren);
        when(componentChildren.getName()).thenReturn(HertzConstants.LOGO);
        when(componentChildren.getChildren()).thenReturn(parChildren);
        when(parChildren.iterator()).thenReturn(parChildrenIterator);
        when(parChildrenIterator.hasNext()).thenReturn(false);
       dataUse.activate();
    }

}