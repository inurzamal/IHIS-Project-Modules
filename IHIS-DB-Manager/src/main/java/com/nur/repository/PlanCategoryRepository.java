package com.nur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nur.entity.PlanCategoryEntity;

public interface PlanCategoryRepository extends JpaRepository<PlanCategoryEntity, Integer>{
	
	@Query("select categoryName from PlanCategoryEntity")
	public List<String> getPlanCategoryName();

}
