<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <span id="dashboardDocumentSearch"> -->
<input type="text" id="datepicker3" class="text datepicker" size="10" maxlength="120" 
							readonly="readonly" name="relevantdatefrom"
							onkeypress="searchKeyPress(event);"
							 title="Submission From Date"  placeholder="Submission From Date" style="vertical-align:top; margin-top:7px;"  />
							<input type="text"
							id="datepicker4" class="text datepicker" size="10" maxlength="120" readonly="readonly"
							name="relevantdateto" onkeypress="searchKeyPress(event);"
							 title="Submission From Date"  placeholder="Submission From Date" style="vertical-align:top; margin-top:7px;"/>
     <select name="documentType" id="documentType"
				onkeypress="searchKeyPress(event);" title="Document Type" class="text" >
					<option value="-1" selected="selected">--Select Doctype--</option>
					<c:forEach var="i" items="${doctypes}">
						<option value="${i.id}">${i.doctypeName}</option>
					</c:forEach>
			</select>
			
           <select name="metadata1" id="metadata1"
				onkeypress="searchKeyPress(event);" title="${attr1Search}" class="text">
					<option value="-1" selected="selected">--Select Company--</option>
					<c:forEach var="i" items="${metadata1}">
						<option value="${i.id }">${i.value}</option>
					</c:forEach>
			</select>		
<select name="metadata2" id="metadata2"
				onkeypress="searchKeyPress(event);" title="${attr2Search}" class="text">
					<option value="-1" selected="selected">--Select Department--</option>
					<c:forEach var="i" items="${metadata2}">
						<option value="${i.id }">${i.value}</option>
					</c:forEach>
			</select>
  <select name="metadata3" id="metadata3"
				onkeypress="searchKeyPress(event);" title="${attr3Search}" class="text">
					<option value="-1" selected="selected">--Select Area--</option>
					<c:forEach var="i" items="${metadata3}">
						<option value="${i.id}">${i.value}</option>
					</c:forEach>
			</select>
			
<%-- <select name="metadata4" id="metadata4"
				onkeypress="searchKeyPress(event);" title="${attr4Search}" class="text">
					<option value="-1" selected="selected">--Select--</option>
					<c:forEach var="i" items="${metadata4}">
						<option value="${i.id}">${i.value}</option>
					</c:forEach>
			</select> --%>
			<!-- </span> -->
<script>
	$(function() {
		
		$(".datepicker").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		/* $("#datepicker3").datepicker({
			dateFormat : 'dd-mm-yy',
			showOn : "button",
			buttonImage : "resources/images/calendar.gif",
			buttonImageOnly : true
		});

		$('#datepicker3').click(function() {
			$('#datepicker3').datepicker('show');
		});

		$("#datepicker4").datepicker({
			dateFormat : 'dd-mm-yy',
			showOn : "button",
			buttonImage : "resources/images/calendar.gif",
			buttonImageOnly : true
		});

		$('#datepicker4').click(function() {
			$('#datepicker4').datepicker('show');
		}); */
	});
</script>

