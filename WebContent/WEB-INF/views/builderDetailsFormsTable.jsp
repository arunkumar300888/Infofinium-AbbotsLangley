<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(document).ready(function(){
	
	$('#formsList tr').hover(function() {
        $(this).css('cursor','pointer');
    });
	
	
});

function builderMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showBuilderDetails" + "?builderDetailsId="
				+ id,
		cache : false,
		success : function(data) {
			 $("#search").hide();	
			//Next 3 lines for properties page to Dashboard Page
			 $('#csvDownloadSearch').hide();
			$('#dynContentMetadata').hide();
			
			$("#header2").show();				
			$("#dynContent").html(data);
			$('#dynContent').show();
			$("#header3").show();
			
		}
	});
}
</script>

<div >
	<span class="grid-box-header ">Builder Detail <input type="button"
		class="download float-right" onclick="builderMetaData(-1);" style="margin-right: 5%" value="Add New" />
	</span>
	<table id="formsList" class="documents-table display">
		<thead>
			<tr>
				<th>Builder Company</th>
				<th>Builder Name</th>
				<th>Email Address</th>
				<th>Mobile</th>
				<th>Company Number</th>
			</tr>
		</thead>
		<tbody>
			
			<c:forEach var="i" items="${builderList}" varStatus="status">
						<tr onclick="builderMetaData('${i.id}');">
				<td>${i.companyName}</td>
				<td>${i.firstName}</td>
				<td>${i.emailAddress}</td>
				<td>${i.mobileNumber}</td>
				<td>${i.companyNumber}</td>
			</tr>
			</c:forEach>

		</tbody>
	</table>
</div>