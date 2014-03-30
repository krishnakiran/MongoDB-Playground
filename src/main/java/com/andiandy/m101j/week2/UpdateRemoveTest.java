package com.andiandy.m101j.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class UpdateRemoveTest {
	public static void main( String[] args ) throws UnknownHostException {
		DBCollection collection = getDbCollection();

		List<String> names = Arrays.asList( "alice", "bobby", "cathy", "david", "ethan" );

		for( String name : names ) {
			collection.insert( new BasicDBObject( "_id", name ) );
		}

		collection.remove( new BasicDBObject("_id","alice") );

		printCollection( collection );

	}

	private static void printCollection( final DBCollection collection ) {
		DBCursor cursor = collection.find();


		try {
			while( cursor.hasNext() ) {
				DBObject cur = cursor.next();
				System.out.println( cur );
			}
		} finally {
			cursor.close();
		}
	}

	private static DBCollection getDbCollection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB( "course" );
		DBCollection lines = courseDB.getCollection( "UpdateRemoveTest" );
		lines.drop();
		return lines;
	}
}
