var FORMAT_DATE =      new RegExp("^\\d{1,2}/\\d{1,2}/\\d{4}$");
var FORMAT_TIMESTAMP = new RegExp("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$");

var FORMAT_POSITIVE_INT = new RegExp("^\d*$");
var FORMAT_INT = new RegExp("^-?\d+$");
var FORMAT_NUMBER = new RegExp("^-?\d+\.?\d*$");

// ------------------------------
function markFieldLabelValid(field, valid)
{
	if(field.parentNode.firstElementChild.nodeName == "LABEL")
	{
		if(!valid)
			field.parentNode.firstElementChild.className = "FormFieldLabelInvalid";
		else
			field.parentNode.firstElementChild.className = "FormFieldLabelRequired";
	}
}

// ------------------------------
function formatInt2String(i)
{
	if (i < 10)
		return ("0" + i);
	else
		return i;
}

// ------------------------------
function date2string(date)
{
	var s = formatInt2String(date.getDate()) + "/" +
		formatInt2String(date.getMonth() + 1) + "/" +
		formatInt2String(date.getFullYear());

	if (document.getElementById("_calendar_select_hours") != null)
	{
		s = s + " " + formatInt2String(date.getHours()) + ":" + formatInt2String(date.getMinutes());
	}

	return s;
}

// ------------------------------
function date2stringLong(date)
{
	var s = formatInt2String(date.getDate()) + " " +
		_calender_months[date.getMonth()].name + " " +
		formatInt2String(date.getFullYear());

	if (document.getElementById("_calendar_select_hours") != null)
	{
		s = s + " @ " + formatInt2String(date.getHours()) + ":" + formatInt2String(date.getMinutes());
	}

	return s;
}

// ------------------------------
function string2date(sdate)
{
	var date = new Date();
	try
	{
		sa = sdate.split("/");
		date.setDate(sa[0]);
		date.setMonth(sa[1] - 1);
		if (sa[2].length > 4)
		{
			var t = sa[2].slice(sa[2].length - 5, sa[2].length).split(":");
			date.setHours(t[0]);
			date.setMinutes(t[1]);
		}
		else
			date.setFullYear(sa[2]);
	}
	catch (e)
	{
	}

	return date;
}

// ------------------------------
function trim(s)
{
	if(s != null)
	{
		s = s.replace(/^\s+/g, "");
		s = s.replace(/\s+$/g, "");
		if(s.length == 0)
			return null;
	}

	return s;
}

// ------------------------------
function getTypeDescription(type)
{
	if(type == "I")
		return "A integer number value (eg 100)";
	else if(type == "N")
		return "A real number value (eg 10.024)";
	else if(type == "S")
		return "A string value";
	else if(type == "B")
		return "Boolean value (true/false)";
	else if(type == "D")
		return "Date value (DD/MM/YYYY)";
	else if(type == "T")
		return "Timestamp value (DD/MM/YYYY HH:MI)";

	return "Some other type";
}

// ------------------------------
function checkRange(v, min, max)
{
	if((min != null) && (v < min))
		return false;

	if((max != null) && (v > max))
		return false;

	return true;
}

// ------------------------------
function validateInput(ctrl, type, regexp, mandatory, min, max)
{
	var v = trim(ctrl.value);
	var valid = false;
	if((v != null) && (v.length != 0))
	{
		if(type == 'I')
		{
			if(!isNaN(v))
			{
				if(v.indexOf(".", 0) == -1)
					valid = checkRange(v, min, max);
			}
		}
		else if (type == 'N')
		{
			if(!isNaN(v))
				valid = checkRange(v, min, max);
		}
		else if (type == 'B')
		{
			if(v.toLowerCase() == 'true')
				valid = true;
		}
		else if (type == 'S')
		{
			valid = true;
		}
		else if (type == 'D')
		{
			regexp = FORMAT_DATE;
			valid = regexp.test(v);
		}
		else if (type == 'T')
		{
			
			regexp = FORMAT_TIMESTAMP;
			valid = regexp.test(v);
		}
		else if (type == 'R')
		{
			valid = regexp.test(v);
		}
	}
	else if (!mandatory)
		valid=true;

	return valid;
}

// ------------------------------
function doValidateRxp(ctrl, type, regexp, mandatory, min, max)
{
	return validateInput(ctrl, type, regexp, mandatory, min, max);
}

// ------------------------------
function doValidateExt(ctrl, type, mandatory, min, max)
{
	return doValidateRxp(ctrl, type, null, mandatory, min, max);
}

// ------------------------------
function doValidate(ctrl, type)
{
	return doValidateExt(ctrl, type, true, null, null);
}
