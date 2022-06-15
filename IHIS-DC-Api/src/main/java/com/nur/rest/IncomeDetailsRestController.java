package com.nur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nur.bindings.Income;
import com.nur.services.DcService;

@RestController
public class IncomeDetailsRestController {
	
	@Autowired
	private DcService service;
	
	@PostMapping("/saveIncome")
	public ResponseEntity<String> saveIncome(Income income){		
		String status = service.saveIncome(income);
		return new ResponseEntity<>(status, HttpStatus.OK);		
	}

}
