<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>




	$(document).ready(function() {

		$('#formsList tr').hover(function() {
			$(this).css('cursor', 'pointer');
		});

		$('#formsList').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"iDisplayLength" : 25,
			"bFilter" : true

		});
	});

	function viewRentReceipt(userId) {
		/* $.ajax({
			type : "GET",
			url : "forms/viewRentPaymentTransactionHistorybyUserID" + "?userId="
					+ userId,
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
		}); */
		var url = "forms/viewRentPaymentTransactionHistorybyUserID"
				+ "?userId=" + userId + "?TB_iframe=true&height=585&width=900";

		tb_show('Receipt', url, null);
		//alert(url);
		/*  window.open(url,"Rent Receipt",'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes'); */
	}

	function rentAccountsMetaData() {
		$.ajax({
			type : "GET",
			url : "forms/showRentAccountsForm",
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

	function GenericRentReceiptDetailsClick(tenancy) {
		//alert("genericRentReceiptDetails clicked");	
		//loadProgress();

		/* $("#genericRent")
				.attr("action",
						"forms/pdfDownloadForm?downloadTypehidden=Generic Rent Receipt");
		$("#genericRent").submit(); */
		/* var gen="Generic Rent Receipt";
		var url = "forms/csvOfgetCsvofTransactionDetails";

		var win= window.open(url,"Rent Info",'');
		 setTimeout(function(){ win.close()}, 3000); */
		 var url = "forms/generateRentReceiptbyUserID?tenancy="+tenancy+"?TB_iframe=true&height=585&width=900";

		tb_show('Receipt', url, null);
		 /*  $.ajax({
				type : "GET",
				url : "forms/csvOfgetCsvofTransactionDetails",
				cache : false,
				success : function(data) {
					
				alert("Successful");
				}
			}); */
	
	}
</script>
<div >
	<span class="grid-box-header ">Rent Receipt <input
		class="download float-right" type="button" id="genericRent"
		value="Generate Rent Receipt"
		onclick="GenericRentReceiptDetailsClick('${tenancyId}')" />
	</span>

	<table id="formsList" class="documents-table display">
		<thead>
			<tr>
				<th>Property Name</th>
				<th>Tenant Name</th>
				<th>Transaction Type</th>
				<th>Amount</th>
				<th>View</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${rentReceipts}" varStatus="status">
				<tr>
					<td>${i.propertyId.propertyTitle}</td>
					<td>${i.tenancyId.tenantFirstName}</td>
					<td>${i.paymenttype}</td>
					<td>${i.rent_amount}</td>
					<td><a href="#" onclick="viewRentReceipt('${i.userId}');">View</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>