package com.nur.services;

import java.util.List;

import com.nur.bindings.CitizenAppRegBinding;

public interface ApplicationRegService {
	
	public String createCitizenApp(CitizenAppRegBinding binding);
	
	public List<CitizenAppRegBinding> viewCitizenApplications();

}
