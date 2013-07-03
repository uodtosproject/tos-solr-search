package br.com.betohayasida.SolrInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class Query {
	static String DEFAULT_URL = "http://localhost:8983/solr";
	SolrServer solr = null;
	
	Logger logger = Logger.getLogger(Query.class);
	Appender myAppender = new ConsoleAppender(new SimpleLayout());
	
	public void init(){

	    BasicConfigurator.configure();
	    logger.setLevel(Level.ERROR);
		//myAppender.setLayout(new SimpleLayout());
	    logger.addAppender(myAppender);
	}
	
	public void open(String urlString){
		//init();
		solr = new HttpSolrServer((urlString == null)? DEFAULT_URL : urlString);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Result> query(String q_text, String q_title, String[] parents){
		SolrQuery query = new SolrQuery();
		String query_string = "";
		QueryParser parser = new QueryParser();
		List<Result> results = new ArrayList<Result>();
		
		if(q_text.length() > 0){
			parser.setField("text");
			String pq = parser.parse(q_text);
			
			if(!pq.equals("Mal Formed String")){
				query_string = pq;
			}
		}
		
    	if(q_title.length() > 0){
			parser.setField("title");
			String pq = parser.parse(q_title);
			
			if(!pq.equals("Mal Formed String")){
				query_string = query_string + " AND " + pq;
			}
    	}
    	
    	
    	if(query_string.length() > 0){
		    if(parents != null){
		    	String fq = "";
		    	int i = 0;
		    	int size = parents.length;
			    for(i = 0; i < size; i++){
			    	fq = fq + "parent:\"" + parents[i] + "\"";
			    	if(i < size - 1){
			    		fq = fq + ", ";
			    	}
			    }
		    	query.setFilterQueries(fq);
		    	//query_string = query_string + " AND " + fq;
		    	System.out.println(query_string);
		    }
	    	query.setQuery(query_string);
		    query.setRows(10000000);
			QueryResponse rsp = null;
			SolrDocumentList docs = null;
			
			// Set highlighting fields 
	        query.setHighlight(true); 
	        query.setHighlightFragsize(0); 
	        query.addHighlightField("text"); 
	        query.setHighlightSnippets(1); 
	        query.setHighlightSimplePre("<em class=\"highlight\">"); 
	        query.setHighlightSimplePost("</em>"); 
			
		    try { 
		    	rsp = solr.query(query);
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
		    
		    if(rsp != null){
		    	docs = rsp.getResults();
		    	
		    	if(docs != null){
		    		Iterator<SolrDocument> iter = docs.iterator();
		    		System.out.println(docs.size());
		    	    while (iter.hasNext()) {
		    	    	SolrDocument resultDoc = iter.next();
			    				            
						String id = (String) resultDoc.getFieldValue("id");
						ArrayList<String> parent = (ArrayList) resultDoc.getFieldValue("parent");
						ArrayList<String> name = (ArrayList) resultDoc.getFieldValue("name");
						ArrayList<String> title = (ArrayList) resultDoc.getFieldValue("title");
						ArrayList<String> url = (ArrayList) resultDoc.getFieldValue("url");
						ArrayList<String> text = (ArrayList) resultDoc.getFieldValue("text");
						
						String clean_text = clean(text.get(0), url.get(0));
		    	    	
						Result result = new Result();
						result.setId(id);
						result.setName(name.get(0));
						result.setParent(parent.get(0));
						result.setText(clean_text);
						result.setTitle(title.get(0));
						result.setUrl(url.get(0));
	
		    	    	List<String> highlights = rsp.getHighlighting().get(id).get("text"); 
			    				            
		    	    	if (highlights != null && highlights.size() > 0) {
		    	    		for(String highlight : highlights){
		    	    			String clean_highlight = clean(highlight, url.get(0));
		    	    			result.addHighlighted(clean_highlight);
		    	    		}
		    	    	}
			    				  
						results.add(result);
		    	    }
		    	}
		    }
    	}
	    return results;
	}
	

	public String clean(String source, String url){
		Document page = Jsoup.parse(source, url);
		String cleaned = new String();
		Whitelist myWhitelist = new Whitelist();
		
		page.select("form").remove();
		page.select("script").remove();

		myWhitelist.addTags("p", "a", "b", "i", "br", "h1", "h2", "h3", "h4", "h5", "h6", "em");
		myWhitelist.addAttributes("a", "href");
		myWhitelist.addAttributes("em", "class");
		
		cleaned = Jsoup.clean(page.html(), myWhitelist);
		cleaned = cleaned.replace("&nbsp;", " ");
		
		return cleaned;
	}

}
