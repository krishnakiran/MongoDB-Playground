package courseQ7;

import java.net.UnknownHostException;
import java.util.AbstractList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.list.*;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

public class Photosharing {

	public static void main(String[] args) throws UnknownHostException {
		DBCollection albums = getDbCollection("albums");
		DBCollection images = getDbCollection("images");
		
		int countOfSunriseImages = countImagesByTag(images, "sunrises");
		
		System.out.println(countOfSunriseImages);
		
		HashSet<Integer> notOrphans = getIdsFromImagesThatAreNotOrphans(albums);
		
		HashSet<Integer> orphans = getIdsFromImagesThatAreOrphans(images, notOrphans);
		
		for (int _id : orphans){
			QueryBuilder builder = QueryBuilder.start("_id").is(_id);
			DBObject imageToRemove = images.findOne(builder.get());
			images.remove(imageToRemove);
		}
		System.out.println(countImagesByTag(images, "sunrises"));

	}


	public static HashSet<Integer> getIdsFromImagesThatAreOrphans(DBCollection images,
			HashSet<Integer> notOrphans) {
		DBCursor cursor = images.find();
		try{
			HashSet<Integer> orphans = new HashSet<Integer>();
			while (cursor.hasNext()){
				BasicDBObject image = (BasicDBObject) cursor.next();
				int image_id = image.getInt("_id");
				if (!notOrphans.contains(image_id)){
					orphans.add(image_id);
				}
			}
			return orphans;
		} finally{
			cursor.close();
		}
	}


	public static HashSet<Integer> getIdsFromImagesThatAreNotOrphans(DBCollection albums) {
		DBCursor cursor = albums.find();
		try{
			HashSet<Integer> notOrphans = new HashSet<Integer>();
			
			while(cursor.hasNext()){
				
				BasicDBObject album = (BasicDBObject) cursor.next();
				BasicDBList imagesInAlbum = (BasicDBList) album.get("images");
				for (Object object : imagesInAlbum.toArray()){
					int image_id = (int) object;
					if (!notOrphans.contains(image_id)){
						notOrphans.add(image_id);
					}
				}
				
			}			
			return notOrphans;
		}
		finally{
			cursor.close();
		}
	}


	public static int countImagesByTag(DBCollection images, String tag) {
		DBCursor cursor = images.find();
		try{
			int i = 0;
			while (cursor.hasNext()){
				BasicDBObject image = (BasicDBObject) cursor.next();
				BasicDBList tags = (BasicDBList) image.get("tags");
				if (tags.contains(tag)){
					i++;
				}
			}
			return i;
		}
		finally{
			cursor.close();
		}
		
	}

	
	private static DBCollection getDbCollection(String collectionName) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB( "photosharing" );
		DBCollection lines = courseDB.getCollection( collectionName );
		return lines;
	}
}
