package com.analyzer.analyzer.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.analyzer.analyzer.beans.ReportsRequest;
import com.analyzer.analyzer.services.ReportsService;

@RestController
public class ReportsController {
    @Autowired
    private ReportsService reportsService;
	
	@PostMapping(path="/reports/getPopularQueries")
	//INFO: we can get post request this way, using hashmap as well, if we don;t want to write a bean: (@RequestBody Map<String, Object> request)
	public ResponseEntity<Map<String, Object>> getPopularQueries(@Valid @RequestBody ReportsRequest request) {
		//basic level validation
		Map<String, Object> resp = new HashMap<String, Object>();
    	resp.put("popularQueries", reportsService.getMostPopularQueries(request));
		return new ResponseEntity<Map<String, Object>>(resp,HttpStatus.OK);
	}
	
	@PostMapping(path="/reports/noResultQueries")
	//INFO: we can get post request this way, using hashmap as well, if we don;t want to write a bean: (@RequestBody Map<String, Object> request)
	public ResponseEntity<Map<String, Object>> noResultQueries(@Valid @RequestBody ReportsRequest request) {
		//basic level validation
		Map<String, Object> resp = new HashMap<String, Object>();
    	resp.put("popularQueries", reportsService.getQueriesWithNoResult(request));
		return new ResponseEntity<Map<String, Object>>(resp,HttpStatus.OK);
	}
}
