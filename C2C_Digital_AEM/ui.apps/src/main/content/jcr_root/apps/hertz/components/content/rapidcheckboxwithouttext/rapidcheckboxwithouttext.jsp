<%@include file="/libs/foundation/global.jsp" %>
<b> Checkbox without Text Component</b> <br/>
<c:if test="${empty properties.chekbox}">
    Please provide content for the component
</c:if> 
<c:if test="${not empty properties.chekbox}">

${properties.chekbox}<br/>

</c:if> 


