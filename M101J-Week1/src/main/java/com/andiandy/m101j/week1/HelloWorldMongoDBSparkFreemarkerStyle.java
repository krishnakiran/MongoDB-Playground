package com.andiandy.m101j.week1;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;

public class HelloWorldMongoDBSparkFreemarkerStyle {
	public static void main(String[] args) throws UnknownHostException {
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");
		
		MongoClient client = new MongoClient("localhost", 27017);
		
		DB database = client.getDB("test");
		final DBCollection collection = database.getCollection("names");

		
		Spark.get(new Route("/") {	
			@Override
			public Object handle(Request arg0, Response arg1) {
				StringWriter writer = new StringWriter();
				try {
					Template helloTemplate = configuration.getTemplate("hello.ftl");
					
					DBObject document = collection.findOne();
					
					helloTemplate.process(document, writer);
					
					System.out.println(writer);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					halt(500);
					e.printStackTrace();
				}
				return writer;
			}
		});

	}
}
