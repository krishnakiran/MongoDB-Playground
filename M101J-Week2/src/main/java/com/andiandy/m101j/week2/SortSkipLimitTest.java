package com.andiandy.m101j.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

public class SortSkipLimitTest {
	public static void main( String[] args ) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB( "course" );
		DBCollection lines = courseDB.getCollection( "DotNotationTest" );
		lines.drop();
		Random rand = new Random();

		//insert 10 documents with a random integer as the value of field "x"
		for( int i = 0; i<10; i++ ) {
			lines.insert(
					new BasicDBObject( "_id", i )
							.append( "start",
									 new BasicDBObject( "x", rand.nextInt( 2 ) )
											 .append( "y", rand.nextInt( 90 )+10 )
								   )
							.append( "end",
									 new BasicDBObject( "x", rand.nextInt( 2 ) )
											 .append( "y", rand.nextInt( 90 )+10 )
								   )
						);
		}




		DBCursor cursor = lines.find(  )
				.sort(new BasicDBObject("start.x", 1 ).append( "start.y",-1 ) ).skip( 2 ).limit( 10 );


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
