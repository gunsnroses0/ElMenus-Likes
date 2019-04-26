
    
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import Commands.Command;
import Service.LikeService;

public class Like {
	private static final String COLLECTION_NAME = "likes";
	static Like instance = new Like();

	private static MongoCollection<Document> collection = null;
	private static final MongoClientURI uri = new MongoClientURI(
			"mongodb://localhost");
	public static HashMap<String, Object> create(HashMap<String, Object> attributes, String target_id) {

		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("El-Menus");
		System.out.println(uri);


		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
		Document newlike = new Document();

		for (String key : attributes.keySet()) {
			newlike.append(key, attributes.get(key));
		}

		newlike.append("target_id", new ObjectId(target_id));
		
		collection.insertOne(newlike);
		mongoClient.close();
		
		return attributes;
	}
	public static HashMap<String, Object> delete(String messageId) {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://localhost");

		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("El-Menus");
//    	Method method =   Class.forName("PlatesService").getMethod("getDB", null);
//    	MongoDatabase database = (MongoDatabase) method.invoke(null, null);

		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection("likes");
		System.out.println("Inside Delete");
		BasicDBObject query = new BasicDBObject();
		System.out.println(messageId);
		query.put("_id", new ObjectId(messageId));

		System.out.println(query.toString());
		HashMap<String, Object> message = null;
		Document doc = collection.findOneAndDelete(query);
		JSONParser parser = new JSONParser(); 
		try {
			JSONObject json = (JSONObject) parser.parse(doc.toJson());
		
			message = Command.jsonToMap(json);
			
			System.out.println(message.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return message;
	}
	public static ArrayList<HashMap<String, Object>> get(String messageId) {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://localhost");

		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("El-Menus");
//    	Method method =   Class.forName("PlatesService").getMethod("getDB", null);
//    	MongoDatabase database = (MongoDatabase) method.invoke(null, null);

		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection("likes");
		System.out.println("Inside Get");
		BasicDBObject query = new BasicDBObject();
		System.out.println(messageId);
		query.put("target_id", new ObjectId(messageId));

		System.out.println(query.toString());
		HashMap<String, Object> message = null;
		FindIterable<Document> docs = collection.find(query);
		JSONParser parser = new JSONParser(); 
		ArrayList<HashMap<String, Object>> likes = new ArrayList<HashMap<String, Object>>();

		for (Document document : docs) {
			JSONObject json;
			try {
				json = (JSONObject) parser.parse(document.toJson());
				HashMap<String, Object> like = Command.jsonToMap(json);	
				likes.add(like);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		mongoClient.close();
        return likes;
	}
	
	  
	
}
