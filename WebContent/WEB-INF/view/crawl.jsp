<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp" %>
<% String status = (String) request.getAttribute("status"); %>
<div class="row">
	<div class="span12">
		<h3>Your request was processed.</h3>
		<p><%= status %></p>
	</div>
</div>
<%@ include file="footer.jsp" %>