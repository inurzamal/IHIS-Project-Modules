package com.nur.services;

import java.util.List;

import com.nur.bindings.AppPlanBinding;

public interface AppPlanService {
	
	List<String> getPlanCategory();
	
	String upsert(AppPlanBinding appPlan);
	
	List<AppPlanBinding> getAllPlans();
	
	AppPlanBinding getPlan(int pid);
	
	String delete(int pid);
	
	String softDelete(int pid);
	
}
