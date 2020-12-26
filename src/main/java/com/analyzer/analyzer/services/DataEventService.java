package com.analyzer.analyzer.services;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analyzer.analyzer.beans.DataEvent;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service
public class DataEventService {
    @Autowired 
    private MongoClient mongoClient ;
    
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    private static final String DATA_EVENTS_COLLECTION_NAME="data_events"; 
    private Gson gson = new Gson(); 
	
    
    private void init(String dbName) {
    	 this.db = mongoClient.getDatabase(dbName);
    	 if(this.db!=null) {
    		 this.collection =this.db.getCollection(DATA_EVENTS_COLLECTION_NAME);	 
    	 }
    }
	public boolean saveEvent(DataEvent event) {
		this.init(event.getDbName());
    	Document parsedDocument = Document.parse(gson.toJson(event));
    	this.collection.insertOne(parsedDocument);
    	 return true;
     }
}
