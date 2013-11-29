package br.com.betohayasida.SolrSearch.DB;

/**
 * Class for Site objects
 * @author rkhayasidajunior
 *
 */
public class Site {

	private String name = null;
	private String url = null;
	private String visitedOn = null;
	private String domain = null;
	private String ico = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVisitedOn() {
		return visitedOn;
	}
	public void setVisitedOn(String visitedOn) {
		this.visitedOn = visitedOn;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
}
