<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <!-- <script>
	$(function() {
		
		var isVisibleQupl = $('#attr2qUpl').is(':visible');		
		if(isVisibleQupl)
			{
		
		$('#attr2qUpl option[value="2"]').attr("selected",true);	
		attr2qUplSelected();
		$("#attr2qUpl").attr('disabled','disabled');
			}
	
		else
			{
			$('#attr2Upl option[value="3"]').attr("selected",true);	
			attr2UplSelected();
			$("#attr2Upl").attr('disabled','disabled');
			}
			
	});	
</script> -->
<c:choose>
	<c:when test="${DT == true }">
		<div id="at2">
			
					<label title="${attr2Name}"  for="">Select ${attr2Name}</label>
					<c:choose>
							<c:when test="${attr2Values.size() == 1}">
								<c:forEach var="i" items="${attr2Values}">
									<div id="at2Disp" style="display: inline;">
										<input title="${i.abbreviation}"  type="text" disabled="disabled" id="attr2Disp"
											name="${i.id}" value="${i.value}" class="text">
									</div>
								</c:forEach>
								<%-- <select name="attr2" id="attr2" style="width: 190px;" onchange="attr2Selected();">
							<c:forEach var="i" items="${attr2Values}">
								<option value="${i.id}">${i.value}</option>
							</c:forEach>
						</select> --%>
							</c:when>
							<c:otherwise>
								<select  name="attr2" id="attr2" class="text"
									onchange="attr2FormSelected();">
									<option value="-1" selected="selected">--Select--</option>
									<c:forEach var="i" items="${attr2Values}">
										<option  title="${i.abbreviation}" value="${i.id}">${i.value}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
		</div>

		<div id="attr3Div"></div>
	</c:when>
	<c:when  test="${QU == true }">
	<div id="atqUpl2">
			
					<label title="${attr2Name}" for="">Select ${attr2Name}</label>
					<c:choose>
							<c:when test="${attr2Values.size() == 1}">
								<c:forEach var="i" items="${attr2Values}">
									<div id="at2qUplDisp" style="display: inline;">
										<input title="${i.abbreviation}" type="text" disabled="disabled" id="attr2qUplDisp"
											name="${i.id}" value="${i.value}" class="text">
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>							
								<select name="attr2qUpl" id="attr2qUpl" class="text" onchange="attr2qUplFormSelected('${itemId }','${docName }');">
								<option value="-1" selected="selected">--Select--</option>
									<c:forEach var="i" items="${attr2Values}">
										<option title="${i.abbreviation}" value="${i.id}">${i.value}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
		</div>

		<div id="attr3qUplDiv"></div>
	
	</c:when>
	
	
	<c:otherwise>
		<div id="atUpl2">
			
					<label title="${attr2Name}" for="">Select ${attr2Name}</label>
					<c:choose>
							<c:when test="${attr2Values.size() == 1}">
								<c:forEach var="i" items="${attr2Values}">
									<div id="at2UplDisp" style="display: inline;">
										<input title="${i.abbreviation}"  type="text" disabled="disabled" id="attr2UplDisp"
											name="${i.id}" value="${i.value}" class="text">
									</div>
								</c:forEach>
								<%-- <select name="attr2" id="attr2" style="width: 190px;" onchange="attr2Selected();">
									<c:forEach var="i" items="${attr2Values}">
										<option value="${i.id}">${i.value}</option>
									</c:forEach>
								</select> --%>
							</c:when>
							<c:otherwise>
								<select name="attr2Upl" id="attr2Upl" class="text"	onchange="attr2UplFormSelected('${itemId }','${docName }');">
								<option value="-1" selected="selected">--Select--</option>
									<c:forEach var="i" items="${attr2Values}">
										<option title="${i.abbreviation}"  value="${i.id}">${i.value}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
		</div>

		<div id="attr3UplDiv"></div>
	</c:otherwise>

</c:choose>

