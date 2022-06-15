package com.nur.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nur.bindings.CitizenAppRegBinding;
import com.nur.services.ApplicationRegService;

@RestController
public class AppRegRestController {
	
	@Autowired
	private ApplicationRegService service;
	
	@PostMapping("/appreg")
	public ResponseEntity<String> createApplication(@RequestBody CitizenAppRegBinding binding){		
		String status = service.createCitizenApp(binding);						
		return new ResponseEntity<>(status,HttpStatus.OK);		
	}
	
	@GetMapping("/applications")
	public ResponseEntity<List<CitizenAppRegBinding>> viewApplication(){
		List<CitizenAppRegBinding> applications = service.viewCitizenApplications();		
		return new ResponseEntity<>(applications,HttpStatus.OK);		
	}
}
