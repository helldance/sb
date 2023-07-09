package com.coordsafe.httpgateway.messaging;

public class FailedEntity {
	private String recipientId, msg, timestamp;
	private String key;
	private int retry;
	
	public FailedEntity (String k, String r, String m, String t, int i){
		this.recipientId = r;
		this.msg = m;
		this.timestamp = t;
		this.key = k;
		this.retry = i;
	}
	
	public void setKey (String k){
		this.key = k;
	}
	
	public String getKey (){
		return this.key;
	}
	
	public void setRecipient (String r){
		this.recipientId = r;
	}
	
	public String getRecipient (){
		return this.recipientId;
	}
	
	public void setMessage (String m){
		this.msg = m;
	}
	
	public String getMessage (){
		return this.msg;
	}
	
	public void setTime (String t){
		this.timestamp = t;
	}
	
	public String getTime (){
		return this.timestamp;
	}	
	
	public void setRetry (int i){
		this.retry = i;
	}
	
	public int getRetry(){
		return this.retry;
	}	
}
