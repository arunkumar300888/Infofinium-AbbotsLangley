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

function inventoryMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showInventory" + "?inventoryId="
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
				<span class="grid-box-header ">Inventory 
				<input type="button" class="download float-right" style="margin-right:5%" value="Add New" onclick="inventoryMetaData('0')"/>
				</span>
				<table id="formsList" class="documents-table display">
					<thead>
						<tr>
							<th>Property Name</th>
							<th>Room</th>
							<th>Tenant Name</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="i" items="${inventoryList}" varStatus="status">
						<tr onclick="inventoryMetaData('${i.id}');">
							<td>${i.propertyDetailsForm.propertyTitle}</td>
							<td>${i.room}</td>
							<td>${i.tenantName}</td>
							<td>${i.status}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
	</div>