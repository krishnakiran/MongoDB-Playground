package com.andiandy.m101j.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

public class FindTest {
	public static void main( String[] args ) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB( "course" );
		DBCollection collection = courseDB.getCollection( "findtest" );
		collection.drop();

		//insert 10 documents with a random integer as the value of field "x"
		for( int i = 0; i<10; i++ ) {
			collection.insert( new BasicDBObject( "x", new Random().nextInt( 2 ) )
									   .append( "y", new Random().nextInt( 100 ) )
									   .append( "z", new Random().nextInt( 1000 ) ) );
		}


		QueryBuilder builder = QueryBuilder.start( "x" ).is( 0 )
										   .and( "y" ).greaterThan( 10 ).lessThan( 70 );


		

		System.out.println( "Find one:" );
		DBObject one = collection.findOne();
		System.out.println( one );

		System.out.println( "\nCount:" );
		long count = collection.count( builder.get() );
		System.out.println( count );

		System.out.println( "\nFind all:" );
		DBCursor cursor = collection.find( builder.get(),
										   new BasicDBObject( "y", true).append( "_id", false ));
		try {
			while( cursor.hasNext() ) {
				DBObject cur = cursor.next();
				System.out.println( cur );
			}
		} finally {
			cursor.close();
		}
	}
}
