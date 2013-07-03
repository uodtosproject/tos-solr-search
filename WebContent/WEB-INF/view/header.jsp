<%@ page import="br.com.betohayasida.SolrSearch.DB.Site,java.util.List,java.lang.reflect.Array,java.util.ArrayList,java.util.List" %>
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
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="<%= request.getContextPath()%>/search">Search</a>
                    <div class="nav-collapse collapse">
                        <ul class="nav">
                            <li><a href="<%= request.getContextPath()%>/search">Home</a></li>
                        </ul>
                    </div>
                    <div class="navbar-form pull-right">
                        <ul class="nav">
                            <li><a href="#top" class="scroll">Scroll to the Top</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
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
			
			<% 
			String q_text = (String) request.getAttribute("q_text");
			String q_title = (String) request.getAttribute("q_title");
			%>
			<div class="hero-unit">
                <h1>Search</h1>
          		<p class="lead">Search in Terms of Use of social networks.</p>
            </div>
            
            <div class="row">
            	<div class="span12">
                <div class="well well-large">
			    	<h2>How to use</h2>
					<p>You can use logic operators (AND, OR and NOT). Group words/expressions in parenthesis, using commas (equivalent to AND) to separate expressions.</p>
					<p>Example: <i>(one expression, another expression) OR (yet another expression, word)</i></p>
					
			    </div>
			    </div>
            </div>
            <form class="form-horizontal" action="<%=request.getContextPath()%>/search/" method="POST">
			<div class="row">
		    	<div class="span6">
		        	<h2>Keywords</h2>
		        	<div class="control-group">
				    	<label class="control-label" for="q_text">Keywords in page's content</label>
						<div class="controls">
							<input tabindex="1" autofocus="autofocus" class="input-xlarge" name="q_text" id="q_text" type="text" placeholder="Query" value="<%= (q_text!=null) ? q_text : "" %>" />
						</div>
					</div>
		        	<div class="control-group">
						<label class="control-label" for="q_title">Keywords in page's title</label>
						<div class="controls">
							<input tabindex="2" autofocus="autofocus" class="input-xlarge" name="q_title" id="q_title" type="text" placeholder="Query" value="<%= (q_title!=null) ? q_title : "" %>" />
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-inverse">Search</button>
						</div>
					</div>
		        </div>
		    	<div class="span6">
		        	<h2>Site filter</h2>
				    <% List<Site> sites = (List<Site>) request.getAttribute("sites"); %>
				    <% for(Site site : sites) { %>
				    <label class="checkbox">
						<input type="checkbox" name="sites" id="sites" value="<%= site.getName() %>|<%= site.getVisitedOn() %>" checked />
						<%= site.getDomain() %>
					</label>
			   		<% } %>
		        </div>
			</div>
		    </form>
		            