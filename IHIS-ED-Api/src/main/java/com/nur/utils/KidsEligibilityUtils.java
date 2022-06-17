package com.nur.utils;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class KidsEligibilityUtils {
	
	public Boolean kidsAgeEligibility(List<Integer> ageList) {
		
		for(Integer age: ageList) {
			if(age<16)
				return false;
		}
		return true;
	}
}
