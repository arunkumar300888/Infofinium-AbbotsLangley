<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
<c:when test="${DT == true }">
<div id="at4">
	
			<label  title="${attr4Name}" for="">Select ${attr4Name}</label>
			<c:choose>
					<c:when test="${attr4Values.size() == 1}">
						<c:forEach var="i" items="${attr4Values}">
						<div id="at4Disp" style="display: inline;">
							<input  title="${i.abbreviation}" type="text" disabled="disabled" id="attr4Disp" name="${i.id}" value="${i.value}" class="text">
						</div>
						</c:forEach>
					<%--<select name="attr3" id="attr3" style="width: 190px;"
							onblur="attr3Selected();">
							<c:forEach var="i" items="${attr3Values}">
								<option value="${i.id}">${i.value}</option>
							</c:forEach>
						</select> --%>
					</c:when>
					<c:otherwise>
						<select name="attr4" id="attr4" class="text"
							onchange="attr4Selected();">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr4Values}">
								<option  title="${i.abbreviation}" value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>

<div id="attr5Div"></div>
</c:when>

<c:when  test="${QU == true }">

<div id="at4qUpl">
	
			<label title="${attr4Name}" for="">Select ${attr4Name}</label>
			<c:choose>
					<c:when test="${attr4Values.size() == 1}">
						<c:forEach var="i" items="${attr4Values}">
						<div id="at4qUplDisp" style="display: inline;">
							<input title="${i.abbreviation}" type="text" disabled="disabled" id="attr4qUplDisp" name="${i.id}" value="${i.value}" class="text">
						</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<select name="attr4qUpl" id="attr4qUpl" class="text"
							onchange="attr4qUplSelected();">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr4Values}">
								<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>
<div id="attr4qUplDiv"></div>
</c:when>

<c:otherwise>
<div id="at3Upl">
			<label title="${attr4Name}" for="">Select ${attr4Name}</label>
			<c:choose>
					<c:when test="${attr4Values.size() == 1}">
						<c:forEach var="i" items="${attr4Values}">
						<div id="at4UplDisp" style="display: inline;">
							<input title="${i.abbreviation}" type="text" disabled="disabled" id="attr4UplDisp" name="${i.id}" value="${i.value}" class="text">
						</div>
						</c:forEach>
						<%-- <select name="attr3" id="attr3" style="width: 190px;"
							onblur="attr3Selected();">
							<c:forEach var="i" items="${attr3Values}">
								<option value="${i.id}">${i.value}</option>
							</c:forEach>
						</select> --%>
					</c:when>
					<c:otherwise>
						<select name="attr4Upl" id="attr4Upl" class="text"
						
							onchange="attr4UplSelected();">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr3Values}">
								<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>
<div id="attr5UplDiv"></div>
</c:otherwise>

</c:choose>

