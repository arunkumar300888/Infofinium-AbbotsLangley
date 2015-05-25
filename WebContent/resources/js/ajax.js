/*
 * AjaxRequest object to easy Ajax calls.  You need to define a state change event
 * handler function and set it using setCallBack(state, callback) function.
 *
 * States:
 *    0 = uninitialized
 *    1 = loading
 *    2 = loaded
 *    3 = interactive
 *    4 = complete
 *
 * interface/prototype for event callback functions is
 *
 *    function_name(XMLHttpRequest obj)
 *
 * Example:
 *
 *  function cbLoaded(xhr)
 *  {
 *     alert(xhr.responseText);
 *  }
 *
 *  function call()
 *  {
 *     var o = new AjaxRequest();
 *     o.setCallBack(4, cbLoaded);
 *
 *     var xhr = o.getXHRObject();
 *     xhr.open('GET', 'http://localhost:8080/snoop.jsp', true);
 *     xhr.send(null);
 *  }
 *
 */
function AjaxRequest()
{
	var xhrObject = null;
	var stateCallbacks = [ null, null, null, null, null ];

	if (window.XMLHttpRequest)
	{
		// Firefox, Safari, ...
		xhrObject = new XMLHttpRequest();
	}
	else if (window.ActiveXObject)
	{
		// ActiveX version Internet Explorer
		xhrObject = new ActiveXObject("Microsoft.XMLHTTP");
	}
	else
		xhrObject = new XMLHttpRequest();

	// Handle state change
	xhrObject.onreadystatechange = function()
	{
		switch (xhrObject.readyState)
		{
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				if (stateCallbacks[xhrObject.readyState] != null)
					stateCallbacks[xhrObject.readyState](xhrObject);

				break;

			default:
		}
		;
	}

	// return the calling object
	this.getXHRObject = function()
	{
		return xhrObject;
	}

	// set callback function for given state
	this.setCallBack = function(state, callback)
	{
		if ((state > -1) && (state < 5))
		{
			stateCallbacks[state] = callback;
		}
	}
}
