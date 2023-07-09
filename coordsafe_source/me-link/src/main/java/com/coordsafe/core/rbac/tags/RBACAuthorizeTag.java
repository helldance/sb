package com.coordsafe.core.rbac.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.ExpressionEvaluationUtils;

import com.coordsafe.core.rbac.entity.Permission;
import com.coordsafe.core.security.CoordsafeGrantedAuthority;
import org.slf4j.*;
@SuppressWarnings("serial")
public class RBACAuthorizeTag extends TagSupport {
	private static final Logger log = LoggerFactory.getLogger(RBACAuthorizeTag.class);
	private String resourceName = new String();

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public int doStartTag() throws JspException {
		if (resourceName == null || resourceName.isEmpty()) {
			return Tag.SKIP_BODY;
		}

		List<GrantedAuthority> authorities = getPrincipalAuthories();
		String evalResourceName = ExpressionEvaluationUtils.evaluateString(
				"resourceName", resourceName, pageContext);

		if (!checkGrantedResource(evalResourceName, authorities)) {
			return Tag.SKIP_BODY;
		}

		return Tag.EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	private boolean checkGrantedResource(String evalResourceName,
			List<GrantedAuthority> authorities) {
		Iterator<GrantedAuthority> itr = authorities.iterator();
		while (itr.hasNext()) {
			CoordsafeGrantedAuthority role = (CoordsafeGrantedAuthority) itr.next();
			List<Permission> permissions = role.getPermissons();

			for (Permission permission : permissions) {
				if (permission.getResource().getName()
						.equalsIgnoreCase(evalResourceName)) {
					return true;
				}
			}
		}
		return false;
	}

	private List<GrantedAuthority> getPrincipalAuthories() {
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();
		
		if (currentUser == null || currentUser.getAuthorities() == null
				|| currentUser.getAuthorities().isEmpty()) {
			return new ArrayList<GrantedAuthority>(0);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(
				currentUser.getAuthorities());

		return authorities;
	}
}
