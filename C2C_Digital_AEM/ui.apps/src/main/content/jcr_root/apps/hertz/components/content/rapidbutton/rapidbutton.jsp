<%@include file="/libs/foundation/global.jsp" %>
<b> Create Button Component</b> <br/>
<c:if test="${empty properties.buttontext}">
    Please provide content for the component
</c:if> 
<c:if test="${not empty properties.buttontext}">

${properties.buttontext}<br/>
${properties.linkurl}<br/>
${properties.linktarget}<br/>
</c:if> 


