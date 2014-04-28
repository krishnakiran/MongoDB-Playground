import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;


public class WriteConcernTest {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient(Arrays.asList(
				new ServerAddress("localhost", 27017),
				new ServerAddress("localhost", 27018),
				new ServerAddress("localhost", 27019)));
		
		client.setWriteConcern(WriteConcern.JOURNALED);
		
		DB db = client.getDB("course");
		DBCollection coll = db.getCollection("write.test");
		coll.setWriteConcern(WriteConcern.JOURNALED);
		coll.drop();
		
		DBObject doc = new BasicDBObject("_id", 1);
		
		coll.insert(doc);
		
		try{
			coll.insert(doc, WriteConcern.JOURNALED);
		} catch (MongoException.DuplicateKey e){
			System.out.println(e.getMessage());
		}

	}

}
