<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="br.com.betohayasida.SolrInterface.Result,java.util.HashMap,java.util.Set,java.lang.reflect.Array,java.util.ArrayList,java.util.List" %>
<%@ include file="header.jsp" %>
<% List<Result> results = (List<Result>) request.getAttribute("results");  %>
<% int i = 0; int j = 0; int size = results.size(); %>
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
						<p><a href="<%= request.getContextPath()%>/page/<%= result.getName() %>" class="btn btn-primary btn-warning">View History</a></p>
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
	            
            		<% if(i % 20 == 19) { %>
	            	</div>
	            	<% } else { %>
	            	<hr />
	            	<% } %>
            	<% } %>
				</div>

    		</div>
		</div>

<%@ include file="footer.jsp" %>