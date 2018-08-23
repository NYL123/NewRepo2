<%@include file="/libs/foundation/global.jsp" %>
<b> Link Component</b> <br/>
<c:if test="${empty properties.linktext}">
    Please provide content for the component
</c:if> 
<c:if test="${not empty properties.linktext}">
${properties.linktext}<br/>

${properties.linkurl}<br/>

</c:if> 


