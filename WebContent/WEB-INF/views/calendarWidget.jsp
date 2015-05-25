<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
//<![CDATA[

	var _selectedDate;
	var leapYear = 0;
	var _calender_months = [
		{name: "January", days: [31, 31]},
		{name: "February", days: [28, 29]},
		{name: "March", days: [31, 31]},
		{name: "April", days: [30, 30]},
		{name: "May", days: [31, 31]},
		{name: "June", days: [30, 30]},
		{name: "July", days: [31, 31]},
		{name: "August", days: [31, 31]},
		{name: "September", days: [30, 30]},
		{name: "October", days: [31, 31]},
		{name: "November", days: [30, 30]},
		{name: "December", days: [31, 31]}
	];

	var displayCells = new Object();

	function _popupDialogContentOk(ajaxControl)
	{
		var dest = document.getElementById(ajaxControl);
		s = date2string(_selectedDate);
		if (dest.type == "select-one" || dest.type == "select-multiple")
		{
			dest.options[dest.options.length] = new Option(s, s);
		}
		else if (dest.type == "text" || dest.type == "password")
		{
			dest.value = s;
		}
	}

	function _popupDialogContentInit(ajaxControl)
	{
		var i;
		var data = null;
		try
		{
			data = document.getElementById(ajaxControl).value;
		}
		catch (e)
		{
		}

		if (data == null || data.length == 0)
		{
			_selectedDate = new Date();
		}
		else
		{
			_selectedDate = string2date(data);
		}

		populateList("_calendar_select_year", _selectedDate.getFullYear() - 2,
			_selectedDate.getFullYear() + 5, _selectedDate.getFullYear());

		populateDataList("_calendar_select_month", _calender_months, _selectedDate.getMonth());
		populateList("_calendar_select_hours", 0, 24, _selectedDate.getHours());
		populateList("_calendar_select_minutes", 0, 60, _selectedDate.getMinutes());
		for (i = 0; i < 42; i++)
		{
			displayCells[i] = document.getElementById("_calendarCell_" + i);
		}

		renderCalendar(_selectedDate);
	}

	function renderCalendar(date)
	{
		var tempDate = new Date(date);
		tempDate.setDate(1);

		var i = tempDate.getDay();
		var j = 0;
		for (j = 0; j < i; j++)
		{
			displayCells[j].className = "calendar_cellDisabled";
			displayCells[j].innerHTML = "&nbsp;";
		}

		var month = _calender_months[date.getMonth()];
		for (j = 0; j < month.days[leapYear]; j++)
		{
			displayCells[j + i].className = "calendar_cellEnabled";
			displayCells[j + i].innerHTML = j + 1;
		}

		for (j += i; j < 42; j++)
		{
			displayCells[j].className = "calendar_cellDisabled";
			displayCells[j].innerHTML = "&nbsp;";
		}

		i = i + date.getDate() - 1;
		displayCells[i].className = "calendar_cellSelected";
		updateSelectedDateDisplay();
	}

	function populateList(elem, start, end, selected)
	{
		try
		{
			var elem = document.getElementById(elem);
			if (elem == null)
				return;

			elem.length = 0;
			var iEnd = end - start;
			var i;
			for (i = 0; i < iEnd; i++, start++)
			{
				elem.options[i] = new Option(start, start);
				if (start == selected)
					elem.options[i].selected = true;
			}
		}
		catch (e)
		{
		}
	}

	function populateDataList(elem, data, selected)
	{
		var elem = document.getElementById(elem);
		elem.length = 0;
		var iEnd = data.length;
		var i;
		for (i = 0; i < iEnd; i++)
		{
			elem.options[i] = new Option(data[i].name, i);
			if (i == selected)
				elem.options[i].selected = true;
		}
	}

	function updateSelectedDateDisplay()
	{
		var elem = document.getElementById("selectedDate");
		elem.innerHTML = date2stringLong(_selectedDate);
	}

	function _changeYear(direction)
	{
		var month = _selectedDate.getMonth();
		var year = _selectedDate.getFullYear();
		if (direction < 0)
		{
			year--;

		}
		else
		{
			year++;
		}

		setMonthYear(month, year);
	}

	function _changeMonth(direction)
	{
		var month = _selectedDate.getMonth();
		var year = _selectedDate.getFullYear();
		if (direction < 0)
		{
			if (month == 0)
			{
				month = 12;
				year--;
			}

			month--;
		}
		else
		{
			if (month == 11)
			{
				month = -1;
				year++;
			}

			month++;
		}

		setMonthYear(month, year);
	}

	function setMonthYear(month, year)
	{
		var yearc = document.getElementById("_calendar_select_year");
		var monthc = document.getElementById("_calendar_select_month");
		if ((year < yearc.options[0].value) ||
			(year > yearc.options[yearc.length - 1].value))
			return;

		if (isLeapYear(year))
			leapYear = 1;
		else
			leapYear = 0;

		if (_selectedDate.getDate() > _calender_months[month].days[leapYear])
			_selectedDate.setDate(_calender_months[month].days[leapYear]);

		if (_selectedDate.getFullYear() < year)
			yearc.selectedIndex = yearc.selectedIndex + 1;
		else if (_selectedDate.getFullYear() > year)
			yearc.selectedIndex = yearc.selectedIndex - 1;

		monthc.selectedIndex = month;

		_selectedDate.setMonth(month);
		_selectedDate.setFullYear(year);
		renderCalendar(_selectedDate);
	}

	function _calendar_selectionChanged(ctrl)
	{
		var value = ctrl.options[ctrl.selectedIndex].value;
		if (ctrl.id == "_calendar_select_hours" || ctrl.id == "_calendar_select_minutes")
		{
			if (ctrl.id == "_calendar_select_minutes")
				_selectedDate.setMinutes(value);
			else
				_selectedDate.setHours(value);

			updateSelectedDateDisplay();
			return;
		}

		if (ctrl.id == "_calendar_select_year")
		{
			var month = _selectedDate.getMonth();
			if (isLeapYear(value))
				leapYear = 1;
			else
				leapYear = 0;

			if (_selectedDate.getDate() > _calender_months[month].days[leapYear])
				_selectedDate.setDate(_calender_months[month].days[leapYear]);

			_selectedDate.setFullYear(value);
		}
		else if (ctrl.id == "_calendar_select_month")
		{
			if (_selectedDate.getDate() > _calender_months[value].days[leapYear])
				_selectedDate.setDate(_calender_months[value].days[leapYear]);

			_selectedDate.setMonth(value);
		}

		renderCalendar(_selectedDate);
	}

	function isLeapYear(year)
	{
		if ((year % 100) == 0)
		{
			if ((year % 400) == 0)
				return true;
		}
		else if ((year % 4) == 0)
			return true;

		return false;
	}

	function calendar_cellSelected(cell)
	{
		var s = cell.className;
		if (s == "calendar_cellEnabled")
		{
			var tempDate = new Date(_selectedDate);
			tempDate.setDate(1);
			var i = tempDate.getDay();
			var j = i + _selectedDate.getDate() - 1;
			displayCells[j].className = "calendar_cellEnabled";

			_selectedDate.setDate(cell.innerHTML * 1);
			j = j = i + _selectedDate.getDate() - 1;
			displayCells[j].className = "calendar_cellSelected";
			updateSelectedDateDisplay();
		}
	}
