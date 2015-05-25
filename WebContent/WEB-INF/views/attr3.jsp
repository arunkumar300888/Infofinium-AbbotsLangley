<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
<c:when test="${DT == true }">
<div id="at3">
	
			<label  title="${attr3Name}" for="">Select ${attr3Name}</label>
			<c:choose>
					<c:when test="${attr3Values.size() == 1}">
						<c:forEach var="i" items="${attr3Values}">
						<div id="at3Disp" style="display: inline;">
							<input  title="${i.abbreviation}" type="text" disabled="disabled" id="attr3Disp" name="${i.id}" value="${i.value}" class="text">
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
						<select name="attr3" id="attr3" class="text"
							onchange="attr3Selected();">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr3Values}">
								<option  title="${i.abbreviation}" value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>

<div id="attr4Div"></div>
</c:when>

<c:when  test="${QU == true }">

<div id="at3qUpl">
	
			<label title="${attr3Name}" for="">Select ${attr3Name}</label>
			<c:choose>
					<c:when test="${attr3Values.size() == 1}">
						<c:forEach var="i" items="${attr3Values}">
						<div id="at3qUplDisp" style="display: inline;">
							<input title="${i.abbreviation}" type="text" disabled="disabled" id="attr3qUplDisp" name="${i.id}" value="${i.value}" class="text">
						</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<select name="attr3qUpl" id="attr3qUpl" class="text"
							onchange="attr3qUplSelected();">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr3Values}">
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
			<label title="${attr3Name}" for="">Select ${attr3Name}</label>
			<c:choose>
					<c:when test="${attr3Values.size() == 1}">
						<c:forEach var="i" items="${attr3Values}">
						<div id="at3UplDisp" style="display: inline;">
							<input title="${i.abbreviation}" type="text" disabled="disabled" id="attr3UplDisp" name="${i.id}" value="${i.value}" class="text">
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
						<select name="attr3Upl" id="attr3Upl" class="text"
						
							onchange="attr3UplSelected();">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="i" items="${attr3Values}">
								<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
</div>
<div id="attr4UplDiv"></div>
</c:otherwise>

</c:choose>

