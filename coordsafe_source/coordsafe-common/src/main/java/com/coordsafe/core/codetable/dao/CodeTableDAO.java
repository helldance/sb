package com.coordsafe.core.codetable.dao;

import java.util.Date;
import java.util.List;

import com.coordsafe.core.codetable.entity.CodeTable;

/**
 * Code Table DAO interface.
 * 
 * @author Darren Mok
 * 
 */
public interface CodeTableDAO {

	/**
	 * Save an instance of CodeTable into the database.
	 * 
	 * @param codeTable
	 *            CodeTable entity.
	 */
	void save(CodeTable codeTable);

	/**
	 * Delete an instance of CodeTable from the database.
	 * 
	 * @param codeTable
	 *            CodeTable entity
	 */
	void delete(CodeTable codeTable);

	/**
	 * Update an instance of CodeTable into the database.
	 * 
	 * @param codeTable
	 *            CodeTable entity
	 */
	void update(CodeTable codeTable);

	/**
	 * Get the total count of codes specified by the type name.
	 * 
	 * @param type
	 *            Name of the type.
	 * @return The count of the total codes found.
	 */
	int getCodeTypeCount(String type);

	/**
	 * Retrieve the full list of codes from the code table.
	 * 
	 * @return List of CodeTable.
	 */
	List<CodeTable> findAll();

	/**
	 * Retrieve the list of codes from the code table specified by the type
	 * name.
	 * 
	 * @param type
	 *            Name of the type.
	 * @return List of CodeTable.
	 */
	List<CodeTable> findByType(String type);

	/**
	 * Retrieve the list of codes from the code table specified by the type name
	 * and between the start and end date.
	 * 
	 * @param type
	 *            Name of the type.
	 * @param startDate
	 *            Start date.
	 * @param endDate
	 *            End date.
	 * @return List of CodeTable.
	 */
	List<CodeTable> findByType(String type, Date startDate, Date endDate);

	/**
	 * A method to find the corresponding codes in the code table using the code
	 * type and code.
	 * 
	 * @param type
	 *            The code type.
	 * @param code
	 *            The code.
	 * @return Instance of CodeTable.
	 */
	CodeTable findByTypeCode(String type, String code);

	/**
	 * A method to find the corresponding code in the code table using id.
	 * 
	 * @param id
	 *            The id.
	 * @return Instance of CodeTable.
	 */
	CodeTable findById(Long id);
}
