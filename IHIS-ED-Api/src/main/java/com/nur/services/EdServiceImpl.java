package com.nur.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.bindings.EligDtlsBinding;
import com.nur.entity.AppPlanEntity;
import com.nur.entity.CitizenAppsEntity;
import com.nur.entity.CitizenChildDtlsEntity;
import com.nur.entity.CitizenEducationDtlsEntity;
import com.nur.entity.CitizenIncomeDtlsEntity;
import com.nur.entity.CitizenPlansEntity;
import com.nur.entity.CoTriggerEntity;
import com.nur.entity.EligibilityDtlsEntity;
import com.nur.repository.AppPlanRepository;
import com.nur.repository.CitizenAppsRepository;
import com.nur.repository.CitizenChildDtlsRepository;
import com.nur.repository.CitizenEducationDtlsRepository;
import com.nur.repository.CitizenIncomeDtlsRepository;
import com.nur.repository.CitizenPlansRepository;
import com.nur.repository.EligibilityDtlsRepository;
import com.nur.utils.KidsEligibilityUtils;

@Service
public class EdServiceImpl implements EdService{
	
	@Autowired
	private CitizenPlansRepository citizenPlanRepo;
	
	@Autowired
	private AppPlanRepository appPlanRepo;
	
	@Autowired
	private CitizenIncomeDtlsRepository incomeRepo;
	
	@Autowired
	private CitizenAppsRepository citizenAppsRepo;
	
	@Autowired
	private CitizenChildDtlsRepository childRepo;
	
	@Autowired
	private CitizenEducationDtlsRepository eduRepo;
	
	@Autowired
	private KidsEligibilityUtils kidsUtils;
	
	@Autowired
	private EligibilityDtlsRepository eligRepo;

	@Override
	public EligDtlsBinding checkEligibility(Integer caseNum) {
		
		String planStatus = "Approved";
		
		LocalDate today = LocalDate.now();
		
		//get planId
		CitizenPlansEntity citizenPlansEntity = citizenPlanRepo.findByCaseNum(caseNum);
		Integer planId = citizenPlansEntity.getPlanId();
		
		//get planNane
		Optional<AppPlanEntity> optional = appPlanRepo.findById(planId);
		AppPlanEntity appPlanEntity = optional.get();
		String planName = appPlanEntity.getPlanName();
		
		
		//getIncome
		CitizenIncomeDtlsEntity incomeEntity = incomeRepo.findByCaseNum(caseNum);
		Double salaryIncome = incomeEntity.getSalaryIncome();
		Double rentIncome = incomeEntity.getRentIncome();
		Double propertyIncome = incomeEntity.getPropertyIncome();
		Double totalIncome = salaryIncome+rentIncome+propertyIncome;

		//get citizenAge
		Optional<CitizenAppsEntity> findById = citizenAppsRepo.findById(caseNum);		
		CitizenAppsEntity citizenAppsEntity = findById.get();
		LocalDate dob = citizenAppsEntity.getDob();
		Integer citizenAge = Period.between(dob, today).getYears();
		
		
		//get kids count
		List<CitizenChildDtlsEntity> childList = childRepo.findByCaseNum(caseNum);
		Integer childCount = childList.size();
		
		//get kid age
		List<Integer> ageList = new ArrayList<>();
		childList.forEach(child->{
			LocalDate childDob = child.getChildDob();
			Integer childAge = Period.between(childDob, today).getYears();
			ageList.add(childAge);
		});
		
		//get citigenEducation details
		CitizenEducationDtlsEntity educationDtlsEntity = eduRepo.findByCaseNum(caseNum);
		Integer graduationYearId = educationDtlsEntity.getGraduationYearId();
		
		Boolean isKidEligible = kidsUtils.kidsAgeEligibility(ageList);
				
		EligDtlsBinding binding = new EligDtlsBinding();
		
		binding.setCaseNum(caseNum);
		binding.setPlanName(planName);
		binding.setHolderName(citizenAppsEntity.getFullName());
		binding.setHolderZzn(citizenAppsEntity.getZzn());
		
		if("SNAP".equals(planName) && totalIncome > 300) {
			planStatus = "Denied";
			binding.setDenialReason("Income greater than 300");
		}
		
		else if("CCAP".equals(planName) && totalIncome>300) {
			planStatus = "Denied";
			binding.setDenialReason("Income greater than 300");
		}
		
		else if("CCAP".equals(planName) && totalIncome<300) {
			if(childCount==0 || isKidEligible) {
				planStatus = "Denied";
				binding.setDenialReason("Kid not available with age less than 16");
			}			
		}
		
		else if("Medicaid".equals(planName) && totalIncome > 300  ) {
			if(propertyIncome>0) {
				planStatus = "Denied";
				binding.setDenialReason("Property Income is not 0");
			}
			planStatus = "Denied";
			binding.setDenialReason("Income greater than 300");
		}
		
		else if("Medicare".equals(planName) && citizenAge<65) {
			planStatus = "Denied";
			binding.setDenialReason("Citizen age less than 65");
		}
		
		else if("NJW".equals(planName) && graduationYearId == null) {
			planStatus = "Denied";
			binding.setDenialReason("Not Applicable for under-graduate");
		}
		
		binding.setPlanStatus(planStatus);
		
		if("Approved".equals(planStatus)) {
			binding.setStartDate(today);
			binding.setEndDate(today.plusMonths(10));
			binding.setBenifitAmt(150);
			binding.setDenialReason("NA");
		}else {
			binding.setStartDate(null);
			binding.setEndDate(null);
		}
		
		saveEligibilityDtls(binding);
		saveIntoCoTrigers(binding);
		
		return binding;
	}
	
	private String saveEligibilityDtls(EligDtlsBinding binding) {	
		EligibilityDtlsEntity entity = new EligibilityDtlsEntity();
		BeanUtils.copyProperties(binding, entity);
		eligRepo.save(entity);	
		return "Saved into db";	
	}
	
	private String saveIntoCoTrigers(EligDtlsBinding binding) {
		CoTriggerEntity triggerEntity = new CoTriggerEntity();
		triggerEntity.setCaseNum(binding.getCaseNum());
		triggerEntity.setTrgStatus("Pending");
		triggerEntity.setNotice(null);
		return "Saved into Co-Triggers table";
		
	}

}
