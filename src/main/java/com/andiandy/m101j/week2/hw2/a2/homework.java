package com.andiandy.m101j.week2.hw2.a2;

import com.mongodb.*;

import java.net.UnknownHostException;

public class homework {
	public static void main( String[] args ) throws UnknownHostException {
		DBCollection collection = getDbCollection();


		printCollection( collection );

	}

	private static void printCollection( final DBCollection collection ) {

		QueryBuilder findQuery = QueryBuilder.start("type").is("homework");
		QueryBuilder sortQuery = QueryBuilder.start("student_id").is(1).and("score").is(-1);



		DBCursor cursor = collection.find(findQuery.get()).sort( sortQuery.get() );


		try {
			DBObject prevStudentLowestScore = cursor.next();
			while(cursor.hasNext()){
				DBObject currentStudent =  cursor.next();
				if(((Integer)currentStudent.get("student_id")).intValue() != ((Integer)prevStudentLowestScore.get("student_id")).intValue()){
					System.out.println("Removing: "+prevStudentLowestScore);
					collection.remove(prevStudentLowestScore);
				}
				prevStudentLowestScore = currentStudent;
			}
		} finally {
			cursor.close();
		}
	}

	private static DBCollection getDbCollection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB( "students" );
		DBCollection lines = courseDB.getCollection( "grades" );
		return lines;
	}
}
