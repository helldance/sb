package com.coordsafe.company.dao;

import java.util.List;

import com.coordsafe.company.entity.Company;


public interface CompanyDAO {

	void save(Company Company);

	void delete(Company Company);

	Company findByName(String name);

	Company findById(Long id);

	List<Company> findAll();

	void update(Company Company);
}
