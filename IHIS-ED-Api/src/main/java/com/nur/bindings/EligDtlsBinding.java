package com.nur.bindings;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EligDtlsBinding {
	
	private Integer caseNum;
	private String holderName;
	private Integer holderZzn;
	private String planName;
	private String planStatus;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer benifitAmt;
	private String DenialReason;
}
