package br.com.betohayasida.SolrSearch.DB;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
