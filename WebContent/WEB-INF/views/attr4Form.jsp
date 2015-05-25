<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
<c:when test="${DT == true }">
<div id="at4">
	
			<label  title="${attr4Name}" for="">Select ${attr4Name}</label>
			<c:choose>
				<c:when test="${attr4Values.size() == 1}">
				<c:forEach var="i" items="${attr4Values}">
				<div id="at4Disp" style="display: inline;">
					<input  title="${i.abbreviation}" type="text" id="attr4Disp" disabled="disabled"  name="${i.id}" value="${i.value}" class="text">
				</div>
					<%-- <select name="attr4Disp" id="attr4Disp" style="width: 190px;" onclick="attr4Displayed('${i.id}');">
							<c:forEach var="i" items="${attr4Values}">
								<option value="${i.id}" selected="selected" >${i.value}</option>
							</c:forEach>
						</select> --%>	
				</c:forEach>
					</c:when>
					<c:otherwise>
						<select name="attr4" id="attr4" class="text" onchange="attr4qUplFormSelected('${itemId }','${docName }');">
						<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr4Values}">
								<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>
<div id="attr5Div"></div>
</c:when>
<c:when test="${QU == true }">

<div id="at4qUpl">
	
			<label title="${attr4Name}" for="">Select ${attr4Name}</label>
			<c:choose>
				<c:when test="${attr4Values.size() == 1}">
				<c:forEach var="i" items="${attr4Values}">
				<div id="at4qDisp" style="display: inline;">
					<input title="${i.abbreviation}" type="text" id="attr4qUplDisp" disabled="disabled"  name="${i.id}" value="${i.value}" class="text">
				</div>
				</c:forEach>
					</c:when>
					<c:otherwise>
						<select name="attr4qUpl" id="attr4qUpl" class="text" onchange="attr4qUplFormSelected('${itemId }','${docName }');">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr4Values}">
								<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>
<div id="attr5qUplDiv"></div>

</c:when>

<c:otherwise>
<div id="at4Upl">
	
			<label title="${attr4Name}"  for="">Select ${attr4Name}</label>
			<c:choose>
				<c:when test="${attr4Values.size() == 1}">
				<c:forEach var="i" items="${attr4Values}">
				<div id="at4Disp"  style="display: inline;">
					<input title="${i.abbreviation}" type="text" id="attr4UplDisp" disabled="disabled"  name="${i.id}" value="${i.value}" class="text">
				</div>
					<%-- <select name="attr4Disp" id="attr4Disp" style="width: 190px;" onclick="attr4Displayed('${i.id}');">
							<c:forEach var="i" items="${attr4Values}">
								<option value="${i.id}" selected="selected" >${i.value}</option>
							</c:forEach>
						</select> --%>	
				</c:forEach>
					</c:when>
					<c:otherwise>
						<select name="attr4Upl" id="attr4Upl" class="text" onchange="attr4UplFormSelected('${itemId }','${docName }');">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr4Values}">
								<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>
<div id="attr5UplDiv"></div>
</c:otherwise>

</c:choose>