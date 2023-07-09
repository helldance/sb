package com.coordsafe.core.codetable.decorators;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.entity.CodeTable;

public class CodeTableDecorator extends TableDecorator {

	public String getAction() {
		CodeTable codeTable = (CodeTable) getCurrentRowObject();

		String spaces = "&nbsp&nbsp";

		String editCodeTable = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/codetable/edit?type=" + codeTable.getType() + "&code="
				+ codeTable.getCode()
				+ "\" title=\"Edit Code\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/codetable_edit.png\"></a>";

		String deleteCodeTable = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/codetable/delete?type=" + codeTable.getType() + "&code="
				+ codeTable.getCode()
				+ "\" title=\"Delete Code\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH
				+ "/common/codetable_delete.png\"></a>";

		return editCodeTable + spaces + deleteCodeTable;
	}

}
