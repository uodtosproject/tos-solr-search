package br.com.betohayasida.SolrInterface;

public class QueryParser {
	private String field = "*";
	
	public QueryParser(){
		
	}
	
	public QueryParser(String field){
		this.field = field;
	}
	
	public void setField(String field){
		this.field = field;
	}
	
	public String getField(){
		return this.field;
	}
	
	public String parse_keywords(String subquery){
		String[] keywords = subquery.split(",");
		String parsed_subquery = "";
		
		int i = 0;
		for(String kw : keywords){
			if(kw.length() > 0){
				parsed_subquery = parsed_subquery + this.field + ":\"" + kw.trim() + "\"";
				if(i < keywords.length - 1){
					parsed_subquery = parsed_subquery + " AND ";
				}
			}
			i++;
		}

		return parsed_subquery;
	}
	
	public String parse_keywords_out(String subquery){
		String[] keywords = subquery.split(" ");
		String parsed_subquery = "";
		
		for(String kw : keywords){
			if(kw.length() > 0){
				if(!kw.trim().equals("OR") && !kw.trim().equals("AND") && !kw.trim().equals("NOT")){
					parsed_subquery = parsed_subquery + " " + this.field + ":\"" + kw.trim() + "\"";
				} else {
					parsed_subquery = parsed_subquery + " " + kw;
				}
			}
		}
		if(parsed_subquery.length() > 0) parsed_subquery = parsed_subquery + " ";
		return parsed_subquery;
	}
	
	public String parse_aux(String subquery, ParsedString parsed_query){
		String r = "";
		
		int i;
		boolean stop = false;
		boolean start = false;
		ParsedString keywords = new ParsedString();
		for(i = 0; i < subquery.length() && !stop; i++){
			
			if(subquery.charAt(i) == '('){ 
				
				start = true;
				parsed_query.concat(parse_keywords_out(keywords.print()) + "(");
				keywords.reset();
				subquery = subquery.substring(0, i) + parse_aux(subquery.substring(i + 1), parsed_query);
				
			} else if(subquery.charAt(i) == ')'){

				if(start) {
					stop = true;
					r = subquery.substring(i + 1);
				} 
				parsed_query.concat(parse_keywords(keywords.print()) + ")");
				keywords.reset();
				
			} else {
				
				keywords.concat(subquery.charAt(i) + "");
				
			}
			
		}
		
		parsed_query.concat(parse_keywords_out(keywords.print()));

		return r;
	}
	
	public String parse(String query){
		ParsedString parsed_query = new ParsedString();

		int k;
		int count = 0;
		boolean malFormed = false;
		for(k = 0; k < query.length() && !malFormed; k++){
			if(query.charAt(k) == '('){
				count++;
			} else if(query.charAt(k) == ')'){
				count--;
			}
			if(count < 0){
				malFormed = true;
			}
		}
		if(count != 0) malFormed = true;
		
		if(malFormed){
			parsed_query.concat("Mal Formed String");
		} else {
			ParsedString keywords = new ParsedString();
			for(k = 0; k < query.length(); k++){
	
				if(query.charAt(k) == '('){
					
					parsed_query.concat(parse_keywords_out(keywords.print()) + "(");
					keywords.reset();
					query = query.substring(0, k) + parse_aux(query.substring(k + 1), parsed_query);
					
				} else if(query.charAt(k) == ')'){
					
					parsed_query.concat(parse_keywords(keywords.print()) + ")");
					keywords.reset();
					
				} else {
					
					keywords.concat(query.charAt(k) + "");
					
				}
				
			}
			parsed_query.concat(parse_keywords_out(keywords.print()));
		}
		return parsed_query.print();
	}
	
	
	public static void main(String[] args) {
		//System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt"))));
		String query = "((deceased user, contact) OR (account)) AND twitter";
		QueryParser parser = new QueryParser("title");

		System.out.println(parser.parse(query));
		System.out.println(query);

	}

}