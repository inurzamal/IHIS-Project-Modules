package com.nur.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nur.bindings.CitizenAppRegBinding;
import com.nur.entity.CitizenAppsEntity;
import com.nur.repository.CitizenAppsRepository;

@Service
public class ApplicationRegServiceImpl implements ApplicationRegService {
	
	@Autowired
	private CitizenAppsRepository appRepo;

	@Override
	public String createCitizenApp(CitizenAppRegBinding binding) {
		String ssaWebUrl = "https://ssa-web-api.herokuapp.com/ssn/{ssn}";
		RestTemplate rt =new RestTemplate();
		ResponseEntity<String> resEntity = rt.getForEntity(ssaWebUrl, String.class, binding.getZzn());
		String stateName = resEntity.getBody();
		
		if("New Jersey".equals(stateName)) {
			
			binding.setStateName(stateName);	
			CitizenAppsEntity entity = new CitizenAppsEntity();		
			BeanUtils.copyProperties(binding, entity);	
			appRepo.save(entity);
			return "Citizen Application Created";
		}
		
		return "Citizen not belongs to New Jersy";

	}

	@Override
	public List<CitizenAppRegBinding> viewCitizenApplications() {
		List<CitizenAppsEntity> entities = appRepo.findAll();
		
		List<CitizenAppRegBinding> list = new ArrayList<>();
		
//		for(CitizenAppsEntity e: entities) {
//			CitizenAppRegBinding binding = new CitizenAppRegBinding();
//			BeanUtils.copyProperties(e, binding);
//			list.add(binding);
//		}
		
		entities.forEach(e->{
			CitizenAppRegBinding binding = new CitizenAppRegBinding();
			BeanUtils.copyProperties(e, binding);
			list.add(binding);
		});
		
		return list;
	}
}
