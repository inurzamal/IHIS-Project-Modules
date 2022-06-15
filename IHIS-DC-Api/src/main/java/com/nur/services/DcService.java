package com.nur.services;

import java.util.List;
import java.util.Map;

import com.nur.bindings.Childs;
import com.nur.bindings.Education;
import com.nur.bindings.Income;
import com.nur.bindings.PlanSelection;
import com.nur.bindings.Summary;

public interface DcService {
	
	//Plan Selection screen methods
	public Map<Integer, String> getPlanNames();	
	public String savePlanSelection(PlanSelection planSelection);
	
	//Income screen method
	public String saveIncome(Income income);
	
	//Education screen methods
	public List<Integer> getGraduationYears();
	public String saveEducation(Education education);
	
	//save kids screen method
	public String saveKids(Childs childs);
	
	//summary screen method
	public Summary getSummary(Integer caseNum);

}
