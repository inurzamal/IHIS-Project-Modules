package com.nur.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Profile {
	
	private Integer acctId;
	private String fullName;
	private String email;
	private String mobileNum;
	private String gender;
	private Integer zzn;
	private LocalDate dob;

}
