package com.nur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nur.entity.GraduationYearsEntity;

public interface GraduationYearsRepository extends JpaRepository<GraduationYearsEntity, Integer>{

	@Query(value="select year from GraduationYearsEntity")
	public List<Integer> graduationYears();
}
