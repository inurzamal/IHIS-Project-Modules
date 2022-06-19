package com.nur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.entity.EligibilityDtlsEntity;

public interface EligibilityDtlsRepository extends JpaRepository<EligibilityDtlsEntity, Integer> {
	
	public EligibilityDtlsEntity findByCaseNum(Integer caseNum);

}
