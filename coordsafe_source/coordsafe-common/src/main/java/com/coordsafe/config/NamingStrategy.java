package com.coordsafe.config;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * This class define the naming strategy to use in the database .
 * 
 * @author Darren Mok
 * @version 2.0
 *
 */
@SuppressWarnings("serial")
public class NamingStrategy extends ImprovedNamingStrategy {

	@Override
	public String classToTableName(String className) {
		String s = "TBL_" + tableName(className);
		return s.toUpperCase();
	}

	@Override
	public String propertyToColumnName(String propertyName) {
		String s = columnName(propertyName);
		return s.toUpperCase();
	}

	@Override
	public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
		String s = "TBL_";
		s += tableName(ownerEntityTable) + "_";
		s += tableName(associatedEntityTable);
		return s.toUpperCase();
	}

	@Override
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		String s = tableName(joinedTable) + "_" + columnName(joinedColumn);
		return s.toUpperCase();
	}
	
	@Override
	public String foreignKeyColumnName(String propertyName,
			String propertyEntityName, String propertyTableName,
			String referencedColumnName) {
		String s = tableName(propertyTableName) + "_" + columnName(referencedColumnName);
		return s.toUpperCase();
	}
	
	
}
