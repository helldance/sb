package com.coordsafe.guardian.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.security.GuardianUser;
import com.coordsafe.guardian.security.GuardianUserGrantedAuthority;

@Service("guardianUserLoginService")
public class GuardianUserLoginService implements UserDetailsService
{
	private static final Log log = LogFactory.getLog(GuardianUserLoginService.class);
	
    @Resource(name="guardianService")
    private GuardianService guardianService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;
    
    @Autowired
	private SaltSource saltSource;

    private Map<String,String> roles = new HashMap<String,String>();

    public GuardianUserLoginService()
    {
        roles.put("ROLE_USER","Customer");
        roles.put("ROLE_ADMIN","Admin");
    }

    public Map<String,String> getRoles()
    {
        return roles;
    }
    public String getPasswordEncoded(String passwd,GuardianUser guardian){
    	return passwordEncoder.encodePassword(passwd, saltSource.getSalt(guardian));
    }
    public UserDetails loadUserByUsername(String username)
    {
        if (username != null && !username.equals(""))
        {
            //Guardian guardian = guardianService.get(username);
        	Guardian guardian = guardianService.getByNameOrEmail(username);
        	
        	log.info("retrieved guardian " + guardian);
        	
            if (guardian == null) {
                return null;
            }
                        
            GrantedAuthority grantedAuth = new GuardianUserGrantedAuthority(guardian.getRole());
            return new GuardianUser(guardian.getId(), 
            		guardian.getPasswd(),guardian.getLogin(),guardian.isAccountNonExpired(),
            		guardian.isAccountNonLocked(),guardian.isEnabled(), 
            		new GrantedAuthority[]{ grantedAuth });
        } else {
            return null;
        }
    }
}
