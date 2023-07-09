package com.textport.www.WebServices;

public class SMSClientSoapProxy implements com.textport.www.WebServices.SMSClientSoap {
  private String _endpoint = null;
  private com.textport.www.WebServices.SMSClientSoap sMSClientSoap = null;
  
  public SMSClientSoapProxy() {
    _initSMSClientSoapProxy();
  }
  
  public SMSClientSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSMSClientSoapProxy();
  }
  
  private void _initSMSClientSoapProxy() {
    try {
      sMSClientSoap = (new com.textport.www.WebServices.SMSClientLocator()).getSMSClientSoap();
      if (sMSClientSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sMSClientSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sMSClientSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sMSClientSoap != null)
      ((javax.xml.rpc.Stub)sMSClientSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.textport.www.WebServices.SMSClientSoap getSMSClientSoap() {
    if (sMSClientSoap == null)
      _initSMSClientSoapProxy();
    return sMSClientSoap;
  }
  
  public java.lang.String ping() throws java.rmi.RemoteException{
    if (sMSClientSoap == null)
      _initSMSClientSoapProxy();
    return sMSClientSoap.ping();
  }
  
  public java.lang.String verifyAuthentication(java.lang.String userName, java.lang.String password) throws java.rmi.RemoteException{
    if (sMSClientSoap == null)
      _initSMSClientSoapProxy();
    return sMSClientSoap.verifyAuthentication(userName, password);
  }
  
  public com.textport.www.WebServices.TextPortSMSResponses sendMessages(com.textport.www.WebServices.TextPortSMSMessages messagesList) throws java.rmi.RemoteException{
    if (sMSClientSoap == null)
      _initSMSClientSoapProxy();
    return sMSClientSoap.sendMessages(messagesList);
  }
  
  
}