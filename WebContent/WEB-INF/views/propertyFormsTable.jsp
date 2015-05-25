<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script>
$(document).ready(function(){
	
	$('#formsList tr').hover(function() {
        $(this).css('cursor','pointer');
    });
	
	$('#formsList').dataTable({
        "bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"iDisplayLength" : 25,
		"bFilter" : true

    });
});

function propertyDetailsMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showPropertyDetails" + "?propertyId="
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
				<span class="grid-box-header ">${taskName} 
				<input type="button" class="download float-right" onclick="propertyDetailsMetaData('0');" style="margin-right:5%" value="Add New"/>
				</span>
				<table id="formsList" class="documents-table display">
					<thead>
						<tr>
							<th>Property Name</th>
							<th>Client</th>
							<th>Name</th>
							<th>Property Type</th>
							<th>Rental Type</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="i" items="${propertiesList}" varStatus="status">
						<tr onclick="propertyDetailsMetaData('${i.id}');">
							<td>${fn:toUpperCase(i.propertyTitle)}</td>
							<td>${fn:toUpperCase(i.client)}</td>
							<td>${i.firstName}</td>
							<td>${i.propertyType}</td>
							<td>${i.rentalType}</td>
							<td>${i.status}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
	</div>