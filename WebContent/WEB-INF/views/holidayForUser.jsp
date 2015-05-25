<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>




<script type="text/javascript">
/* 	$('#btnSendEmail').click(function(){	
		alert("inside send");
		loadProgress();
	});	 */
	$(function () {
		$(".datepicker").datepicker({
			dateFormat : 'dd-mm-yy'
			    
		})
	});

	
	function holidayCompleted(result){
		if (result == 1) {
			alert("holiday completed");
			var holidayDialog = $("<div id='MenuDialog' title='Holiday Result'>\<p>\<span class='ui-icon ui-icon-check' "
					+ "style='float: left; margin: 0 7px 20px 0;'> "
					+ "</span>Only this user has the task<b></div>");

			holidayDialog.dialog({
				resizable : false,
				width : 300,
				height : 150,
				modal : true,
				closeOnEscape : false,
				buttons : {
					"Ok" : {
						text : 'Ok',
						/* class : 'butn',
						width : '40px', */
						click : function() {
							$(this).dialog("close");
						}
					}
				}
			});
			
		} else{
			dialogTemplate("Holiday Result", "User has no task");
		} 
		
	}
	</script>
	
	
	
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
<form method="post"  id="frmUserHoliday" >
 <input type="hidden" id="userId" name="userId" value="${userId }"/>
<table id="holidayTable" cellspacing="7">
            <tr>
              <td class="label">From</td>
              <td class="star">*</td>
             <td>
             <input id="holidayDateFrom" name="holidayDateFrom" readonly="readonly"
										type="text" class="text datepicker"  value="${holidayDateFrom}" pattern="dd-MM-yyyy"
										onkeypress="searchKeyPress(event);"
										placeholder="Date" title="From Date"/> </td>
			 <td><div style="color: red;" align="right">${result}</div></td>							
										</tr>			
             <tr>
               <td class="label">To</td>
              <td class="star">*</td>
             <td>
             <input id="holidayDateTo" name="holidayDateTo" readonly="readonly"
										type="text" class="text datepicker"  value="${holidayDateTo}" pattern="dd-MM-yyyy"
										onkeypress="searchKeyPress(event);"
										placeholder="Date" title="To Date"/> </td>	
										
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td>
              <input type="button" class="download" value="Save" onclick="holidayDateSelected();" />
             <input name="" type="button" class="download" value="Back" onclick="showUsers();" />
              </td>
           <%--  </tr>
            <tr>
            <td></td>
            <td></td>
            <td><div style="color: red;" align="right">${result}</div></td>
            </tr> --%>
          </table>
</form>
<div id="hiddenDivUserHoliday">
	<iframe id="hiddenNewFrame" name="hiddenNewFrame" style="display: none">
	</iframe>
</div>