package com.coordsafe.core.codetable.service;

import java.util.Date;
import java.util.List;

import com.coordsafe.core.codetable.entity.CodeTable;
import com.coordsafe.core.codetable.exception.CodeTableException;

public interface CodeTableService {

	void save(CodeTable codeTable) throws CodeTableException;

	void delete(String type, String code) throws CodeTableException;

	void update(CodeTable codeTable) throws CodeTableException;

	int getCodeTypeCount(String type);

	List<CodeTable> findAll();

	List<CodeTable> findByType(String type);

	List<CodeTable> findByType(String type, Date startDate, Date endDate);

	CodeTable findByTypeCode(String type, String code);

	CodeTable findById(Long id);
}
