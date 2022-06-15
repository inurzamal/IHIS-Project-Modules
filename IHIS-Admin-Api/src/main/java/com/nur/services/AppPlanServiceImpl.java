package com.nur.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.bindings.AppPlanBinding;
import com.nur.entity.AppPlanEntity;
import com.nur.repository.AppPlanRepository;
import com.nur.repository.PlanCategoryRepository;

@Service
public class AppPlanServiceImpl implements AppPlanService {
	
	@Autowired
	private AppPlanRepository repository;
	
	@Autowired
	private PlanCategoryRepository categoryRepo;
	
	@Override
	public List<String> getPlanCategory() {		
		return categoryRepo.getPlanCategoryName();
	}

	@Override
	public String upsert(AppPlanBinding appPlan) {		
		AppPlanEntity entity = new AppPlanEntity();
		BeanUtils.copyProperties(appPlan, entity);
		repository.save(entity);
		return "SUCCESS";
	}

	@Override
	public List<AppPlanBinding> getAllPlans() {	
		List<AppPlanEntity> findAll = repository.findAll();	
		List<AppPlanBinding> bindingList = new ArrayList<>();
				
		for(AppPlanEntity e: findAll) {
			AppPlanBinding b = new AppPlanBinding();
			b.setPlanId(e.getPlanId());
			b.setPlanName(e.getPlanName());
			b.setPlanStDate(e.getPlanStDate());
			b.setPlanEnDate(e.getPlanEnDate());
			b.setCategoryId(e.getCategoryId());
			
			bindingList.add(b);
		}
			
		return bindingList;
	}
	
	@Override
	public AppPlanBinding getPlan(int pid) {
			
		Optional<AppPlanEntity> findById = repository.findById(pid);		
		if(findById.isPresent()) {		
			AppPlanEntity appPlanEntity = findById.get();
			
			AppPlanBinding appPlanBinding = new AppPlanBinding();
			appPlanBinding.setPlanId(appPlanEntity.getPlanId());			
			appPlanBinding.setPlanName(appPlanEntity.getPlanName());
			appPlanBinding.setPlanStDate(appPlanEntity.getPlanStDate());
			appPlanBinding.setPlanEnDate(appPlanEntity.getPlanEnDate());
			appPlanBinding.setCategoryId(appPlanEntity.getCategoryId());
			
			return appPlanBinding;
		}
		return null;
	}


	@Override
	public String delete(int pid) {
		repository.deleteById(pid);
		return "Deleted Successfully";
	}

	@Override
	public String softDelete(int pid) {
		Optional<AppPlanEntity> findById = repository.findById(pid);
		if(findById.isPresent()) {
			AppPlanEntity appPlanEntity = findById.get();
			appPlanEntity.setActiveSw("N");			
		}
		return "Deactivated..";
	}

}
