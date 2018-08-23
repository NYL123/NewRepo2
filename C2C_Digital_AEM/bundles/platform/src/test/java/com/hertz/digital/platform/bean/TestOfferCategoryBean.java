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

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class TestOfferCategoryBean {

	@InjectMocks
    private OfferCategoryBean offerCategoryBean= new OfferCategoryBean();

    @Mock
    OfferDetailsBean offerDetailsBean;
    
    @Mock
    ImageInfoBean imageInfoBean;
    
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoggerFactory.class);
        offerCategoryBean=PowerMockito.mock(OfferCategoryBean.class);
        List<OfferDetailsBean> mockList1 = new ArrayList<OfferDetailsBean>();
        mockList1.add(offerDetailsBean);
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setBadge(imageInfoBean);
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getBadge();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setLogo(imageInfoBean);
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getLogo();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setImage(imageInfoBean);
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getImage();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setOffers(mockList1);
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getOffers();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setCtaAction(Mockito.anyString());
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getCtaAction();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setCtaLabel(Mockito.anyString());
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getCtaLabel();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setSubhead(Mockito.anyString());
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getSubhead();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setHeadline(Mockito.anyString());
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getHeadline();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setRank(Mockito.anyString());
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getRank();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setHeadlineSecondLine(Mockito.anyString());;
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getHeadlineSecondLine();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setDb2Category(Mockito.anyString());
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getDb2Category();
        PowerMockito.doCallRealMethod().when(offerCategoryBean).setContentPath(Mockito.anyString());;
        PowerMockito.doCallRealMethod().when(offerCategoryBean).getContentPath();
        offerCategoryBean.setBadge(imageInfoBean);
        offerCategoryBean.setLogo(imageInfoBean);
        offerCategoryBean.setImage(imageInfoBean);
        offerCategoryBean.setOffers(mockList1);
        offerCategoryBean.setCtaAction(Mockito.anyString());
        offerCategoryBean.setCtaLabel(Mockito.anyString());
        offerCategoryBean.setSubhead(Mockito.anyString());
        offerCategoryBean.setHeadline(Mockito.anyString());
        offerCategoryBean.setRank(Mockito.anyString());
        offerCategoryBean.setHeadlineSecondLine("headlineSecondLine");
        offerCategoryBean.setContentPath("contentPath");
        offerCategoryBean.setDb2Category("db2Category");
    }
                
    @Test
    public final void test(){
    	Assert.assertNotNull(offerCategoryBean.getBadge());
    	Assert.assertNotNull(offerCategoryBean.getLogo());
    	Assert.assertNotNull(offerCategoryBean.getImage());
    	Assert.assertNotNull(offerCategoryBean.getOffers());
    	Assert.assertNotNull(offerCategoryBean.getLogo());
    	Assert.assertNotNull(offerCategoryBean.getCtaAction());
    	Assert.assertNotNull(offerCategoryBean.getCtaLabel());
    	Assert.assertNotNull(offerCategoryBean.getSubhead());
    	Assert.assertNotNull(offerCategoryBean.getHeadline());
    	Assert.assertNotNull(offerCategoryBean.getRank());
    	Assert.assertNotNull(offerCategoryBean.getHeadlineSecondLine());
    	Assert.assertNotNull(offerCategoryBean.getDb2Category());
    	Assert.assertNotNull(offerCategoryBean.getContentPath());
   }
}
