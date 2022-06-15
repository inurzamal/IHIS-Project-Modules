package com.nur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.entity.CitizenEducationDtlsEntity;

public interface CitizenEducationDtlsRepository extends JpaRepository<CitizenEducationDtlsEntity, Integer> {
	
	public CitizenEducationDtlsEntity findByCaseNum(Integer caseNum);

}
