package br.com.betohayasida.SolrSearch.DB;

import java.util.ArrayList;
import java.util.List;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * DB Handler for Pages collection
 * @author rkhayasidajunior
 *
 */
public class PagesModel extends MongoBase {
	private String DBNAME = "crawler";
	private String COLLECTIONNAME = "pages";

	/**
	 * Connects to the DB
	 * @return True if the connection is successful
	 */
	public boolean connect(){
		return super.connect(DBNAME, COLLECTIONNAME);
	}
	
	public void setRelevance(String name, int rel){
		if(this.connect()){
			BasicDBObject query = new BasicDBObject("name", name);
			DBCursor cursor = collection.find(query);
			
			try {
				if(cursor.hasNext()) {
					DBObject item = cursor.next();
					String relevance = (String) item.get("relevance");

					BasicDBObject doc = new BasicDBObject("name", name);
					
					if(relevance != null){
						doc.append("relevance", String.valueOf(Integer.valueOf(relevance) + rel));
					} else {
						doc.append("relevance", String.valueOf(rel));
					}
			
					BasicDBObject newDocument = new BasicDBObject();
					
					newDocument.append("$set", doc);
					collection.update(query, newDocument);
				}
			} finally {
				
				cursor.close();
			}
			
			this.close();
		
		}
	}
	
	public int getRelevance(String name){
		int relevance = 0;
		
		if(this.connect()){
			BasicDBObject query = new BasicDBObject("name", name);
			DBCursor cursor = collection.find(query);
			
			try {
				
				while(cursor.hasNext()) {
					DBObject result = cursor.next();
					String r = (String) result.get("relevance");
					if(r != null){
						relevance = Integer.valueOf(r);
					}
				}
				
			} finally {
				cursor.close();
			}
			
			this.close();
		}
		
		return relevance;
	}
	
	public List<Page> get(String name){
		List<Page> results = new ArrayList<Page>();
		
		if(this.connect()){
			BasicDBObject query = new BasicDBObject("name", name);
			DBCursor cursor = collection.find(query);
			
			try {
				
				while(cursor.hasNext()) {
					DBObject result = cursor.next();
					
					Page page = new Page(
							(String) result.get("name"),
							(String) result.get("parent"),
							(String) result.get("retrievedOn"),
							(String) result.get("text"),
							(String) result.get("title"),
							(String) result.get("url"));
					String relevance = (String) result.get("relevance");
					if(relevance != null){
						page.setRelevance(Integer.valueOf(relevance));
					}
					results.add(page);
				}
				
			} finally {
				cursor.close();
			}
			this.close();
		}
		
		return results;
	}

}
