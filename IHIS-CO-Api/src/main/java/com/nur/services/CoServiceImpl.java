package com.nur.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.entity.CoTriggerEntity;
import com.nur.repository.CoTriggerRepository;

@Service
public class CoServiceImpl implements CoService {
	
	@Autowired
	private CoTriggerRepository coRepo;

	@Override
	public Map<String, Integer> generateNotices() {
		
		List<CoTriggerEntity> pendingTrgs = coRepo.findByTrgStatus("Pending");
		
	
		return null;
	}

}
