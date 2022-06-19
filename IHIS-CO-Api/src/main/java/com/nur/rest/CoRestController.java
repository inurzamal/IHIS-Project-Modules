package com.nur.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nur.services.CoService;

@RestController
public class CoRestController {
	
	@Autowired
	private CoService service;
	
	@GetMapping("/notice")
	public Map<String, Integer> generateNotices(){
		return service.generateNotices();
		
	}

}
