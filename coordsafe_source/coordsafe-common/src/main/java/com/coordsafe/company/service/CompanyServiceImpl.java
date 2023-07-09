package com.coordsafe.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.company.dao.CompanyDAO;
import com.coordsafe.company.entity.Company;

import org.slf4j.*;
@Transactional(propagation = Propagation.REQUIRED)
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	
	private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
	@Autowired
	private CompanyDAO companyDAO;


	@Override
	public void create(Company company) {
		log.info(company.getName());
		if (companyDAO.findByName(company.getName()) != null) {
			try {
				throw new Exception("Company "
						+ company.getName() + " already exists.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			companyDAO.save(company);
		}
	}

	@Override
	public void update(Company company)  {
		companyDAO.update(company);
	}

	@Override
	public void delete(String companyName)  {
		if (companyDAO.findByName(companyName) == null) {
			try {
				throw new Exception("Company " + companyName
						+ " does not exists.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			companyDAO.delete(companyDAO.findByName(companyName));
		}
	}

	@Override
	public Company findByName(String name) {
		return companyDAO.findByName(name);
	}
	
	@Override
	public Company findById(Long id) {
		return companyDAO.findById(id);
	}

	@Override
	public List<Company> findAll() {
		return companyDAO.findAll();
	}

}
