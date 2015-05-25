<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(document).ready(function(){
	
	$('#formsList tr').hover(function() {
        $(this).css('cursor','pointer');
    });
	
	$('#formsList').dataTable( {
        "bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"iDisplayLength" : 25,
		"bFilter" : true

    } );
});

function checkingOutMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showCheckingOut" + "?checkingoutId="
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
	<span class="grid-box-header ">Checking Out Form  
	<input type="button" class="download float-right" style="margin-right: 5%"  onclick="checkingOutMetaData('0');" value="Add New" />
	</span>
	<table id="formsList" class="display">
		<thead>
			<tr>
				<th>Property</th>
				<th>Tenancy</th>
				<th>Tenant Name</th>
				<th>Name Of Employee</th>
				<th>Rented Property Address</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${checkingOutList}" varStatus="status">
						<tr onclick="checkingOutMetaData('${i.id}');">
				<td>${i.propertyDetailsForm.propertyTitle}</td>
				<td>${i.tenancy}</td>
				<td>${i.tenantName}</td>
				<td>${i.nameOfEmployees}</td>
				<td>${i.rentedPropertyAddress}</td>
				
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>