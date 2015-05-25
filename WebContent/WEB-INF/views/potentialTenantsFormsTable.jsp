<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(document).ready(function(){
	
	$('#formsList tr').hover(function() {
        $(this).css('cursor','pointer');
    });
	
	
});

function potentialMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showPotentialTenant" + "?potentialTenantId="
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
	<span class="grid-box-header ">Potential Tenant <input type="button"
		class="download float-right" onclick="potentialMetaData(0);" style="margin-right: 5%" value="Add New" />
	</span>
	<table id="formsList" class="display">
		<thead>
			<tr>
				<th>Tenant Name</th>
				<th>Mobile</th>
				<th>Email Address</th>
				<th>Type</th>
				<th>No.Of Bedrooms</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
			
			<c:forEach var="i" items="${potentialTenantList}" varStatus="status">
						<tr onclick="potentialMetaData('${i.id}');">
			
				<td>${i.firstName}</td>
				<td>${i.mobileNumber}</td>
				<td>${i.emailAddress}</td>
				<td>${i.type}</td>
				<td>${i.numberOfBedRooms}</td>
				<td>${i.status}</td>
			</tr>
</c:forEach>
		</tbody>
	</table>
</div>