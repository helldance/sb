/**
 * SMSClientSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.textport.www.WebServices;

public interface SMSClientSoap extends java.rmi.Remote {
    public java.lang.String ping() throws java.rmi.RemoteException;
    public java.lang.String verifyAuthentication(java.lang.String userName, java.lang.String password) throws java.rmi.RemoteException;
    public com.textport.www.WebServices.TextPortSMSResponses sendMessages(com.textport.www.WebServices.TextPortSMSMessages messagesList) throws java.rmi.RemoteException;
}
