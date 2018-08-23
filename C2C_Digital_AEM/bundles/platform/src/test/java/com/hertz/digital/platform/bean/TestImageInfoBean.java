package com.hertz.digital.platform.bean;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.factory.HertzConfigFactory;
import com.hertz.digital.platform.utils.HertzUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, HertzUtils.class })
public class TestImageInfoBean {

	@Mock
	private Resource compResource;

	@Mock
	private Resource onChildResource;

	@Mock
	private Resource childResource;

	@Mock
	private Resource childResource1;

	@Mock
	private ValueMap valueMap;

	@Mock
	private ValueMap childMap;

	@Mock
	private ValueMap childMap2;
	@Mock
	HertzConfigFactory hFactory;

	public static final String standardFileReference = "fileReference";

	public static final String nonStandardFileReference = "somethingElse";

	public static final String imagePath1 = "/foo/bar.png";

	public static final String imagePath2 = "/bar/foo.png";

	public static final String componentPath = "/content/foo/bar";

	public static final String relativeChildPath = "child";

	public static final String childPath = "/content/child/foo/bar";

	public static final String imagePath3 = "/child/foo/bar.png";

	@Test
	public void test() {

		PowerMockito.mockStatic(HertzUtils.class);
		when(HertzUtils.getServiceReference(HertzConfigFactory.class)).thenReturn(hFactory);

		when(valueMap.get(standardFileReference, String.class)).thenReturn(imagePath1);
		when(valueMap.get(nonStandardFileReference, String.class)).thenReturn(imagePath2);

		when(compResource.getPath()).thenReturn(componentPath);

		valueMap = Mockito.mock(ValueMap.class);
		when(compResource.getValueMap()).thenReturn(valueMap);
		when(valueMap.get(standardFileReference, String.class)).thenReturn(imagePath1);
		when(childMap.get(standardFileReference, String.class)).thenReturn(imagePath3);

		when(childMap.get(standardFileReference, String.class)).thenReturn(imagePath3);

		when(onChildResource.getPath()).thenReturn(componentPath);
		when(onChildResource.hasChildren()).thenReturn(true);
		when(onChildResource.getChild(relativeChildPath)).thenReturn(childResource);

		when(childResource.getValueMap()).thenReturn(childMap);

		ImageInfoBean testBean = new ImageInfoBean(compResource);

		Assert.assertEquals(testBean.getSources().size(), 2);
		Map<String, Object> testImages1 = testBean.getSources().get(0);
		Assert.assertNotNull(testImages1);
		Assert.assertEquals((String) testImages1.get("size"), "small");
		List<Object> renditions1 = (List<Object>) testImages1.get("renditions");
		Assert.assertEquals(renditions1.size(), 2);
		Map<String, String> rend1 = (Map<String, String>) renditions1.get(0);
		Assert.assertEquals(rend1.get("density"), "1x");
		// Assert.assertEquals(rend1.get("src"), imagePath1);

		ImageInfoBean testBean2 = new ImageInfoBean(compResource, nonStandardFileReference, false);

		Assert.assertEquals(testBean2.getSources().size(), 2);
		Map<String, Object> testImages2 = testBean2.getSources().get(0);
		Assert.assertNotNull(testImages2);
		Assert.assertEquals((String) testImages2.get("size"), "small");
		List<Object> renditions2 = (List<Object>) testImages2.get("renditions");
		Assert.assertEquals(renditions2.size(), 2);
		Map<String, String> rend2 = (Map<String, String>) renditions2.get(0);
		Assert.assertEquals(rend2.get("density"), "1x");
		ImageInfoBean testBean3 = new ImageInfoBean(null, nonStandardFileReference, false);
		ImageInfoBean testBean4 = new ImageInfoBean(compResource, StringUtils.EMPTY, false);
		when(compResource.hasChildren()).thenReturn(true);
		when(compResource.getChild(nonStandardFileReference)).thenReturn(null);
		ImageInfoBean testBean5 = new ImageInfoBean(compResource, nonStandardFileReference, true);
		when(compResource.getChild(nonStandardFileReference)).thenReturn(childResource1);
		when(childResource1.getValueMap()).thenReturn(childMap2);
		when(childMap2.get("fileReference", String.class)).thenReturn(StringUtils.EMPTY);
		ImageInfoBean testBean6 = new ImageInfoBean(compResource, nonStandardFileReference, true);
		when(compResource.hasChildren()).thenReturn(false);
		ImageInfoBean testBean7 = new ImageInfoBean(compResource, nonStandardFileReference, true);
		Assert.assertNotNull(testBean7.getLarge1xPath());
		Assert.assertNotNull(testBean7.getLarge2xPath());
		Assert.assertNotNull(testBean7.getSmall1xPath());
		Assert.assertNotNull(testBean7.getSmall2xPath());

	}

	@Test
	public void testVariables() {
		ImageInfoBean testBean2 = new ImageInfoBean(null);
		testBean2.setTitle("title");
		Assert.assertTrue(testBean2.getTitle().equalsIgnoreCase("title"));
	}

}
