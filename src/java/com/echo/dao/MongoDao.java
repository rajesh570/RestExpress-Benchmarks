/**
 *
 */
package com.echo.dao;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * @author VGASPRI
 *
 */
/**
 * @author VGASPRI
 *
 */
public class MongoDao {

	private static Mongo mongo;

	static {

		try {

			mongo = new Mongo("localhost", 27017);

			DB db = mongo.getDB("test");
			DBCollection collection = db.getCollection("echoTestColl");
			collection.remove(new BasicDBObject());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves echoValue to the default mongo db: localhost:27017/test.
	 * creates echoTestColl
	 * Deletes collection and record after its done.
	 * @param echoValue
	 */
	public static void saveToMongo(String echoValue)
	{
		try {
			DB db = mongo.getDB("test");

			// get a single collection, create if it does not exist
			DBCollection collection = db.getCollection("echoTestColl");

			// BasicDBObject example
//			System.out.println("BasicDBObject example...");
			BasicDBObject document = new BasicDBObject();
			document.put("data", echoValue);
			//document.put("table", "hosting");

			/*BasicDBObject documentDetail = new BasicDBObject();
			documentDetail.put("records", "99");
			documentDetail.put("index", "vps_index1");
			documentDetail.put("active", "true");
			document.put("detail", documentDetail);
			 */
			collection.insert(document);

//			DBCursor cursorDoc = collection.find();
//			while (cursorDoc.hasNext()) {
//				System.out.println(cursorDoc.next());
//			}

//			collection.remove(new BasicDBObject());

			// BasicDBObjectBuilder example
			/*System.out.println("BasicDBObjectBuilder example...");
			BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
				.add("database", "mkyongDB")
                                .add("table", "hosting");

			BasicDBObjectBuilder documentBuilderDetail = BasicDBObjectBuilder.start()
                                .add("records", "99")
                                .add("index", "vps_index1")
				.add("active", "true");

			documentBuilder.add("detail", documentBuilderDetail.get());

			collection.insert(documentBuilder.get());

			DBCursor cursorDocBuilder = collection.find();
			while (cursorDocBuilder.hasNext()) {
				System.out.println(cursorDocBuilder.next());
			}

			collection.remove(new BasicDBObject());
 			*/

			// Map example
			/*System.out.println("Map example...");
			Map<String, Object> documentMap = new HashMap<String, Object>();
			documentMap.put("database", "mkyongDB");
			documentMap.put("table", "hosting");

			Map<String, Object> documentMapDetail = new HashMap<String, Object>();
			documentMapDetail.put("records", "99");
			documentMapDetail.put("index", "vps_index1");
			documentMapDetail.put("active", "true");

			documentMap.put("detail", documentMapDetail);

			collection.insert(new BasicDBObject(documentMap));

			DBCursor cursorDocMap = collection.find();
			while (cursorDocMap.hasNext()) {
				System.out.println(cursorDocMap.next());
			}

			collection.remove(new BasicDBObject());
 			*/
			// JSON parse example

			/*System.out.println("JSON parse example...");

			String json = "{'database' : 'mkyongDB','table' : 'hosting'," +
			"'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}}";

			DBObject dbObject = (DBObject)JSON.parse(json);

			collection.insert(dbObject);

			DBCursor cursorDocJSON = collection.find();
			while (cursorDocJSON.hasNext()) {
				System.out.println(cursorDocJSON.next());
			}

			collection.remove(new BasicDBObject());
 			*/

		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
	
	public static String readWrite(String echoValue)
	{
		try {
				DB db = mongo.getDB("test");
				DBCollection items = db.getCollection("echoTestColl");
				BasicDBObject query = new BasicDBObject();
				query.put("data", echoValue);
				DBCursor cursor = items.find(query);
				while (cursor.hasNext()) {
				    return(cursor.next().toString());
				}
		    } catch (MongoException e){
		    	e.printStackTrace();
		    }
		    
		return null;
		 
	}
}
