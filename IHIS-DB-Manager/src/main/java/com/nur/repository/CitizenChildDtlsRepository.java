package com.nur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.entity.CitizenChildDtlsEntity;

public interface CitizenChildDtlsRepository extends JpaRepository<CitizenChildDtlsEntity, Integer> {

	public List<CitizenChildDtlsEntity> findByCaseNum(Integer caseNum);
}
