<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${DT == true }">
		<div id="at5">

			<label title="${attr5Name}" for="">Select ${attr5Name}</label>
			<c:choose>
				<c:when test="${attr5Values.size() == 1}">
					<c:forEach var="i" items="${attr5Values}">
						<div id="at5Disp" style="display: inline;">
							<input title="${i.abbreviation}" type="text" id="attr5Disp"
								disabled="disabled" name="${i.id}" value="${i.value}"
								class="text">
						</div>
						<%-- <select name="attr4Disp" id="attr4Disp" style="width: 190px;" onclick="attr4Displayed('${i.id}');">
							<c:forEach var="i" items="${attr4Values}">
								<option value="${i.id}" selected="selected" >${i.value}</option>
							</c:forEach>
						</select> --%>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<select name="attr5" id="attr5" class="text"
						onchange="attr5Selected();">
						<option value="-1" selected="selected">--Select--</option>
						<c:forEach var="i" items="${attr5Values}">
							<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
						</c:forEach>
					</select>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="doctypeDiv"></div>
	</c:when>
	<c:when test="${QU == true }">

		<div id="at5qUpl">

			<label title="${attr5Name}" for="">Select ${attr5Name}</label>
			<c:choose>
				<c:when test="${attr5Values.size() == 1}">
					<c:forEach var="i" items="${attr5Values}">
						<div id="at5qDisp" style="display: inline;">
							<input title="${i.abbreviation}" type="text" id="attr5qUplDisp"
								disabled="disabled" name="${i.id}" value="${i.value}"
								class="text">
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<select name="attr5qUpl" id="attr5qUpl" class="text"
						onchange="attr5qUplSelected();">
						<option value="-1" selected="selected">--Select--</option>
						<c:forEach var="i" items="${attr5Values}">
							<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
						</c:forEach>
					</select>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="doctypeqUplDiv"></div>

	</c:when>

	<c:otherwise>
		<div id="at5Upl">

			<label title="${attr5Name}" for="">Select ${attr5Name}</label>
			<c:choose>
				<c:when test="${attr5Values.size() == 1}">
					<c:forEach var="i" items="${attr5Values}">
						<div id="at5Disp" style="display: inline;">
							<input title="${i.abbreviation}" type="text" id="attr5UplDisp"
								disabled="disabled" name="${i.id}" value="${i.value}"
								class="text">
						</div>
						<%-- <select name="attr4Disp" id="attr4Disp" style="width: 190px;" onclick="attr4Displayed('${i.id}');">
							<c:forEach var="i" items="${attr4Values}">
								<option value="${i.id}" selected="selected" >${i.value}</option>
							</c:forEach>
						</select> --%>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<select name="attr5Upl" id="attr5Upl" class="text"
						onchange="attr5UplSelected();">
						<option value="-1" selected="selected">--Select--</option>
						<c:forEach var="i" items="${attr5Values}">
							<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
						</c:forEach>
					</select>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="doctypeUplDiv"></div>
	</c:otherwise>

</c:choose>