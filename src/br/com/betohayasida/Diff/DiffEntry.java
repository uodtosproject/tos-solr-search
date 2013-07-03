package br.com.betohayasida.Diff;

import br.com.betohayasida.SolrSearch.DB.Page;

public class DiffEntry {
	private Page page = null;
	private String diff;
	
	public DiffEntry(Page page, String diff){
		this.page = page;
		this.diff = diff;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	
}
