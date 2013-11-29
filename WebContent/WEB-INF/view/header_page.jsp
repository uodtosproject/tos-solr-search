<%@ page import="br.com.betohayasida.SolrSearch.DB.Site,br.com.betohayasida.SolrSearch.DB.Question,java.util.List,java.lang.reflect.Array,java.util.ArrayList,java.util.List" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Search - <%= request.getAttribute("title") %></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap.min.css">
        
		<link rel="shortcut icon" href="<%= request.getContextPath()%>/resources/favicon.ico">
		<link rel="apple-touch-icon" href="<%= request.getContextPath()%>/resources/apple-touch-icon.png">
		<link rel="apple-touch-icon" sizes="72x72" href="<%= request.getContextPath()%>/resources/apple-touch-icon-72x72.png">
		<link rel="apple-touch-icon" sizes="114x114" href="<%= request.getContextPath()%>/resources/apple-touch-icon-114x114.png">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap-responsive.min.css">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/main.css">

        <script src="<%= request.getContextPath()%>/resources/js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    </head>
    <body>
        <%@ include file="menu.jsp" %>
        <div class="container">
			<% 
			String alertText = (String) request.getAttribute("alertText");
			String alertTitle = (String) request.getAttribute("alertTitle");
			if(alertText != null && alertTitle != null){
			%>
			<div class="alert">
		    	<button type="button" class="close" data-dismiss="alert">&times;</button>
		    	<h3><%= alertTitle %></h3>
		    	<p><%= alertText %></p>
		    </div>
			<% } %>