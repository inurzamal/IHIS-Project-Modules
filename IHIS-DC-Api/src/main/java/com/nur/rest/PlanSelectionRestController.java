package com.nur.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nur.bindings.PlanSelection;
import com.nur.services.DcService;

@RestController
public class PlanSelectionRestController {
	
	@Autowired
	private DcService service;
	
	@GetMapping("/planNames")
	public ResponseEntity<Map<Integer,String>> getPlaneNames(){		
		Map<Integer,String> map = service.getPlanNames();			
		return new ResponseEntity<>(map, HttpStatus.OK);		
	}
	
	@PostMapping("/savePlan")
	public ResponseEntity<String> savePlanSelection(PlanSelection planSection){		
		String msg = service.savePlanSelection(planSection);
		return new ResponseEntity<>(msg, HttpStatus.OK);				
	}
	
}