//]]>
</script>

<table style="width:100%;cursor:default;">
	<tr>
		<td colspan="4" style="text-align:left">
			<select id="_calendar_select_month" style="width:110px; text-align:center" onchange="_calendar_selectionChanged(_calendar_select_month);">
				<option />
			</select>
		</td>

		<td colspan="3" style="text-align:right">
			<select id="_calendar_select_year" style="width:110px; text-align:center" onchange="_calendar_selectionChanged(_calendar_select_year);">
				<option />
			</select>
		</td>
	</tr>

	<tr>
		<td colspan="7"></td>
	</tr>

	<tr>
		<td class="calendar_cellHeader" style="cursor:pointer" onclick="_changeYear(-1);">&#8810;</td>
		<td class="calendar_cellHeader" style="cursor:pointer" onclick="_changeMonth(-1);">&#8826;</td>
		<td colspan="3"></td>
		<td class="calendar_cellHeader" style="cursor:pointer" onclick="_changeMonth(1);">&#8827;</td>
		<td class="calendar_cellHeader" style="cursor:pointer" onclick="_changeYear(1);">&#8811;</td>
	</tr>

	<tr>
		<td colspan="7" id="selectedDate" style="font-size:10px;text-align:center;padding-top:5px;"></td>
	</tr>

	<tr>
		<td class="calendar_cellHeader">S</td>
		<td class="calendar_cellHeader">M</td>
		<td class="calendar_cellHeader">T</td>
		<td class="calendar_cellHeader">W</td>
		<td class="calendar_cellHeader">T</td>
		<td class="calendar_cellHeader">F</td>
		<td class="calendar_cellHeader">S</td>
	</tr>
	<tr>
		<td id="_calendarCell_0" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_1" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_2" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_3" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_4" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_5" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_6" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
	</tr>
	<tr>
		<td id="_calendarCell_7" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_8" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_9" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_10" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_11" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_12" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_13" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
	</tr>
	<tr>
		<td id="_calendarCell_14" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_15" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_16" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_17" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_18" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_19" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_20" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
	</tr>
	<tr>
		<td id="_calendarCell_21" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_22" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_23" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_24" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_25" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_26" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_27" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
	</tr>
	<tr>
		<td id="_calendarCell_28" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_29" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_30" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_31" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_32" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_33" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_34" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
	</tr>
	<tr>
		<td id="_calendarCell_35" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_36" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_37" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_38" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_39" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_40" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
		<td id="_calendarCell_41" class="calendar_cellEnabled" onclick="calendar_cellSelected(this);">&nbsp;</td>
	</tr>


	<tr>
		<td colspan="7" style="text-align:center">
			Time:
			<select id="_calendar_select_hours" onchange="_calendar_selectionChanged(_calendar_select_hours);">
				<option />
			</select><span style="font-weight:bolder">&nbsp;:</span>
			<select id="_calendar_select_minutes"  onchange="_calendar_selectionChanged(_calendar_select_minutes);">
				<option />
			</select>
		</td>
	</tr>
</table>
