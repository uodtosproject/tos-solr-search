package br.com.betohayasida.SolrSearch.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import br.com.betohayasida.Diff.DiffEntry;
import br.com.betohayasida.Diff.DiffMod;
import br.com.betohayasida.SolrSearch.DB.Page;
import br.com.betohayasida.SolrSearch.DB.PagesModel;
import br.com.betohayasida.SolrSearch.DB.Site;
import br.com.betohayasida.SolrSearch.DB.SitesModel;

public class PagesController extends HttpServlet {

	private static final long serialVersionUID = 6738408051364248371L;

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html");
		String URI = request.getRequestURI();
    	String[] URIparts = URI.split("/");
    	
    	if(URIparts.length > 2){
			if(URIparts[2].equalsIgnoreCase("page")){
				if(URIparts.length == 4){
					String name = URIparts[3];
					
			    	SitesModel siteModel = new SitesModel();
			    	List<Site> sites = siteModel.getSites();
			    	
					PagesModel pagesModel = new PagesModel();
					List<Page> pages = pagesModel.get(name);
				
					List<String> currentParents = new ArrayList<String>();
					for(Site site : sites){ 
						currentParents.add(site.getName() + "|" + site.getVisitedOn()); 
					}
					if(pages.size() > 0){
						Page currentPage = null; 
						for(Page p : pages){ 
							if(currentParents.contains(p.getParent())) { 
								currentPage = p; 
							}
						} 
						DiffMod diffMod = new DiffMod();
						List<DiffEntry> diffs = new ArrayList<DiffEntry>();
						for(Page p : pages){ 
							if(!p.getRetrievedOn().equalsIgnoreCase(currentPage.getRetrievedOn())) { 
								diffs.add(new DiffEntry(p, StringEscapeUtils.unescapeHtml4(diffMod.diff_prettyHtml(diffMod.diff_main(currentPage.getText(), p.getText().replace("account", "super-accoun"), false))).replaceAll("¦", "")));
							}
						} 
						RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/page.jsp");
						request.setAttribute("title", "History for " + currentPage.getTitle());
						request.setAttribute("sites", sites);
						request.setAttribute("pages", pages);
						request.setAttribute("diffs", diffs);
						request.setAttribute("currentPage", currentPage);
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
		request.setAttribute("title", "Page");
		request.setAttribute("sites", sites);
		try {
			rd.forward(request,response);
		} catch (Exception e) {
			// e.printStackTrace();
		} 
	}
}