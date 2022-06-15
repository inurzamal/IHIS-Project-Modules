package com.nur.services;

import com.nur.bindings.Card;
import com.nur.bindings.LoginForm;
import com.nur.bindings.Profile;

public interface CaseWorkerService {
	
	public String login(LoginForm loginForm);
	public String forgotPazzword(String email);
	public Card fetchDashboardCardsData();
	//public Map<String, Integer> fetchDashboardCardData();
	public Profile fetchCwProfileInfo(String email);
	public String updateCwProfile(Profile profile);
	
}
