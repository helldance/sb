package com.coordsafe.core.rbac.decorators;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.service.CodeTableService;
import com.coordsafe.core.rbac.entity.Resource;

public class ResourceSearchTableDecorator extends TableDecorator {
	
	private CodeTableService codeTableService;
	
	public ResourceSearchTableDecorator(CodeTableService codeTableService) {
		super();
		this.codeTableService = codeTableService;
	}

	public String getType() {
		Resource resource = (Resource) getCurrentRowObject();
		
		if (resource.getType() == null || resource.getType().isEmpty())
			return "";
		else
			return codeTableService.findByTypeCode("RESOURCE_TYPE", resource.getType()).getDescription();
	}

	public String getAction() {
		Resource resource = (Resource) getCurrentRowObject();

		String spaces = "&nbsp&nbsp";

		String editResource = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/resource/edit?name=" + resource.getName()
				+ "\" title=\"Edit Resource\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH + "/common/resource_edit.png\"></a>";

		String deleteResource = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/resource/delete?name=" + resource.getName()
				+ "\" title=\"Delete Resource\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH + "/common/resource_delete.png\"></a>";

		return editResource + spaces + deleteResource;
	}
}
