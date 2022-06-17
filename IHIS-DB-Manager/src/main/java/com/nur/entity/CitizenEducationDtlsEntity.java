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
@Table(name = "CITIZEN_GRADUATION_DTLS")
public class CitizenEducationDtlsEntity {
	
	@Id
	@GeneratedValue
	@Column(name="GRADUATION_ID")
	private Integer graduationId;
	
	@Column(name="CASE_NUM", unique=true)
	private Integer caseNum;
	
	@Column(name="HIGHEST_DEGREE")
	private String highestDegree;
	
	@Column(name="GRADUATION_YEAR_ID")
	private Integer graduationYearId;
	
	@Column(name="UNIVERSITY")
	private String university;
	
	@Column(name="CREATED_DATE")
	private LocalDate createdDate;
	
	@Column(name="UPDATED_DATE")
	private LocalDate updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

}
