<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

function tenancyMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showTenancy" + "?tenancyId="
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
<div  >
				<span class="grid-box-header ">Tenancy  
				<input type="button" class="download float-right" style="margin-right:5%" value="Add New" onclick="tenancyMetaData('0')"/>
				</span>
				<table id="formsList" class="documents-table display">
					<thead>
						<tr>
							<th>Property</th>
							<th>Tenant Name</th>
							<th>Mobile Number</th>
							<th>Email Address</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="i" items="${tenancyList}" varStatus="status">
						<tr onclick="tenancyMetaData('${i.id}');">
							<td>${fn:toUpperCase(i.propertyDetailsForm.propertyTitle)}</td>
							<td>${i.tenantFirstName}</td>
							<td>${i.tenantMobileNumber}</td>
							<td>${i.tenantEmailAddress}</td>
							<td>${i.status}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
	</div>