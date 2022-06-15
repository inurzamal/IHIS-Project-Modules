package com.nur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.entity.CaseWorkersAcctEntity;

public interface CaseWorkersAcctRepository extends JpaRepository<CaseWorkersAcctEntity, Integer> {
	
	public CaseWorkersAcctEntity findByEmailAndPazzword(String email, String pazzword);
	
	public CaseWorkersAcctEntity findByEmail(String email);

}
