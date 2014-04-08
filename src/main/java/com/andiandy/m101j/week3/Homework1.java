package com.andiandy.m101j.week3.hw1;

import com.mongodb.*;

import java.net.UnknownHostException;

public class Homework1 {

	public static void main( String[] args ) throws UnknownHostException {
		DBCollection collection = getDbCollection();



		DBCursor cusor = collection.find();
		int i = 0;
		try{
			while( cusor.hasNext()){
				i++;
				BasicDBObject student = (BasicDBObject) cusor.next();
				BasicDBList scores = (BasicDBList) student.get( "scores" );
				double lowest_score = 100.0;

				String key_of_lowest_score = "";
				for (String key : scores.keySet()){
					DBObject score = (DBObject) scores.get( key );
					double actual_score = Double.parseDouble( score.get( "score" ).toString() );
					if (score.get( "type" ).equals( "homework" )){
						if (actual_score < lowest_score){
							lowest_score = actual_score;
							key_of_lowest_score = key;
						}
					}
				}
				scores.removeField( key_of_lowest_score );
				student.put( "scores", scores );

				collection.update( new BasicDBObject( "_id", student.get( "_id" )), student, true, false );

			}
		} finally {
			cusor.close();
		}

	}

	private static DBCollection getDbCollection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB( "school" );
		DBCollection lines = courseDB.getCollection( "students" );
		return lines;
	}
}
