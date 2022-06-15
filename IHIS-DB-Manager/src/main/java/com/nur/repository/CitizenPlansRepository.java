package com.nur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.entity.CitizenPlansEntity;

public interface CitizenPlansRepository extends JpaRepository<CitizenPlansEntity, Integer> {
	
	public CitizenPlansEntity findByCaseNum(Integer caseNum);

}
