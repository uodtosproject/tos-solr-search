<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="br.com.betohayasida.SolrInterface.Result,java.util.HashMap,java.util.Set,java.lang.reflect.Array,java.util.ArrayList,java.util.List" %>
<%@ include file="header_collapsed.jsp" %>
<% String question_text = (String) request.getAttribute("question_text"); %>
<% String question_id = (String) request.getAttribute("question_id"); %>
<% String[] parents = (String[]) request.getAttribute("parents"); %>
<% List<Result> results = (List<Result>) request.getAttribute("results"); %>
<hr />
<br />
<div class="row">
	<div class="span12">
		<h1><%= question_text %></h1>
        <form class="form-horizontal" action="<%=request.getContextPath()%>/question/<%= question_id %>" method="POST">
	       	<h2>Site filter</h2>
	       	
		    <label class="checkbox">
		    	<input type="checkbox" id="select-all" /> <b>Select all / none</b>
		    </label>
		    <% List<Site> sites2 = (List<Site>) request.getAttribute("sites"); %>
		    <% int i = 0; boolean first = true; %>
		    <% for(Site site : sites2) { %>
			    <% if( i%4 == 0 && first) { %>
			    <div class="row">
			    	<% first = false; %>
			    <% } else if( i%4 == 0 ) { %>
			    </div>
			    <div class="row">
			    <% } %>
				    <div class="span3">
					    <label class="checkbox">
					    	<% 
					    	boolean checked = false;
					    	if(parents != null) {
					    		for(String p : parents){
					    			if(p.equalsIgnoreCase(site.getName() + "|" + site.getVisitedOn())){
					    				checked = true;
					    			}
					    		}
					    	}
					    	%>
							<input type="checkbox" name="sites" id="sites" value="<%= site.getName() %>|<%= site.getVisitedOn() %>" <%if(checked) {%>checked<%}%> />
							<img height="15" width="15" src="<%= site.getIco() %>" title="<%= site.getDomain() %>" /> <%= site.getDomain() %>
						</label>
					</div>
		    	<% i++;  %>
	   		<% } %>
			    </div>
	   		<br class="clear" />
			<div class="row">
				<div class="span12">
					<button type="submit" class="btn btn-inverse">Search</button>
				</div>
			</div>
	   		<br class="clear" />
   		</form>
	</div>
</div>
<%if(results != null) { %>
<%  i = 0; int j = 0; int size = results.size(); %>
		<div class="row">
        	<div class="span12">
            	<h3><%= results.size() %> results</h3>
            	
            	<ul class="nav nav-tabs" id="myTab">
            	<% for(i=0; i<size; i++){ %>
            		<% if(i % 20 == 0) {  %>
					<li<%if(i<20){ %> class="active"<% } %>><a href="#tab<%=j%>" data-toggle="tab"><%= j*20 + 1 %> - <%= ((j + 1)*20 ) %></a></li>
					<% j++; } %>
            	<% } %>
				</ul>
				 
				<div class="tab-content">
            	<% j = 0; %>
            	<% for(i = 0; i < size; i++){   %>
            		<% Result result = results.get(i); %>
	            	<% if(i % 20 == 0) { %>
	            		<% String tbclass = "tab-pane"; 
	            		if(i < 20) { tbclass = tbclass + " active"; } %>
						<div class="<%= tbclass %>" id="tab<%= j %>">
					<% j++; } %>
						<h4><%= i + 1 %>.: "<%= result.getTitle() %>"</h4>
						<p><b>URL: </b><a href="<%= result.getUrl() %>" title="<%= result.getUrl() %>"><%= result.getUrl() %></a></p>
						<div class="accordion" id="accordion<%= result.getId().replace("|", "") %>">
							<div class="accordion-group">
								<div class="accordion-heading">
							    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion<%= result.getId().replace("|", "") %>" href="#highlights<%= result.getId().replace("|", "") %>">Highlights</a>
						   		</div>
						    	<div id="highlights<%= result.getId().replace("|", "") %>" class="accordion-body collapse">
						      		<div class="accordion-inner">
						                <% for(String highlight : result.getHighlighted()) { %>
							    	    <pre class="pre-scrollable">
						                	<%= highlight %>
					                	</pre>
						                <% } %>
							      	</div>
							    </div>
						  	</div>
							<div class="accordion-group">
								<div class="accordion-heading">
							    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion<%= result.getId().replace("|", "") %>" href="#text<%= result.getId().replace("|", "") %>">Entire Text</a>
						   		</div>
						    	<div id="text<%= result.getId().replace("|", "") %>" class="accordion-body collapse">
						      		<div class="accordion-inner">
							    	    <pre class="pre-scrollable">
					                		<%= result.getText() %>
								    	</pre>
							      	</div>
							    </div>
						  	</div>
						</div>
						<div class="row">
							<div class="span6">
								<p><a href="<%= request.getContextPath()%>/page/<%= result.getName() %>" class="btn btn-primary btn-warning">View History</a></p>
							</div>
							<div class="span6 alignright">
								<p><b>Is this relevant?</b> <a href="<%= request.getContextPath()%>/page/<%= result.getName() %>/relup" class="rellink btn btn-primary">Yes</a> <a href="<%= request.getContextPath()%>/page/<%= result.getName() %>/reldown" class="rellink btn btn-primary">No</a></p>
							</div>
	            		</div>
            		<% if(i % 20 == 19) { %>
	            	</div>
	            	<% } else { %>
	            	<hr />
	            	<% } %>
            	<% } %>
				</div>

    		</div>
		</div>
<% } %>
<%@ include file="footer.jsp" %>