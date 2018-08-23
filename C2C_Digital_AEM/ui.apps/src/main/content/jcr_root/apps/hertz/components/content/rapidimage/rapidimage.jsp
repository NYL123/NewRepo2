<%@include file="/libs/foundation/global.jsp" %>
<b> Image Component</b> <br/>
<c:if test="${empty properties.fileReference}">
    Please provide content for the component
</c:if> 
<c:if test="${not empty properties.fileReference}">

${properties.fileReference}<br/>
 ${properties.imagetext}<br/>

</c:if>
