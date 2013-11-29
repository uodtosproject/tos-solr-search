<%@ page import="br.com.betohayasida.SolrSearch.DB.Site,br.com.betohayasida.SolrSearch.DB.Question,java.util.List,java.lang.reflect.Array,java.util.ArrayList" %>
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
			
			<% 
			String q_text = (String) request.getAttribute("q_text");
			String q_title = (String) request.getAttribute("q_title");
			%>
			<div class="hero-unit">
                <h1>T&C.Search</h1>
          		<p class="lead">Search in Terms and Conditions of Internet Service Providers.</p>
            </div>
            
            <div class="row">
            	<div class="span12">
	                <div class="well well-large">
				    	<h2>How to use</h2>
						<p>You can use logic operators (AND, OR and NOT). Group words/expressions in parenthesis, using commas (equivalent to AND) to separate expressions.</p>
						<p>Example: <i>(one expression, another expression) OR (yet another expression, word)</i></p>
						
				    </div>
  
        				<form class="form-horizontal" action="<%=request.getContextPath()%>/search/" method="POST">
        				<h2>Keywords</h2>
						<div class="row">
					    	<div class="span12">
					        	<div class="control-group">
							    	<label class="control-label" for="q_text">Keywords</label>
									<div class="controls">
										<input tabindex="1" autofocus="autofocus" class="input-xlarge" name="q_text" id="q_text" type="text" placeholder="Query" value="<%= (q_text!=null) ? q_text : "" %>" />
									</div>
								</div>
							</div>	
					   	</div>
					   	<div class="row">
					    	<div class="span12">
					        	<h2>Site filter</h2>
					        </div>
					    	<div class="span12">
							    <br class="clear"/>
							    <label class="checkbox">
							    	<input type="checkbox" id="select-all" checked="checked" /> <b>Select all / none</b>
							    </label>
						    </div>
						</div>
					    <% List<Site> sites = (List<Site>) request.getAttribute("sites"); %>
						<% int i = 0; boolean first = true; %>
					    <% for(Site site : sites) { %>
						    <% if( i%4 == 0 && first) { %>
						    <div class="row">
						    	<% first = false; %>
						    <% } else if( i%4 == 0 ) { %>
						    </div>
						    <div class="row">
						    <% } %>
							    <div class="span3">
								    <label class="checkbox">
										<input type="checkbox" name="sites" id="sites" value="<%= site.getName() %>|<%= site.getVisitedOn() %>" checked="checked" />
										<img width="15" height="15" src="<%= site.getIco() %>" title="<%= site.getDomain() %>" /> <%= site.getDomain() %>
									</label>
								</div>
					    	<% i++;  %>
				   		<% } %>
					    </div>
					    <div class="row">
					    	<div class="span12">
							    <br class="clear"/>
							    <label class="checkbox">
									<button type="submit" class="btn btn-inverse">Search</button>
							    </label>
						    </div>
						</div>
 					</form>
	   			</div>
	   		</div>
		   	
            <form class="form-horizontal" action="<%=request.getContextPath()%>/crawl/" method="POST">
			<div class="row">
		    	<div class="span12">
		    		<div class="accordion" id="accordion-crawl">
						<div class="accordion-group">
							<div class="accordion-heading">
								<h4><a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-crawl" href="#collapse-crawlOne">
									Is the service you wanted not in the list?
								</a></h4>
							</div>
							<div id="collapse-crawlOne" class="accordion-body collapse">
								<div class="accordion-inner">
						        	<p>Notice that the process may take a few minutes. DO NOT reload the page, as it may interfere with the process.</p>
						        	<div class="control-group">
								    	<label class="control-label" for="url">URL</label>
										<div class="controls">
											<input tabindex="5" class="input-xlarge" name="url" id="url" type="text" placeholder="e.g. http://www.facebook.com"  />
										</div>
									</div>
									<div class="control-group">
										<div class="controls">
											<button type="submit" class="btn btn-inverse">Add</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
		        </div>
			</div>
		    </form>
			
		            