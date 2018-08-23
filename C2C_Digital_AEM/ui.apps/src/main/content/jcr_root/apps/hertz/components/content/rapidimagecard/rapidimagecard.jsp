${properties.alttext}<br/><%@include file="/libs/foundation/global.jsp" %>
<b> Rapid Banner Component</b> <br/>
<c:if test="${empty properties.fileReference}">
    Please provide content for the component
</c:if> 
<c:if test="${not empty properties.fileReference}">
    <b>Image :</b> ${properties.fileReference}<br/>
    <b>Alt Text:</b> ${properties.alttext}<br/>
    <b>Image Tagline Text :</b>${properties.taglineText}<br/>
    <b>Image Description Text :</b>${properties.descriptionText}<br/>
    <b>Add to Profile Text :</b>${properties.addToProfileText}<br/>
</c:if>

