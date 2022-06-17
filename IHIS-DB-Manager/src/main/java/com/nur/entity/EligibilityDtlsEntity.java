package com.nur.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ELIG_DTLS")
public class EligibilityDtlsEntity {

	@Id
	@GeneratedValue
	@Column(name="ELIG_ID")
	private Integer eligId;
	
	@Column(name="CASE_NUM", unique=true)
	private Integer caseNum;
	
	@Column(name="PLAN_NAME")
	private String planName;
	
	@Column(name="PLAN_STATUS")
	private String planStatus;
	
	@Column(name="START_DATE")
	private LocalDate startDate;
		
	@Column(name="END_DATE")
	private LocalDate endDate;
	
	@Column(name="BENEFIT_AMT")
	private Integer benifitAmt;
	
	@Column(name="DENIAL_REASON")
	private String DenialReason;
	
	@Column(name="CREATED_DATE")
	private LocalDate createdDate;
	
	@Column(name="UPDATED_DATE")
	private LocalDate updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
}
