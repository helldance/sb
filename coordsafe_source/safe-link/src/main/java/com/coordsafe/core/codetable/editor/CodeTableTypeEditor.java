package com.coordsafe.core.codetable.editor;

import java.beans.PropertyEditorSupport;

import com.coordsafe.core.codetable.entity.CodeTable;

public class CodeTableTypeEditor extends PropertyEditorSupport {
		
	public CodeTableTypeEditor() {
		super();
	}
	
	@Override
	public String getAsText() {
		CodeTable codeTable = (CodeTable) getValue();
		
		if (codeTable == null) {
			return null;
		} else {
			return codeTable.getDescription();
		}
	}
}
