package br.com.betohayasida.SolrSearch.DB;

/**
 * Solr query
 * @author rkhayasidajunior
 *
 */
public class Question {
	private String text;
	private String query;
	private String id;
	private String cat;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	
}
