package br.com.betohayasida.SolrSearch.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

import br.com.betohayasida.SolrSearch.DB.Question;
import br.com.betohayasida.SolrSearch.DB.QuestionsModel;
import br.com.betohayasida.SolrSearch.DB.Site;
import br.com.betohayasida.SolrSearch.DB.SitesModel;

/**
 * Controller for REST calls to Crawler
 * @author rkhayasidajunior
 *
 */
public class CrawlController extends HttpServlet {

	private static final long serialVersionUID = -1336330668711052523L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
			if(URIparts[2].equalsIgnoreCase("crawl")){
				String url = (String) request.getParameterValues("url")[0];
				
				ApacheHttpClientPost restClient = new ApacheHttpClientPost();
				
				String output = restClient.post(url);
				
				JsonParserFactory factory=JsonParserFactory.getInstance();
				JSONParser parser=factory.newJsonParser();
				Map<?, ?> jsonData = parser.parseJson(output);

				String status = (String) jsonData.get("status");
				String message = (String) jsonData.get("message");
				
				RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/crawl.jsp");
				request.setAttribute("title", url);
				request.setAttribute("status", status);
				request.setAttribute("message", message);
				rd.forward(request,response);
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