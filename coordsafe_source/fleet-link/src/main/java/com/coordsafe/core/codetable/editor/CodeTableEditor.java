package com.coordsafe.core.codetable.editor;

import java.beans.PropertyEditorSupport;
import java.util.StringTokenizer;

import com.coordsafe.core.codetable.entity.CodeTable;
import com.coordsafe.core.codetable.service.CodeTableService;

public class CodeTableEditor extends PropertyEditorSupport {

	private static final String DELIM = ",";
	
	private CodeTableService codeTableService;
	
	public CodeTableEditor(CodeTableService codeTableService) {
		super();
		this.codeTableService = codeTableService;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text.isEmpty() || text == null) {
			setValue(null);
			return;
		}
		
		StringTokenizer tokenizer = new StringTokenizer(text, DELIM);
		
		CodeTable codeTable = codeTableService.findByTypeCode(tokenizer.nextToken(), tokenizer.nextToken());
		if (codeTable != null) {
			setValue(codeTable);
		} else {
			setValue(null);
		}
	}
	
	@Override
	public String getAsText() {
		CodeTable codeTable = (CodeTable) getValue();
		
		if (codeTable == null) {
			return null;
		} else {
			return codeTable.getType() + DELIM + codeTable.getCode();
		}
	}
}
