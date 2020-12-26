package com.analyzer.analyzer.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analyzer.analyzer.beans.ReportsRequest;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service
public class ReportsService {
    @Autowired
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    private static final String DATA_EVENTS_COLLECTION_NAME="data_events"; 
    private static final String GROUPING_QUERY_FIELD="$value.query"; 
    private Gson gson = new Gson(); 
    
    
    private void init(String dbName) {
    	this.db = mongoClient.getDatabase(dbName);
        if(this.db!=null) {
        	this.collection=this.db.getCollection(DATA_EVENTS_COLLECTION_NAME);	
        }
   }
    
    private String getDateInString(Date dateObj) {
    	final String DATE_FORMAT = "MMM dd, yyyy, HH:mm:ss a";
    	SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    	return format.format(dateObj);
    }
    
    private Document buildMatchAggregationQuery(String type,ReportsRequest request,boolean isNoResult) {
    	Document matchAggregationQuery = new Document();
    	
    	
    	ArrayList<Document> conditions = new ArrayList<Document>();
    	//TODO: convert date functions to something returning strings instead of fates and then you're gtg
    	
        conditions.add(new Document("createTs",new Document("$gte",request.getStartTs())));

    	conditions.add(new Document("createTs",new Document("$lte",request.getEndTs())));
//    	conditions.add(new Document("endTs",new Document("$lte",request.getEndTs())));
    	conditions.add(new Document("collection",request.getIndex()));
    	conditions.add(new Document("eventLabel",request.getEventLabel()));
    	conditions.add(new Document("eventCategory",request.getEventCategory()));
    	if(isNoResult==true) {
    		conditions.add(new Document("value.count",new Integer(0)));	
    	}
    	
    	
    	matchAggregationQuery.append("$match", new Document("$and",conditions));
//    	matchAggregationQuery.append("$match", value)
    	
    	return matchAggregationQuery;
    	
    }
    
    private Document buildGroupingQuery() {
    	Document groupLogic = new Document();
    	groupLogic.append("_id", GROUPING_QUERY_FIELD);
    	groupLogic.append("count", new Document("$sum",1));
    	
    	Document groupDocument = new Document("$group",groupLogic);
    	return groupDocument;
    	    	
    }
    
    private ArrayList<Map<String, Object>> buildAggregationPipeLine(ReportsRequest request,boolean isNoResult) {
    	//complex document, also generic so creating it in a function.
    	Document matchDocument = this.buildMatchAggregationQuery("", request,isNoResult);
        Document groupDocument = this.buildGroupingQuery();
        Document sortDocument = new Document("$sort",new Document("count",-1));
        
        Iterable<Document> aggregationIterable =  this.collection.aggregate(Arrays.asList(matchDocument,groupDocument,sortDocument));
        System.out.println("hasNext"+aggregationIterable.iterator().hasNext());
        ArrayList<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
        aggregationIterable.forEach((datum)->{
        	if(datum!=null) {
        		Map resObj = new HashMap<String, Object>(); 
        	    resObj.put("query",datum.get("_id"));
        	    resObj.put("count",datum.get("count"));
        	    results.add(resObj);
        	    }
        }); 
       return results;
    }
    
	public ArrayList<Map<String, Object>> getMostPopularQueries(ReportsRequest request){
		this.init(request.getDbName());
	    return this.buildAggregationPipeLine(request,false);
	}
	
	public ArrayList<Map<String, Object>> getQueriesWithNoResult(ReportsRequest request){
		this.init(request.getDbName());
	    return this.buildAggregationPipeLine(request,true);
	}
}
