package com.nur.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitizenAppRegBinding {
	
	private Integer caseNum;	
	private String fullName;	
	private String email;	
	private String mobileNum;	
	private String gender;	
	private LocalDate dob;
	private Integer zzn;
	private String stateName;
}
