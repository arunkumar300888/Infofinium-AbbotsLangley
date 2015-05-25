<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id='_popupDialog' class='popupDialog'>
	<form id='_popupDialogForm'>
		<table class='dialog'>
			<tr>
				<td class='dialogTitle' style='text-align:center;'><b>${title}</b></td>
			</tr>

			<tr>
				<td id="_popupDialogContent" style='text-align:center;font-size: smaller'>

					<div style="width:250px; padding:5px">
						<jsp:include page="${viewContent}" />
					</div>

				</td>
			</tr>

			<tr>
				<td style='text-align:center;'>
					<p style="margin-top:10px;">
						
						<input type='button' class="butn"  style='width:100px;' id="_popupdialog_close" name='_popupdialog_close' value='Close' onclick='closePopupDialog();' />
						<input type='button' class="butn"  style='width:100px;' id="_popupdialog_ok" name='_popupdialog_ok' value='Ok' onclick='_popupDialogOk();' />
					</p>
				</td>
			</tr>
			<tr>
				<td style='text-align:right;'>&nbsp;</td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
//<![CDATA[
	var _popupDialogAjaxField = "${popupDialogAjaxField}";

	function _popupDialogInit()
	{
		_popupDialogContentInit(_popupDialogAjaxField);
	}

	function _popupDialogOk()
	{
		_popupDialogContentOk(_popupDialogAjaxField);
		try
		{
			var c = document.getElementById(_popupDialogAjaxField);
			c.validateField(c);
		}
		catch(e)
		{
		}

		closePopupDialog();
	}
	
	_popupDialogInit();
//]]>
</script>
