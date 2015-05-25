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

function jobDetailsMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showJobDetails" + "?jobId="
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
				<span class="grid-box-header ">New Job 
				<input type="button" class="download float-right" onclick="jobDetailsMetaData('0');" style="margin-right:5%" value="Add New"/>
				</span>
				<table id="formsList" class="documents-table display">
					<thead>
						<tr>
							<th>Job Number</th>
							<th>Description</th>
							<th>Property</th>
							<th>Tenancy</th>
							<th>Builder</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="i" items="${jobsList}" varStatus="status">
						<tr onclick="jobDetailsMetaData('${i.id}');">
							<td>${i.jobNumber}</td>
							<td>${i.descriptionOfJob}</td>
							<td>${i.linkOfPropertyForm}</td>
							<td>${i.linkOfTenantForm}</td>
							<td>${i.builder.userName}</td>
							<td>${i.status}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
	</div>