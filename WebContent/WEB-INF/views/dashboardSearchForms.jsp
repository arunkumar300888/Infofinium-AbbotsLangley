<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <span id="dashboardFormSearch"> -->
<input type="text" id="datepicker3" class="text datepicker" size="10"
	maxlength="120" readonly="readonly" name="relevantdatefrom"
	onkeypress="searchKeyPress(event);"
	
	placeholder="Required From Date"
	style="vertical-align: top; margin-top: 7px;" />
<input type="text" id="datepicker4" class="text datepicker" size="10"
	maxlength="120" readonly="readonly" name="relevantdateto"
	onkeypress="searchKeyPress(event);"
	onchange="colorChangerelevantdateto();" placeholder="Required To Date"
	style="vertical-align: top; margin-top: 7px;" />
<select name="formType" id="formType"
	onkeypress="searchKeyPress(event);" title="Form Type" class="text">
	<option value="-1" selected="selected">--Select--</option>
	<c:forEach var="i" items="${formDefinitions}" varStatus="status">
		<option value="${i.id}">${i.name} - ${i.description}</option>
	</c:forEach>
</select>
<input type="text" class="text" name="specificdata" id="specificData"
	placeholder="Specific Data" title="Specific Data"
	style="vertical-align: top; margin-top: 7px;">
<!-- </span> -->

<script>
	$(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd-mm-yy'
		});
	});
</script>




