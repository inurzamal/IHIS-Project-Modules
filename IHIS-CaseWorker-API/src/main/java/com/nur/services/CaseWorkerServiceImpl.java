package com.nur.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.bindings.Card;
import com.nur.bindings.LoginForm;
import com.nur.bindings.Profile;
import com.nur.entity.CaseWorkersAcctEntity;
import com.nur.entity.EligibilityDtlsEntity;
import com.nur.repository.AppPlanRepository;
import com.nur.repository.CaseWorkersAcctRepository;
import com.nur.repository.CitizenAppsRepository;
import com.nur.repository.EligibilityDtlsRepository;
import com.nur.utils.EmailUtils;

@Service
public class CaseWorkerServiceImpl implements CaseWorkerService {
	
	Logger logger = LoggerFactory.getLogger(CaseWorkerServiceImpl.class);
	
	@Autowired
	private CaseWorkersAcctRepository workerRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private AppPlanRepository appPlanRepo;
	
	@Autowired
	private CitizenAppsRepository citizenAppRepo;
	
	@Autowired
	private EligibilityDtlsRepository eligDtlsRepo;
	 

	@Override
	public String login(LoginForm loginForm) {
		CaseWorkersAcctEntity entity = workerRepo.findByEmailAndPazzword(loginForm.getEmail(), loginForm.getPazzword());
		
		if(entity == null) {
			return "Invalid Credentials..";
		}
		
		return "SUCCESS";
	}

	@Override
	public String forgotPazzword(String email) {
		
		CaseWorkersAcctEntity entity = workerRepo.findByEmail(email);
		
		if(entity == null) {
			return "Invalid Email Id";
		}
		
		String fileName = "RECOVER-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		String mailBody = readMailBodyContent(fileName, entity);
		String subjects = "Recover Password";
		
		String toEmail = entity.getEmail();
		
		boolean isSent = emailUtils.sendEmail(toEmail, subjects, mailBody);
		
		if(isSent) {
			return "Pazzword Sent to your registered email";
		}
		
		return "FAIL";
	}

	@Override
	public String updateCwProfile(Profile profile) {
		
		CaseWorkersAcctEntity entity = workerRepo.findById(profile.getAcctId()).get();
			
		BeanUtils.copyProperties(profile, entity);
					
		if(entity.getAcctId() == null) {
			return "Can't update without acctId";
		}		
		workerRepo.save(entity);
		return "Updated Successfully";
	}
	
	
	@Override
	public Card fetchDashboardCardsData() {
		long plansCount = appPlanRepo.count();
		
		Card card = new Card();
		card.setPlansCount(plansCount);
		
		long applicationCount = citizenAppRepo.count();
		card.setAppsCount(applicationCount);
		
		List<EligibilityDtlsEntity> findAll = eligDtlsRepo.findAll();
		
		
		int approvedSize = findAll.stream().filter(ed->ed.getPlanStatus().equals("Approved")).collect(Collectors.toList()).size();		

		int deniedSize = findAll.stream().filter(ed->ed.getPlanStatus().equals("Denied")).collect(Collectors.toList()).size();
		
		card.setApprovedCitizenCnt(approvedSize);
		
		card.setDeniedCitizenCnt(deniedSize);
				
		return card;
	}

	@Override
	public Profile fetchCwProfileInfo(String email) {
		CaseWorkersAcctEntity entity = workerRepo.findByEmail(email);
		Profile profile = new Profile();
		BeanUtils.copyProperties(entity, profile);
		return profile;
	}


	

	//Reading file for mailBody content
	private String readMailBodyContent(String fileName, CaseWorkersAcctEntity entity) {

		String mailBody = null;

		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		String line = null;

		try {

			br = new BufferedReader(new FileReader(fileName));
			line = br.readLine(); 

			while (line != null) {
				sb.append(line); 
				line = br.readLine(); 
			}

			mailBody = sb.toString();

			mailBody = mailBody.replace("{NAME}", entity.getFullName());
			mailBody = mailBody.replace("{PWD}", entity.getPazzword());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return mailBody;
	}

}
