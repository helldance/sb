package com.coordsafe.httpgateway.messaging;

import javax.servlet.http.HttpServlet;

public class ServiceStarter extends HttpServlet implements javax.servlet.Servlet{
	private static final long serialVersionUID = 7686282313056535357L;
	
	public ServiceStarter(){
		//new Thread(new CsGwMessagingServer()).start();		
	}
}
