package com.nur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nur.bindings.EligDtlsBinding;
import com.nur.services.EdService;

@RestController
public class EdRestController {
	
	@Autowired
	private EdService service;
	
	@GetMapping("/eligibility/{caseNum}")
	public ResponseEntity<EligDtlsBinding> determineEligibility(@PathVariable Integer caseNum){	
		EligDtlsBinding eligibility = service.checkEligibility(caseNum);	
		return new ResponseEntity<>(eligibility, HttpStatus.OK);	
	}
}
