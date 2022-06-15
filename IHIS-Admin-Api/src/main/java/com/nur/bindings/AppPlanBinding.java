package com.nur.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AppPlanBinding {
	
	private Integer planId;
	
	private String planName;
	
	private LocalDate planStDate;	

	private LocalDate planEnDate;
	
	private Integer categoryId;

}
