package com.nur.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nur.bindings.Education;
import com.nur.services.DcService;

@RestController
public class EducationDtlsRestController {
	
	@Autowired
	private DcService service;
	
	@GetMapping("/years")
	public ResponseEntity<List<Integer>> getYear(){		
		List<Integer> years = service.getGraduationYears();
		return new ResponseEntity<>(years, HttpStatus.OK);			
	}
	
	@PostMapping("/saveEducation")
	public ResponseEntity<String> saveEducation(Education education){		
		String status = service.saveEducation(education);
		return new ResponseEntity<>(status, HttpStatus.OK);		
	}

}
