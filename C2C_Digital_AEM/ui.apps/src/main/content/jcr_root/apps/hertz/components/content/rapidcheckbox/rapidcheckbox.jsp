<%@include file="/libs/foundation/global.jsp" %>
<b> Checkbox Component</b> <br/>
<c:if test="${empty properties.linktarget}">
    Please provide content for the component
</c:if> 
<c:if test="${not empty properties.linktarget}">

${properties.optiononetext}<br/>
${properties.optiontwotext}<br/>
${properties.linktarget}<br/>
</c:if> 


