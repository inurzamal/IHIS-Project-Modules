package com.nur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.entity.CoTriggerEntity;

public interface CoTriggerRepository extends JpaRepository<CoTriggerEntity, Integer> {
	
	public List<CoTriggerEntity> findByTrgStatus(String trgStatus);

}
