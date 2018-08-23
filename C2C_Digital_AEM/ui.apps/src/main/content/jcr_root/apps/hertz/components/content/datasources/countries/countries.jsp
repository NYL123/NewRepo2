<%@include file="/libs/granite/ui/global.jsp"%>
<%@page session="false" 
    import="com.adobe.granite.ui.components.ds.DataSource,
            com.adobe.granite.ui.components.ds.ValueMapResource,
			java.util.HashMap,
			org.apache.sling.api.wrappers.ValueMapDecorator,
			com.adobe.granite.ui.components.ds.SimpleDataSource,
			org.apache.commons.collections.iterators.TransformIterator,
			java.util.Map,
			java.util.LinkedHashMap,
			org.apache.commons.collections.Transformer,
			java.util.Locale,
			java.util.ArrayList,
			java.util.Collections,
			java.util.Comparator,
			java.util.LinkedHashMap,
			java.util.List,
			java.util.Map.Entry,
			java.util.Set,
			org.apache.sling.api.resource.*"%>
<%
    final Map<String, String> countries = new LinkedHashMap<String, String>();
	countries.put("*","Default");
	String[] locales = Locale.getISOCountries();				
	for (String countryCode : locales) {
	    Locale obj = new Locale("", countryCode);	    
        countries.put(obj.getCountry(),obj.getDisplayCountry(obj));
	 }

    final ResourceResolver resolver = resourceResolver;

    DataSource ds = new SimpleDataSource(new TransformIterator(countries.keySet().iterator(), new Transformer() {
        public Object transform(Object o) {
            String country = (String) o;
            ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());

            vm.put("value", country);
            vm.put("text", countries.get(country));

            return new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm);
        }
    }));

    request.setAttribute(DataSource.class.getName(), ds);
%>
