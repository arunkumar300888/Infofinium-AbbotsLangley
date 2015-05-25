function getPosition(obj)
{
	var curleft = obj.offsetLeft;
	var curtop = obj.offsetTop;
	if (obj.offsetParent)
	{
		while (obj = obj.offsetParent)
		{
			curleft += obj.offsetLeft;
			curtop += obj.offsetTop;
		}
	}

	return {left: curleft, top: curtop};
}

// ------------------------------
function panelSize(panel)
{
	var c = document.getElementById(panel);
	return {width: c.offsetWidth, height: c.offsetHeight};
}

// ------------------------------
function calcPos(cPos, dSize, pSize)
{
	var x = cPos + dSize;
	if (x > pSize)
	{
		cPos = cPos - (x - pSize);
	}

	if (cPos < 0)
		cPos = 0;

	return cPos;
}

// ------------------------------
function openPopupDialog(ctrl, content, jsContent)
{
	var panelPos = getPosition(ctrl);
	var c = document.getElementById("_popupPanel");
	
	//c.innerHTML = content;
	$('#_popupPanel').html(content);
	
	showPopupPanel("_popupPanel", true);
	if (jsContent != null)
	{
		//document.getElementById("_popupDialogContent").innerHTML = jsContent;
		$('#_popupDialogContent').html(jsContent);
	}

	var pageSize = panelSize("_popupPanel");
	var popupSize = panelSize("_popupDialog");
	var dlg = document.getElementById("_popupDialog");

	dlg.style.left = (calcPos(panelPos.left, popupSize.width, pageSize.width) + 10) + "px";
	dlg.style.top = (calcPos(panelPos.top, popupSize.height, pageSize.height) + 10) + "px";
}

// ------------------------------
function closePopupDialog()
{
	showPopupPanel("_popupPanel", false);
}

// ------------------------------
function _popupSelect(ctrl)
{
	ctrl.form._okBtn.disabled = (ctrl.selectedIndex == -1);
}

function _ok(ctrl)
{
	_popupData.popupOkHandler(ctrl.form._popupSelectionList.value);
	closePopupDialog();
}

function _previous()
{
	if ((_popupData.requestedPage == 1) || _popupData.ajaxCallInProgress)
		return;

	_loadResultsPage(_popupData.requestedPage - 1);
}

function _next()
{
	if ((_popupData.requestedPage == _popupData.totalPages) || _popupData.ajaxCallInProgress)
		return;

	_loadResultsPage(_popupData.requestedPage + 1);
}

var _popupData = null;

function _popupInit(data)
{
	if (_popupData == null)
		_popupData = data;
	else
	{
		_popupData.requestedPage = data.requestedPage;
		_popupData.totalPages = data.totalPages;
	}

	document.getElementById("_previousPage").className =
			((data.requestedPage == 1) ? "hasNoMore" : "hasMore");

	document.getElementById("_nextPage").className =
			((data.requestedPage == data.totalPages) ? "hasNoMore" : "hasMore");

	document.getElementById("_resultInfo").innerHTML = "[" + data.requestedPage + "/" + data.totalPages + "]";

	var resultList = data.searchResults;
	document._popupForm._popupSelectionList.length = 0;
	if (resultList != null)
	{
		for (var i = 0; i < resultList.length; i++)
		{
			var o = new Option(resultList[i].displayValue, resultList[i].value);
			if (resultList[i].description != null)
				o.title = resultList[i].value + " : " + resultList[i].description;
			else
				o.title = resultList[i].value;

			document._popupForm._popupSelectionList.options[i] = o;
		}
	}
}

function _pageResultsLoaded(xhr)
{
	try
	{
		eval(trim(xhr.responseText));
	}
	catch (err)
	{
		alert(err);
	}

	_popupData.ajaxObject = null;
	_popupData.ajaxCallInProgress = false;
}


function _loadResultsPage(page)
{
	_popupData.ajaxCallInProgress = true;
	_popupData.ajaxObject = new AjaxRequest();
	_popupData.ajaxObject.setCallBack(4, _pageResultsLoaded);
	var xhr = _popupData.ajaxObject.getXHRObject();
	var requestedUrl = _popupData.requestUrl + "&requestedPage=" + page;
	xhr.open("GET", requestedUrl, true);
	xhr.send(null);
}

function makeAjaxCall(dialogTitle, ctrl, url)
{
	var o = new AjaxRequest();
	var xhr = o.getXHRObject();
	xhr.open("GET", "popupFragment.jsp?command=showDialog&dialogTitle=" + dialogTitle, false);
	xhr.send(null);
	if (xhr.status != 200)
		return;

	var content = trim(xhr.responseText);
	xhr.open("GET", url, false);
	xhr.send(null);
	if (xhr.status != 200)
		return;

	_popupData = null;
	var jsContent = trim(xhr.responseText);
	openPopupDialog(ctrl, content, jsContent);
	//eval(jsContent);
}
