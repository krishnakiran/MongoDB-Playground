package com.andiandy.m101j.week2;

import com.mongodb.BasicDBObject;

import java.util.Arrays;
import java.util.Date;

public class DocumentRepresentationTest {

	public static void main( String[] args ) {
		BasicDBObject doc = new BasicDBObject();
		doc.put( "userName", "Andi" );
		doc.put( "birthDate", new Date( 43543543 ) );
		doc.put( "programmer", true );
		doc.put( "age", 18 );
		doc.put( "language", Arrays.asList( "Java", "C++" ) );
		doc.put( "address", new BasicDBObject( "street", "20 Main" )
				.append( "zip", "4567" )
				.append( "town", "Westfield" ) );



	}

}
