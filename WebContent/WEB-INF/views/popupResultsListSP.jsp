<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
//<![CDATA[
	function _popupDialogContentOk(ajaxControl)
	{
		var dest = document.getElementById(ajaxControl);
		var lb = document.getElementById("_pd_data_listboxSP");
		var ef = document.getElementById("_pd_data_entrySP");
		var qf = document.getElementById("_pd_qty_entrySP");
		try
		{
			var s = null;
			if (ef != null)
			{
				s = trim(ef.value);
			}

			if (s == null)
			{
				if (lb.selectedIndex != -1 && lb.type == "select-one")
				{
					s = lb.options[lb.selectedIndex].value;
				}
			}

			if (dest.type == "select-one" || dest.type == "select-multiple")
			{
				if(s != null)
				{
					_popupDialogAddOption(dest, s.split("[#")[0] + " [#" + (1*trim(qf.value)) + "]");
				}
				else
				{
					var j = 0;
					for(j=0; j<lb.options.length; j++)
					{
						if(lb.options[j].selected)
						{
							_popupDialogAddOption(dest, lb.options[j].value.split("[#")[0] + " [#" + (1*trim(qf.value)) + "]");
						}
					}
				}
			}
			else if (s != null && (dest.type == "text" || dest.type == "password"))
			{
					dest.value = s + " [" + (1*trim(qf.value)) + "]";
			}
		}
		catch (e)
		{
			alert("Exception " + e);
		}
	}

	function _popupDialogAddOption(dest, value)
	{
		var s = value.split("[#")[0].trim().replace(/\s+/g, "_");
		var option = dest.jmrDomExt.extraData[s];
		if(option == null)
		{
			option = new  Option();
			dest.jmrDomExt.extraData[s] = dest.options[dest.options.length] = option;
		}

		option.text = option.value = value;
	}

	function _popupDialogContentInit(ajaxControl)
	{
		var elem = document.getElementById(ajaxControl);
		if (elem.type == "select-one" || elem.type == "select-multiple")
		{
			elem = document.getElementById("_pd_data_listboxSP");
			elem.multiple = "multiple";
		}

		document.getElementById("_popupdialog_ok").disabled = true;
		document.getElementById("_popupdialog_ok").className = "butnDisabled";
		return;
	}
	
	function _pd_qty_entrySP_onkeyup(field)
	{
		field = document.getElementById("_pd_qty_entrySP");
		var valid = doValidateExt(field, 'I', true, 1, null);
		document.getElementById("_popupdialog_ok").disabled = !valid;
		document.getElementById("_popupdialog_ok").className = (valid)? "butn": "butnDisabled";
	}

//]]>
</script>

<p>
	<select size="5" class="m_select_text" style="width: 100%;" id="_pd_data_listboxSP" name="_pd_data_listboxSP">
		<c:forEach var="i" items="${popupDataList}">
			<option value="${i}">${i}</option>
		</c:forEach>		
	</select>

	<c:if test="${(entryField=='yes')}">
		<br />
		<input class="text" id="_pd_data_entrySP" name="_pd_data_entrySP"  type="text"/>
	</c:if>

	<c:if test="${(qtyField=='yes')}">
		<br />
		<div style="vertical-align:middle;text-align:left;width:90%">
			<label for="_pd_qty_entrySP" style="text-align:left" title="End">Quantity :</label>
			<input class="text" id="_pd_qty_entrySP" name="_pd_qty_entrySP" onkeyup="_pd_qty_entrySP_onkeyup(this)" type="text"/>
		</div>
	</c:if>
</p>
