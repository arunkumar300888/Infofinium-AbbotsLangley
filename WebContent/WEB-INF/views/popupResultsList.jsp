<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
//<![CDATA[           
           $('#_pd_data_entry').on('keypress', function (event) {
               if(event.which == '13'){
                //  alert("inside popuplist");
                   return false;
               }
         });
    
	function _popupDialogContentOk(ajaxControl)
	{
		var dest = document.getElementById(ajaxControl);
		var lb = document.getElementById("_pd_data_listbox");
		var ef = document.getElementById("_pd_data_entry");
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
					_popupDialogAddOption(dest, s.split("[#")[0]);
				}
				else
				{
					var j = 0;
					for(j=0; j<lb.options.length; j++)
					{
						if(lb.options[j].selected)
						{
							_popupDialogAddOption(dest, lb.options[j].value.split("[#")[0]);
						}
					}
				}
			}
			else if (s != null && (dest.type == "text" || dest.type == "password"))
			{
					dest.value = s;
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
			elem = document.getElementById("_pd_data_listbox");
			elem.multiple = "multiple";
		}

		return;
	}

//]]>
</script>

<p>
	<select size="5" class="m_select_text" style="width: 100%;" id="_pd_data_listbox" name="_pd_data_listbox">
		<c:forEach var="i" items="${popupDataList}">
			<option value="${i}">${i}</option>
		</c:forEach>		
	</select>

	<c:if test="${(entryField=='yes')}">
		<br />
		<input class="text" id="_pd_data_entry" name="_pd_data_entry" type="text"/>
	</c:if>
</p>
