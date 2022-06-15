package com.nur.services;

import java.util.List;

import com.nur.bindings.CwAccBinding;

public interface CwAccountService {
	
	String createCwAccount(CwAccBinding accBinding);
	
	List<CwAccBinding> getAllAccount();
	
	CwAccBinding getAccount(int id);
	
	String deleteAccount(int id);
	
	String deleteSoft(int id);
	
}
