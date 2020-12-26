package com.analyzer.analyzer.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.analyzer.analyzer.beans.DataEvent;
import com.analyzer.analyzer.services.DataEventService;

@RestController
public class DataEventController {
    @Autowired
    DataEventService dataEventService;
	
	@PostMapping(path="/addEvent")
	public boolean saveEvent(@Valid @RequestBody DataEvent event) {
    	//INFO: if creating time is not set, we are setting it to now.
    	System.out.println("===debug===event"+event);
		if(event.getCreateTs()==null) {
    		event.setCreateTs(new Date());
    	}
    	dataEventService.saveEvent(event);
    	return true;
    }
}
