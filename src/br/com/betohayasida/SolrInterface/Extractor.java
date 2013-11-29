package br.com.betohayasida.SolrInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Extractor of results
 * @author rkhayasidajunior
 *
 */
public class Extractor {
	
	public List<String> extract(List<String> highlights){
		List<String> highlightsExtraction = new ArrayList<String>();
		System.out.println(highlights.size());
		for(String h : highlights){
			Document doc = Jsoup.parseBodyFragment(h);
			Elements hls = doc.getElementsByAttributeValue("class", "highlight");
			boolean bodyFound = false;
			for(Element hl : hls){
				if(hl.parent().tagName().equalsIgnoreCase("body")){
					if(!bodyFound){
						highlightsExtraction.add(hl.parent().html());
					}
					bodyFound = true;
				} else {
					highlightsExtraction.add(hl.parent().html());
				}
			}
		}
		Set<String> hls = new HashSet<String>(highlightsExtraction);
		highlightsExtraction.clear();
		for(String hl : hls){
			highlightsExtraction.add(hl);
		}
		return highlightsExtraction;
	}
}
