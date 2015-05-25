<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<span class="grid-box-header" id="header1title">Download Template
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>

<span class="grid-box-header" id="header2title">Controlled Upload <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>

<span class="grid-box-header" id="header3title">Quick Upload <font class="mand">* Mandatory fields</font> <a
	href='#' class='close-widget-box'><img src="resources/images/wizart/x.png"
		class="float-right" /></a>
</span>

	
<div class="select1">
<div  class="select-box">
	<div id="crNewDiv" style="min-width: 100%; margin: 0 auto; padding: 10px;">
		<c:choose>
			<c:when test="${DT == true }">
				<div id="createDiv">

					<label title="${attr1Name}" for="">Select ${attr1Name}</label>
					<c:choose>
						<c:when test="${attr1Values.size() == 1}">
							<c:forEach var="i" items="${attr1Values}">
								<div id="at1Disp" style="display: inline;">
									<input title="${i.abbreviation}" type="text"
										disabled="disabled" id="attr1Disp" name="${i.id}"
										value="${i.value}" class="text">
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<select name="attr1" id="attr1" class="text"
								onchange="attr1FormSelected();">
								<option value="-1" selected="selected">--Select--</option>
								<c:forEach var="i" items="${attr1Values}">
									<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
								</c:forEach>
							</select>
						</c:otherwise>
					</c:choose>

				</div>

				<div id="attr2Div"></div>
			</c:when>

			<c:when test="${QU == true }">
				<div id="qupldDiv">
					<div id="createDiv">

						<label title="${attr1Name}" for="">Select ${attr1Name}</label>
						<c:choose>
							<c:when test="${attr1Values.size() == 1}">
								<c:forEach var="i" items="${attr1Values}">
									<div id="at1qUplDisp" style="display: inline;">
										<input title="${i.abbreviation}" type="text"
											disabled="disabled" id="attr1qUplDisp" name="${i.id}"
											value="${i.value}" class="text">
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<select name="attr1qUpl" id="attr1qUpl" class="text"
									onchange="attr1qUplFormSelected('${itemId }','${docName }');">
									<option value="-1" selected="selected">--Select--</option>
									<c:forEach var="i" items="${attr1Values}">
										<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>

					</div>

				</div>
				<div id="attr2qUplDiv"></div>
			</c:when>


			<c:otherwise>
				<div id="upldDiv">
					<div id="createDiv">

						<label title="${attr1Name}" for="">Select ${attr1Name}</label>
						<c:choose>
							<c:when test="${attr1Values.size() == 1}">
								<c:forEach var="i" items="${attr1Values}">
									<div id="at1UplDisp" style="display: inline;">
										<input title="${i.abbreviation}" type="text"
											disabled="disabled" id="attr1UplDisp" name="${i.id}"
											value="${i.value}" class="text">
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<select name="attr1Upl" id="attr1Upl" class="text"
									onchange="attr1UplFormSelected('${itemId }','${docName }');">
									<option value="-1" selected="selected">--Select--</option>
									<c:forEach var="i" items="${attr1Values}">
										<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div id="attr2UplDiv"></div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
</div>

					<div class="clear"></div>