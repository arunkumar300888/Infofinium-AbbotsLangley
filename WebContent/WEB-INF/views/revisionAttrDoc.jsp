<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$("#datepickerrev").datepicker({
	dateFormat : 'dd-mm-yy',
	/* showOn : "button",
	buttonImage : "../resources/images/calendar.gif",
	minDate: 0,
	buttonImageOnly : true */
});

$("#datepickerrev").click(function(){
	//alert("doc date");
    $("#datepickerrev").datepicker('show');
});
</script>
		<div id="revisionAttrDoc" align="left">
			<table>	
				<tr valign="top">
					<c:forEach var="i" items="${attr}">
				<c:if test="${i.type=='WF_ATTR_DATE'}">

							<c:choose>
								<c:when test="${i.name=='Target Date'}">
									<td width="123" title="Submission Date" class="value" ><label class="label">Submission
											Date</label>&nbsp;<font color="red">*</font></td>
											<td><input title="Submission Date" type="text"
								name="id_${i.name}" id="datepickerrev" readonly="readonly"
								class="text datepicker" /><br></td>
								</c:when>

								<c:otherwise>
									<td width="123" title="${i.name}" class="value" ><label>${i.name}</label>&nbsp;<font
										color="red">*</font></td>
										<td><input title="Submission Date" type="text"
								name="id_${i.name}" id="datepickerrev" readonly="true"
								class="text datepicker"/><br></td>
								</c:otherwise>
							</c:choose>
						</c:if>
							</c:forEach>
						</tr>
						
						<tr valign="top">
				<td></td>
				<td><input title="Revision Document" type="submit" class="butn" 
					id="revisionAttr" value="Revision"  style="width: 100px;"
					onclick="submitform();"></td>
			</tr>
			</table>
		</div>
	