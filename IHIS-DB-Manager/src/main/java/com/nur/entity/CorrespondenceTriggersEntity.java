package com.nur.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "CO_TRIGGERS")
public class CorrespondenceTriggersEntity {

	@Id
	@GeneratedValue
	@Column(name="TRG_ID")
	private Integer trgId;
	
	@Column(name="CASE_NUM")
	private String caseNum;
	
	@Column(name="TRG_STATUS")
	private String trgStatus;
	
	@Column(name="NOTICE")
	private Blob notice;
}
