package com.coordsafe.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.api.entity.ApiMessage;
import com.coordsafe.api.entity.ApiRequest;
import com.coordsafe.api.service.ApiRequestService;
import com.coordsafe.api.entity.ApiKey;
import com.coordsafe.api.service.ApiService;

/**
 * Servlet Filter implementation class WebServiceValidateFilter
 */
@WebFilter("/WebServiceValidateFilter")
public class WebServiceValidateFilter implements Filter {
	private static Log logger = LogFactory.getLog("WebServiceValidateFilter");
	
	@Autowired
	private ApiService apiKeyService;
	
	@Autowired
	private ApiRequestService reqSvrs;
	
	static final List<String> exemptedUri = new ArrayList<String>(
			Arrays.asList("/api/user/", "/api/guardian/login", "/api/guardian/logout", "/api/guardian/register",
					"/api/guardian"));

	/**
     * Default constructor. 
     */
    public WebServiceValidateFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// pass the request along the filter chain
		//TODO put key in request header
		//String requestKey = request.getParameter("key");

		HttpServletRequest hsr = (HttpServletRequest) request;
		HttpServletResponse hsrp = (HttpServletResponse) response;
		
		String pathUri = hsr.getRequestURI();
		String requestKey = hsr.getHeader("key");
		
		ApiRequest arReq = new ApiRequest();
		
		if (requestKey != null){
			ApiKey storedKey = apiKeyService.getByKey(requestKey);
			
			arReq.setKey(storedKey);
			arReq.setReferrer(hsr.getHeader("Referer"));
			arReq.setReqFrom(request.getRemoteAddr());
			arReq.setReqDt(new Date());
			arReq.setAgent(hsr.getHeader("User-Agent"));
			arReq.setResource(hsr.getRequestURI());
			
			logger.info("request is from: " + request.getRemoteHost() + " " + request.getRemoteAddr() + " " + hsr.getHeader("Host") + " "
					 + hsr.getHeader("Referer") + " " + hsr.getRequestURL());
				
			if (storedKey != null){
				if (!storedKey.isLoggedIn()){
					// user is not logged in, should deny access
					logger.info("user is not logged in");
					
					//return;
				}
				if (storedKey.isValid()){
					if (requestKey.equals("test-1234qwer")){
						// test key. no validation
						logger.info(ApiMessage.TEST_KEY_RCVED);
						
						storedKey.setCount(storedKey.getCount() + 1);
						storedKey.setLastReqDt(new Date());
						storedKey.setLastReqFrom(hsr.getRemoteAddr());
						
						apiKeyService.update(storedKey);
					}
					else if (requestKey.equals("master-1234qwer")){
						// test key. no validation
						logger.info(ApiMessage.MASTER_KEY_RCVED);
						
						storedKey.setCount(storedKey.getCount() + 1);
						storedKey.setLastReqDt(new Date());
						storedKey.setLastReqFrom(hsr.getRemoteAddr());
						
						apiKeyService.update(storedKey);
					}
					else {
						//if(storedKey.getDomain().contains((hsr.getHeader("Referer")))){
							logger.info(ApiMessage.KEY_VERIFIED);
							
							storedKey.setCount(storedKey.getCount() + 1);
							storedKey.setLastReqDt(new Date());
							storedKey.setLastReqFrom(hsr.getRemoteAddr());
							
							apiKeyService.update(storedKey);	
						//}
						//else {
							//logger.info(ApiMessage.KEY_UNMATCH);
							//return;
						//}
					}		
				}
				else {
					logger.info(ApiMessage.KEY_INVALID);
					
					hsrp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, ApiMessage.KEY_INVALID);
					
					return;
				}
			}
			else{
				//TODO return key not match
				logger.error(ApiMessage.KEY_NOT_EXIST);
				
				hsrp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, ApiMessage.KEY_NOT_EXIST);
				
				return;
			}
			
			arReq.setResult(String.valueOf(hsrp.getStatus()));
			
			reqSvrs.create(arReq);
		}
		else {
			boolean allNonExempted = false;
			
			for (String uri : exemptedUri){
				if (pathUri.contains(uri)){
					logger.info("bypass key for: " + pathUri);

					allNonExempted = true;
					break;
				}
			}

			if (!allNonExempted){
				logger.error("0. no request key specified");
				
				// insert unauthorised access
				reqSvrs.create(arReq);

				hsrp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, ApiMessage.KEY_NOT_EXIST);
				
				return;				
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
