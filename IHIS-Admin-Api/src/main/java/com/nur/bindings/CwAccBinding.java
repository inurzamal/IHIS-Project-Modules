package com.nur.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CwAccBinding {
	
	private Integer acctId;	
	private String fullName;
	private String email;	
	private String mobileNum;
	private String gender;
	private LocalDate dob;
	private Integer zzn;
}
