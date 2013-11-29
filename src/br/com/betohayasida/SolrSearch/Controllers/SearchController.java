package br.com.betohayasida.SolrSearch.Controllers;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.betohayasida.SolrInterface.Extractor;
import br.com.betohayasida.SolrInterface.Query;
import br.com.betohayasida.SolrInterface.Result;
import br.com.betohayasida.SolrSearch.DB.Question;
import br.com.betohayasida.SolrSearch.DB.QuestionsModel;
import br.com.betohayasida.SolrSearch.DB.Site;
import br.com.betohayasida.SolrSearch.DB.SitesModel;

/**
 * Controller for searches
 * @author rkhayasidajunior
 *
 */
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
    	SitesModel siteModel = new SitesModel();
    	List<Site> sites = siteModel.getSites();
    	QuestionsModel qm = new QuestionsModel();
    	List<Question> questions = qm.getAll();
		request.setAttribute("sites", sites);
		request.setAttribute("questions", questions);
    	
    	if(URIparts.length > 2){
			if(URIparts[2].equalsIgnoreCase("search")){
				String q_text = (String) request.getParameterValues("q_text")[0];
				String q_title = (String) request.getParameterValues("q_text")[0];
				String[] parents = request.getParameterValues("sites");
				Query querier = new Query();
				querier.open(null);
				List<Result> results = querier.query(q_text, q_title, parents);
				Extractor extractor = new Extractor();
				
				for(Result r : results){
					List<String> hl = extractor.extract(r.getHighlighted());
					r.setHighlighted(hl);
				}
				
			    RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/results.jsp");
				request.setAttribute("title", "Search for '" + q_text + "'");
				request.setAttribute("results", results);
				request.setAttribute("q_text", q_text);
				request.setAttribute("q_title", q_title);
				request.setAttribute("parents", parents);
				try {
					rd.forward(request,response);
				} catch (Exception e) {
					e.printStackTrace();
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
    	QuestionsModel qm = new QuestionsModel();
    	List<Question> questions = qm.getAll();
		RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/home.jsp");
		request.setAttribute("title", "Page");
		request.setAttribute("sites", sites);
		request.setAttribute("questions", questions);
		try {
			rd.forward(request,response);
		} catch (Exception e) {
			// e.printStackTrace();
		} 
	}
}