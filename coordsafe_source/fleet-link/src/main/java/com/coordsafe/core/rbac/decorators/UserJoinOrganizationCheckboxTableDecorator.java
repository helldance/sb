package com.coordsafe.core.rbac.decorators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ObjectUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.User;


/**
 * A table decorator which adds checkboxes for selectable rows.
 * @author Fabrizio Giustina
 * @version $Id: CheckboxTableDecorator.java 1134 2008-12-27 10:16:33Z fgiust $
 */
public class UserJoinOrganizationCheckboxTableDecorator extends TableDecorator
{

    private String id = "id";

    private Map params;

    private List checkedIds;

    private String fieldName = "_chk";

    /**
     * Setter for <code>id</code>.
     * @param id The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Setter for <code>fieldName</code>.
     * @param fieldName The fieldName to set.
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * @see org.displaytag.decorator.Decorator#init(javax.servlet.jsp.PageContext, java.lang.Object,
     * org.displaytag.model.TableModel)
     * 
     * Edited by Darren Mok @ STEE Info-Comm Systems.
     */
    public void init(PageContext pageContext, Object decorated, TableModel tableModel)
    {
        super.init(pageContext, decorated, tableModel);
        User user = (User) pageContext.getRequest().getAttribute(fieldName);
        if (user == null) {
        	checkedIds = new ArrayList(0);
        } else {
        	checkedIds = new ArrayList();
        	for (Organization organization : user.getOrganizations()) {
        		checkedIds.add(organization.getName());
        	}
        }

    }

    /**
     * @see org.displaytag.decorator.TableDecorator#finish()
     */
    public void finish()
    {

        if (!checkedIds.isEmpty())
        {
            JspWriter writer = getPageContext().getOut();
            for (Iterator it = checkedIds.iterator(); it.hasNext();)
            {
                String name = (String) it.next();
                StringBuffer buffer = new StringBuffer();
                buffer.append("<input type=\"hidden\" name=\"");
                buffer.append(fieldName);
                buffer.append("\" value=\"");
                buffer.append(name);
                buffer.append("\">");
                try
                {
                    writer.write(buffer.toString());
                }
                catch (IOException e)
                {
                    // should never happen
                }
            }
        }

        super.finish();

    }

    public String getCheckbox()
    {

        String evaluatedId = ObjectUtils.toString(evaluate(id));

        boolean checked = checkedIds.contains(evaluatedId);

        StringBuffer buffer = new StringBuffer();
        buffer.append("<input type=\"checkbox\" name=\"_chk\" value=\"");
        buffer.append(evaluatedId);
        buffer.append("\"");
        if (checked)
        {
            checkedIds.remove(evaluatedId);
            buffer.append(" checked=\"checked\"");
        }
        buffer.append("/>");

        return buffer.toString();
    }

}