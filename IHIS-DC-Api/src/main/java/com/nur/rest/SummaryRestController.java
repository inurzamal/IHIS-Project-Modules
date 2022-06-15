package com.nur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nur.bindings.Summary;
import com.nur.services.DcService;

@RestController
public class SummaryRestController {
	
	@Autowired
	private DcService service;
	
	@GetMapping("/summary")
	public ResponseEntity<Summary> getSummary(Integer caseNum){		
		Summary summary = service.getSummary(caseNum);
		return new ResponseEntity<>(summary, HttpStatus.OK);			
	}
}
