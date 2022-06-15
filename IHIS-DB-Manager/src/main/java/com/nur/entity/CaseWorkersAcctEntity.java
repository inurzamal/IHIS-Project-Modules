package com.nur.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CW_ACCOUNTS")
public class CaseWorkersAcctEntity {
	
	@Id
	@GeneratedValue
	@Column(name="ACCT_ID")
	private Integer acctId;
	
	@Column(name="FULLNAME")
	private String fullName;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PWD")
	private String pazzword;
	
	@Column(name="MOBILE_NUM")
	private String mobileNum;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="DOB")
	private LocalDate dob;
	
	@Column(unique=true,name="ZZN")
	private Integer zzn;
	
	@Column(name="ACTIVE_SW")
	private Character activeSw;
	
	@Column(name="CREATED_DATE")
	private LocalDate createdDate;
	
	@Column(name="UPDATED_DATE")
	private LocalDate updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	
}
