package com.nur.entity;

import java.sql.Blob;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "CO_TRIGGERS")
public class CoTriggerEntity {

	@Id
	@GeneratedValue
	@Column(name="TRG_ID")
	private Integer trgId;
	
	@Column(name="CASE_NUM", unique=true)
	private Integer caseNum;
	
	@Column(name="TRG_STATUS")
	private String trgStatus;
	
	@Column(name="NOTICE")
	private Blob notice;
	
	@Column(name="CREATED_DATE")
	private LocalDate createdDate;
	
	@Column(name="UPDATED_DATE")
	private LocalDate updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
}
