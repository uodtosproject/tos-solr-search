package br.com.betohayasida.SolrInterface;

public class ParsedString{
	private String s = "";
	
	public void concat(String str){
		this.s = this.s.concat(str);
	}
	
	public String print(){
		return this.s;
	}
	
	public void reset(){
		this.s = "";
	}
	
}