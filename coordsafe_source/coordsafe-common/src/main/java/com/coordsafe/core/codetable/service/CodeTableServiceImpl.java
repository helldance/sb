package com.coordsafe.core.codetable.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.core.codetable.dao.CodeTableDAO;
import com.coordsafe.core.codetable.entity.CodeTable;
import com.coordsafe.core.codetable.exception.CodeTableException;
import com.coordsafe.core.codetable.service.CodeTableService;

@Transactional(propagation=Propagation.REQUIRED)
@Service("codeTableService")
public class CodeTableServiceImpl implements CodeTableService {

	private CodeTableDAO codeTableDAO;
	
	@Autowired
	public void setCodeTableDAO(CodeTableDAO codeTableDAO) {
		this.codeTableDAO = codeTableDAO;
	}

	@Override
	public void save(CodeTable codeTable) throws CodeTableException {		
		if (codeTableDAO.findByTypeCode(codeTable.getType(), codeTable.getCode()) != null) {
			throw new CodeTableException("Code and Type exists!");
		} else {
			codeTable.setDeleteIndicator(false);
			codeTable.setCreatedDate(new Date());
			codeTableDAO.save(codeTable);
		}
	}

	@Override
	public void delete(String type, String code) throws CodeTableException {
		codeTableDAO.delete(codeTableDAO.findByTypeCode(type, code));
	}

	@Override
	public void update(CodeTable codeTable) throws CodeTableException {
		codeTableDAO.update(codeTable);
	}

	@Override
	public int getCodeTypeCount(String type) {
		return codeTableDAO.getCodeTypeCount(type);
	}

	@Override
	public List<CodeTable> findAll() {
		return codeTableDAO.findAll();
	}

	@Override
	public List<CodeTable> findByType(String type) {
		return codeTableDAO.findByType(type);
	}

	@Override
	public List<CodeTable> findByType(String type, Date startDate, Date endDate) {
		return codeTableDAO.findByType(type, startDate, endDate);
	}

	@Override
	public CodeTable findByTypeCode(String type, String code) {
		return codeTableDAO.findByTypeCode(type, code);
	}

	@Override
	public CodeTable findById(Long id) {
		return codeTableDAO.findById(id);
	}
}
