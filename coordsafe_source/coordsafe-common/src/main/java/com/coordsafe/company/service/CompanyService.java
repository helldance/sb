package com.coordsafe.company.service;

import java.util.List;

import com.coordsafe.company.entity.Company;

public interface CompanyService {

	void create(Company company);

	void update(Company company) ;

	void delete(String companyName);

	Company findByName(String name);
	
	Company findById(Long id);

	List<Company> findAll();
}
