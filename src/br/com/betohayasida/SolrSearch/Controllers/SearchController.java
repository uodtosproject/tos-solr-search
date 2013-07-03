package br.com.betohayasida.SolrSearch.Controllers;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.betohayasida.SolrInterface.Query;
import br.com.betohayasida.SolrInterface.Result;
import br.com.betohayasida.SolrSearch.DB.Site;
import br.com.betohayasida.SolrSearch.DB.SitesModel;

public class SearchController extends HttpServlet {

	private static final long serialVersionUID = 6738408051364248371L;

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html");
		String URI = request.getRequestURI();
    	String[] URIparts = URI.split("/");
    	
    	if(URIparts.length > 2){
			if(URIparts[2].equalsIgnoreCase("search")){
				goHome(request,response);
			} else {
				goHome(request,response);
			} 
    	} else {
    		goHome(request,response);
    	}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html");
		String URI = request.getRequestURI();
    	String[] URIparts = URI.split("/");
    	
    	if(URIparts.length > 2){
			if(URIparts[2].equalsIgnoreCase("search")){
		    	SitesModel siteModel = new SitesModel();
		    	List<Site> sites = siteModel.getSites();
				String q_text = (String) request.getParameterValues("q_text")[0];
				String q_title = (String) request.getParameterValues("q_title")[0];
				String[] parents = request.getParameterValues("sites");
				Query querier = new Query();
				querier.open(null);
				List<Result> results = querier.query(q_text, q_title, parents);
				
			    RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/results.jsp");
				request.setAttribute("title", "Search for '" + q_text + "' and '" + q_title + "'");
				request.setAttribute("results", results);
				request.setAttribute("sites", sites);
				request.setAttribute("q_text", q_text);
				request.setAttribute("q_title", q_title);
				request.setAttribute("parents", parents);
				try {
					rd.forward(request,response);
				} catch (Exception e) {
					// e.printStackTrace();
				} 
			} else {
				goHome(request,response);
			} 
    	} else {
    		goHome(request,response);
    	}
	}
	
	public void goHome(HttpServletRequest request, HttpServletResponse response){
    	SitesModel siteModel = new SitesModel();
    	List<Site> sites = siteModel.getSites();
		RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/home.jsp");
		request.setAttribute("title", "Search");
		request.setAttribute("sites", sites);
		try {
			rd.forward(request,response);
		} catch (Exception e) {
			// e.printStackTrace();
		} 
	}
}