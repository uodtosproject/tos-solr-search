package br.com.betohayasida.SolrInterface;

import java.util.ArrayList;
import java.util.List;

public class Result {

	private String id = null;
	private String name = null;
	private String url = null;
	private String parent = null;
	private String text = null;
	private String title = null;
	private List<String> highlighted = new ArrayList<String>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getHighlighted() {
		return highlighted;
	}
	public void setHighlighted(List<String> highlighted) {
		this.highlighted = highlighted;
	}
	public void addHighlighted(String highlighted) {
		this.highlighted.add(highlighted);
	}
}
