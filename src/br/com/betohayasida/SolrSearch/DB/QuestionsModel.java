package br.com.betohayasida.SolrSearch.DB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * Solr query handler
 * @author rkhayasidajunior
 *
 */
public class QuestionsModel extends MongoBase {
	private String DBNAME = "solrsearch";
	private String COLLECTIONNAME = "questions";

	/**
	 * Connects to the DB
	 * @return True if the connection is successful
	 */
	public boolean connect(){
		return super.connect(DBNAME, COLLECTIONNAME);
	}
	
	public List<Question> getAll(){
		List<Question> questions = new ArrayList<Question>();
		
		if(this.connect()){
			DBCursor cursor = collection.find();
			
			try {
				
				while(cursor.hasNext()) {
					DBObject result = cursor.next();
					Question question = new Question();
					question = new Question();
					question.setId((String) result.get("id"));
					question.setQuery((String) result.get("query"));
					question.setText((String) result.get("text"));
					question.setCat((String) result.get("cat"));
					questions.add(question);
				}
				
			} finally {
				cursor.close();
			}
			this.close();
		}
		
		return questions;
	}
	
	public Question get(String id){
		Question question = null;
		
		if(this.connect()){
			BasicDBObject query = new BasicDBObject("id", id);
			DBCursor cursor = collection.find(query);
			
			try {
				
				while(cursor.hasNext()) {
					DBObject result = cursor.next();
					
					question = new Question();
					question.setId(id);
					question.setQuery((String) result.get("query"));
					question.setText((String) result.get("text"));
					question.setCat((String) result.get("cat"));
				}
				
			} finally {
				cursor.close();
			}
			this.close();
		}
		
		return question;
	}

	
	public void save(Question question){
		if(this.connect()){
			BasicDBObject doc = new BasicDBObject("id", hashMD5(question.getText())).
	                append("query", question.getQuery()).
	                append("text", question.getText()).
	                append("cat", question.getCat());
			
			collection.insert(doc);
			this.close();
		}
	}
	

	private static String hashMD5(String text){
		MessageDigest md;
		String id = text;
		
		// Hash the URL through MD5
		try {
			md = MessageDigest.getInstance("MD5");
			id = (new HexBinaryAdapter()).marshal(md.digest(text.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
		}
		
		return id;
	}
	
	public static void main(String[] args){
		QuestionsModel qm = new QuestionsModel();
		Question q = new Question();
		
		q.setText("Time before an account can be terminated");
		q.setCat("1");
		q.setQuery("((cancel account) OR (terminate account) OR (close account)) AND ((period) OR (dormant) OR (inactive))");
		qm.save(q);
	}
}
