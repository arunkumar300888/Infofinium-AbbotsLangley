<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String pageHtml = "";

	try
	{
		pageHtml = (String) request.getAttribute("pageHtml");
	}
	catch(Exception e)
	{
	}
%>
<%=pageHtml%>