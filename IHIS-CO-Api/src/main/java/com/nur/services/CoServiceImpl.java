package com.nur.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;

import com.nur.entity.CitizenAppsEntity;
import com.nur.entity.CoTriggerEntity;
import com.nur.entity.EligibilityDtlsEntity;
import com.nur.repository.CitizenAppsRepository;
import com.nur.repository.CoTriggerRepository;
import com.nur.repository.EligibilityDtlsRepository;
import com.nur.utils.EmailUtils;

@Service
public class CoServiceImpl implements CoService {
	
	private static final String PDF_PATH="C:\\Users\\NIELITCC13\\Documents\\pdfs\\";

	@Autowired
	private CoTriggerRepository triggersRepo;

	@Autowired
	private EligibilityDtlsRepository eligRepo;
	
	@Autowired
	private CitizenAppsRepository citizenAppRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public Map<String, Integer> generateNotices() {
		
		Map<String, Integer> statusMap = new HashMap<>();
		Integer success = 0;
		Integer failure = 0;
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		ExecutorCompletionService<Object> pool = new ExecutorCompletionService<>(executorService);
		
		List<CoTriggerEntity> pendingTrgs = triggersRepo.findByTrgStatus("Pending");

		for (CoTriggerEntity triggersEntity : pendingTrgs) {
			
			pool.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					processTrigger(triggersEntity);
					return null;
				}
			});
		}
		
		statusMap.put("Total Pending Triggers", pendingTrgs.size());
		statusMap.put("SUCCESS_COUNT", success);
		statusMap.put("FAILURE_COUNT", failure);
		
		return statusMap;
	}

	private void processTrigger(CoTriggerEntity triggersEntity) throws IOException{

		//get Eligibility data and Citizen data
		EligibilityDtlsEntity eligData = eligRepo.findByCaseNum(triggersEntity.getCaseNum());
		CitizenAppsEntity citizenApps = citizenAppRepo.findById(triggersEntity.getCaseNum()).get();

		//generate pdf
		generatePdf(eligData, citizenApps);

		//send pdf to email
		//sendPdftoEmail(citizenApps);

		//store pdf in db & update trigger as completed		
		byte[] fileData = new byte[1024];
		FileInputStream fis = new FileInputStream(new File(PDF_PATH+triggersEntity.getCaseNum()+".pdf"));
		fis.read(fileData);
		fis.close();
		
		triggersEntity.setNotice(fileData);
		triggersEntity.setTrgStatus("Completed");
		triggersRepo.save(triggersEntity);
	}

	private void generatePdf(EligibilityDtlsEntity eligData, CitizenAppsEntity citizenApps) throws FileNotFoundException  {

		Document document = new Document();
		
		FileOutputStream fos = new FileOutputStream(new File(PDF_PATH+eligData.getCaseNum()+".pdf"));

		PdfWriter writer = PdfWriter.getInstance(document, fos);

		document.open();

		Font font = new Font(Font.HELVETICA, 16, Font.BOLDITALIC, Color.RED);
		Paragraph para = new Paragraph("Eligibility Details", font);

		document.add(para);

		PdfPTable table = new PdfPTable(2);

		table.addCell("Holder Name");
		table.addCell(citizenApps.getFullName());
			
		table.addCell("Holder SSN");
		table.addCell(String.valueOf(citizenApps.getZzn()));
		
		table.addCell("Plan Name");
		table.addCell(eligData.getPlanName());	
		
		table.addCell("Plan Status");
		table.addCell(eligData.getPlanStatus());
		
		table.addCell("Start Date");
		if(eligData.getStartDate() != null) {
			table.addCell(String.valueOf(eligData.getStartDate()));
		}else {
			table.addCell("NA");
		}
		
		table.addCell("End Date");
		if(eligData.getEndDate() != null) {
			table.addCell(String.valueOf(eligData.getEndDate()));
		}else {
			table.addCell("NA");
		}
				
		table.addCell("Benefit Amount");
		if(eligData.getBenifitAmt() != null) {
			table.addCell(String.valueOf(eligData.getBenifitAmt()));
		}else {
			table.addCell("NA");
		}
				
		table.addCell("Denial Reason");
		if(eligData.getDenialReason() != null) {
			table.addCell(eligData.getDenialReason());
		}else {
			table.addCell("NA");
		}
		

		document.add(table);
		
		document.close();
		writer.close();
	}
	

	private void sendPdftoEmail(CitizenAppsEntity app) {	
		String subject = "Your Eligibility Information: IHIS";
		String body = "Please the attachment of your eligibility details";	
		emailUtils.sendEmail(app.getEmail(), subject, body, new File(PDF_PATH+app.getCaseNum()+".pdf"));	
	}

}
