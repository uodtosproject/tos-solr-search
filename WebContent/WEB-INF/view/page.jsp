<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="br.com.betohayasida.Diff.DiffEntry, br.com.betohayasida.SolrSearch.DB.Page, br.com.betohayasida.SolrSearch.DB.Site, java.util.HashMap,java.util.Set,java.lang.reflect.Array,java.util.ArrayList,java.util.List" %>
<%@ include file="header.jsp" %>
<% 
	List<Page> pages = (List<Page>) request.getAttribute("pages"); 
	Page currentPage = (Page) request.getAttribute("currentPage"); 
	List<DiffEntry> diffs = (List<DiffEntry>) request.getAttribute("diffs"); 
%>
		<hr />
		<div class="row">
        	<div class="span12">
        		
				<h2>Page: <%= currentPage.getTitle() %></h2>
				<p><b>URL: </b><a href="<%= currentPage.getUrl() %>" title="<%= currentPage.getUrl() %>"><%= currentPage.getUrl() %></a></p>
				<p><b>Retrieved on:</b> <%= currentPage.getRetrievedOn() %></p>
				<br />
	    	    <pre class="pre-scrollable">
               		<%= currentPage.getText() %>
		    	</pre>
		    	<br />
		    	<% if(diffs.size() > 0) { %>
		    	<hr />
		    	<br />
		    	<div class="accordion" id="accordion">
			    	<% int i = 0; for(DiffEntry diff : diffs){ %>
					<div class="accordion-group">
						<div class="accordion-heading">
					    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#accordion-<%= i %>">Version of <%= diff.getPage().getRetrievedOn() %></a>
				   		</div>
				    	<div id="accordion-<%= i %>" class="accordion-body collapse">
				      		<div class="accordion-inner">
	    	    				<pre class="pre-scrollable">
				               		<%= diff.getDiff() %>
						    	</pre>
					      	</div>
					    </div>
				  	</div>
			    	<% i++; } %>
		    	</div>
		    	<% } %>
			</div>
		</div>
<%@ include file="footer.jsp" %>