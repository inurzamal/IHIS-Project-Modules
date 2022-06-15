package com.nur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.entity.CitizenIncomeDtlsEntity;

public interface CitizenIncomeDtlsRepository extends JpaRepository<CitizenIncomeDtlsEntity, Integer> {
	
	public CitizenIncomeDtlsEntity findByCaseNum(Integer caseNum);

}
