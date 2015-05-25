<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="resources/Extn/styleDSJV.css" rel="stylesheet" />

<div class="value controlspan">
<hr>
<c:forEach var="sg" items="${securityGroups}"> 
<c:choose>
<c:when test="${(sgForAttr.id)==(sg.id)}">
<input type="radio" name="sgrp" id="sgrp" value="${sg.id}" checked="checked">
<span>${sg.name}</span><br>
</c:when>
<c:otherwise>
<input type="radio" name="sgrp" id="sgrp" value="${sg.id}"> <span>${sg.name}</span><br>
</c:otherwise>
</c:choose>
</c:forEach>
</div>

