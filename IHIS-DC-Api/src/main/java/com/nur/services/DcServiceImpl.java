package com.nur.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.bindings.Child;
import com.nur.bindings.Childs;
import com.nur.bindings.Education;
import com.nur.bindings.Income;
import com.nur.bindings.PlanSelection;
import com.nur.bindings.Summary;
import com.nur.entity.AppPlanEntity;
import com.nur.entity.CitizenChildDtlsEntity;
import com.nur.entity.CitizenEducationDtlsEntity;
import com.nur.entity.CitizenIncomeDtlsEntity;
import com.nur.entity.CitizenPlansEntity;
import com.nur.repository.AppPlanRepository;
import com.nur.repository.CitizenChildDtlsRepository;
import com.nur.repository.CitizenEducationDtlsRepository;
import com.nur.repository.CitizenIncomeDtlsRepository;
import com.nur.repository.CitizenPlansRepository;
import com.nur.repository.GraduationYearsRepository;

@Service
public class DcServiceImpl implements DcService {
	
	@Autowired
	private AppPlanRepository appPlansRepo;
	
	@Autowired
	private CitizenPlansRepository citizenPlanRepo;
	
	@Autowired
	private CitizenIncomeDtlsRepository incomeRepo;
	
	@Autowired
	private CitizenEducationDtlsRepository eduRepo;
	
	@Autowired
	private CitizenChildDtlsRepository childRepo;
	
	@Autowired
	private GraduationYearsRepository yearsRepo;
	

	@Override
	public Map<Integer, String> getPlanNames() {
		List<AppPlanEntity> list = appPlansRepo.findAll();
		
		Map<Integer, String> plansMap = new HashMap<>();
		
		list.forEach(plan->{
			plansMap.put(plan.getPlanId(), plan.getPlanName());
		});
		return plansMap;
	}

	@Override
	public String savePlanSelection(PlanSelection planSelection) {
		CitizenPlansEntity entity = new CitizenPlansEntity();
		BeanUtils.copyProperties(planSelection, entity);
		citizenPlanRepo.save(entity);
		return "Plan selection saved";
	}

	@Override
	public String saveIncome(Income income) {
		CitizenIncomeDtlsEntity entity = new CitizenIncomeDtlsEntity();
		BeanUtils.copyProperties(income, entity);
		incomeRepo.save(entity);
		return "Income data saved";
	}

	@Override
	public List<Integer> getGraduationYears() {	
		
		return yearsRepo.graduationYears();
	}

	@Override
	public String saveEducation(Education education) {
		CitizenEducationDtlsEntity entity = new CitizenEducationDtlsEntity();
		BeanUtils.copyProperties(education, entity);
		eduRepo.save(entity);
		return "Education details saved";
	}

	@Override
	public String saveKids(Childs childs) {
		
		List<Child> childList = childs.getChilds();
		
		childList.forEach(child->{
			CitizenChildDtlsEntity entity = new CitizenChildDtlsEntity();
			BeanUtils.copyProperties(child, entity);
			childRepo.save(entity);
		});
		
		return "Child Info saved";
	}

	@Override
	public Summary getSummary(Integer caseNum) {
			
		Summary summary = new Summary();
		
		//get PlanSelection based on caseNum & set to summary		
		CitizenPlansEntity citizenPlanEntity = citizenPlanRepo.findByCaseNum(caseNum);
		PlanSelection binding = new PlanSelection();
		BeanUtils.copyProperties(citizenPlanEntity, binding);
		summary.setPlanSelection(binding);
					
		// get Income data based on caseNum & set to summary		
		CitizenIncomeDtlsEntity incomeEntity = incomeRepo.findByCaseNum(caseNum);		
		Income incomeBinding = new Income();	
		BeanUtils.copyProperties(incomeEntity, incomeBinding);
		summary.setIncome(incomeBinding);
				
		// get Education data based on caseNum & set to summary
		CitizenEducationDtlsEntity educationEntity = eduRepo.findByCaseNum(caseNum);		
		Education educationBinding = new Education();		
		BeanUtils.copyProperties(educationEntity, educationBinding);	
		summary.setEducation(educationBinding);
		
		// get Child data based on caseNum & set to summary
		List<CitizenChildDtlsEntity> childsEntity = childRepo.findByCaseNum(caseNum);
		
		List<Child> childList = new ArrayList<>();
		
		childsEntity.forEach(entity->{
			Child childBinding = new Child();
			BeanUtils.copyProperties(entity, childBinding);
			childList.add(childBinding);
		});
		
		Childs childs = new Childs();
		childs.setCaseNum(caseNum);
		childs.setChilds(childList);
		
		summary.setKids(childs);
		
		return summary;
	}
	
}
