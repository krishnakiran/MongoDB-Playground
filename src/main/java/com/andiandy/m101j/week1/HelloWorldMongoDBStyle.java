package com.andiandy.m101j.week1;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class HelloWorldMongoDBStyle {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient("localhost", 27017);
		
		DB database = client.getDB("test");
		DBCollection collection = database.getCollection("names");

		DBObject document =  collection.findOne();
		System.out.println(document);
	}

}
