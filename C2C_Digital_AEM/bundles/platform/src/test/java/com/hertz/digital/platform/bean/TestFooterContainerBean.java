package com.hertz.digital.platform.bean;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
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
public class TestFooterContainerBean {

	@InjectMocks
	private FooterContainerBean mockFooterContainerBean;

	@Mock
	FooterColumnBean mockFooterLinkBean;

	@Mock
	SignUpForEmailBean signUpForEmailBean;

	@Mock
	SocialLinksContentBean socialLinksContentBean;

	@Mock
	LegalLinksBean legalLinksBean;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LoggerFactory.class);
		mockFooterContainerBean = Mockito.mock(FooterContainerBean.class);
		List<FooterColumnBean> mockList1 = new ArrayList<FooterColumnBean>();
		List<SignUpForEmailBean> mockList2 = new ArrayList<SignUpForEmailBean>();
		List<SocialLinksContentBean> mockList3 = new ArrayList<SocialLinksContentBean>();
		List<LegalLinksBean> mockList4 = new ArrayList<LegalLinksBean>();
		mockList1.add(mockFooterLinkBean);
		mockList2.add(signUpForEmailBean);
		mockList3.add(socialLinksContentBean);
		mockList4.add(legalLinksBean);
		Mockito.doCallRealMethod().when(mockFooterContainerBean).setLegalDescriptionText(Mockito.anyString());
		Mockito.doCallRealMethod().when(mockFooterContainerBean).setFooterlinks(mockList1);
		Mockito.doCallRealMethod().when(mockFooterContainerBean).setLegalLinks(mockList4);
		Mockito.doCallRealMethod().when(mockFooterContainerBean).setSignUpForEmail(mockList2);
		Mockito.doCallRealMethod().when(mockFooterContainerBean).setSocialLinks(mockList3);
		Mockito.doCallRealMethod().when(mockFooterContainerBean).getFooterlinks();
		Mockito.doCallRealMethod().when(mockFooterContainerBean).getLegalDescriptionText();
		Mockito.doCallRealMethod().when(mockFooterContainerBean).getLegalLinks();
		Mockito.doCallRealMethod().when(mockFooterContainerBean).getSignUpForEmail();
		Mockito.doCallRealMethod().when(mockFooterContainerBean).getSocialLinks();
		mockFooterContainerBean.setFooterlinks(mockList1);
		mockFooterContainerBean.setLegalDescriptionText("legalDescriptionText");
		mockFooterContainerBean.setLegalLinks(mockList4);
		mockFooterContainerBean.setSignUpForEmail(mockList2);
		mockFooterContainerBean.setSocialLinks(mockList3);
	}

	@Test
	public void testConstructorIsPrivate() throws Exception {
		Constructor<FooterContainerBean> constructor = FooterContainerBean.class.getDeclaredConstructor();
		assertTrue(Modifier.isPublic(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public final void test() {
		Assert.assertNotNull(mockFooterContainerBean.getLegalDescriptionText());
		Assert.assertNotNull(mockFooterContainerBean.getFooterlinks());
		Assert.assertNotNull(mockFooterContainerBean.getLegalLinks());
		Assert.assertNotNull(mockFooterContainerBean.getSignUpForEmail());
		Assert.assertNotNull(mockFooterContainerBean.getSocialLinks());
	}
}
