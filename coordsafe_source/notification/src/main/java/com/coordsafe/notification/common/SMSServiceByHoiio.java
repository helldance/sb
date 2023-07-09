package com.coordsafe.notification.common;

import java.util.Iterator;
import java.util.List;


import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.notification.entity.EventMessage;
import com.coordsafe.notification.entity.NotificationSetting;
import com.hoiio.sdk.*;
import com.hoiio.sdk.exception.HoiioException;
import com.hoiio.sdk.objects.sms.BulkSmsTxn;
import com.hoiio.sdk.objects.sms.SmsTxn;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;

public class SMSServiceByHoiio {
	private static final Logger log = LoggerFactory.getLogger(SMSServiceByHoiio.class);
	private Hoiio hoiio;
/*	public static void main(String args[]){
		
		SMSServiceByHoiio service = new SMSServiceByHoiio();
		
		EventMessage msg = null;
		//String status = service.sent(msg);
		//log.info(status);
	}*/
	
	public String sent(List<String>dests, String msg ){
		 // Create hoiio service
	   // Hoiio hoiio = new Hoiio("GUO1a9qjFbuEPwv0", "lxJVT1RIweHWKzpW");
		log.info("In the SMS by Hoiio Service ...");
			
	    try {
	        // Make a call
	        //CallTxn callTxn = hoiio.getVoiceService().makeCall(dest1, dest2);   

	        // Get the response from Hoiio
	        //System.out.println(callTxn.getTxnRef());

	        
			// Send an sms
    		 //hoiio.getSmsService().send("+6597256276", msg);

	    	for(Iterator<String> it = dests.iterator();it.hasNext();){
	    		String dest = it.next();
	    		log.info("The mobile dest="  + dest + " The msg ==" + msg );
	    		
	    		//msg = msg.substring(0, msg.indexOf("Alarm")+5);
	    		
	    		SmsTxn smsTxn = hoiio.getSmsService().send("+65" + dest, msg);
	    		log.info(smsTxn.getTxnRef());
	    	}
	    	
	    	//error_not_allowed_for_trial
	        //BulkSmsTxn smsTxn = hoiio.getSmsService().sendBulk(dests, msg);//.send(dest, message);      

	        // Get the response from Hoiio
	       // log.info(smsTxn.getTxnRef());
	        
	        
	        
	        // Get sms history
/*	        SmsHistory smsHistory = hoiio.getSmsService().fetchHistory();
	        for (Sms sms : smsHistory.getSmsList()) {
	            System.out.println(sms.getContent());
	            System.out.println(sms.getDest());
	            System.out.println(sms.getSmsStatus().toString());
	            System.out.println(sms.getTag());
	            System.out.println(sms.getTxnRef());
	            System.out.println(sms.getDebit());
	            System.out.println(sms.getDate());
	            System.out.println(sms.getRate());
	            System.out.println(sms.getSplitCount());
	            System.out.println(sms.getCurrency().toString());
	        }*/
	        
	        //return smsTxn.getTxnRef();
	    	return "SMS success";
	    } catch (HoiioException e) {
	        // This is thrown when the request doesn't return success_ok
	      e.printStackTrace();
	    }
	    
	    return "SMS Failed";
		
	}

	public Hoiio getHoiio() {
		return hoiio;
	}

	public void setHoiio(Hoiio hoiio) {
		this.hoiio = hoiio;
	}
	


}
