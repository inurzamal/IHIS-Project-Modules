package com.nur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nur.bindings.Childs;
import com.nur.services.DcService;

@RestController
public class ChildRestController {
	
	@Autowired
	private DcService service;
	
	@PostMapping("/saveChilds")
	public ResponseEntity<String> saveChildren(Childs childs){		
		String status = service.saveKids(childs);
		return new ResponseEntity<>(status, HttpStatus.OK);		
	}
	
	

}
