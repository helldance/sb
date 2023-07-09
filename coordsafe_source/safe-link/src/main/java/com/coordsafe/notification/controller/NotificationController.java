package com.coordsafe.notification.controller;

import java.util.ArrayList;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


import org.slf4j.*;

import com.coordsafe.company.entity.Company;
import com.coordsafe.company.service.CompanyService;
import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.notification.entity.NotificationSetting;
import com.coordsafe.notification.entity.VehicleNotificationSetting;
import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.notification.form.NotificationSettingsForm;
import com.coordsafe.notification.producer.TopicMessageProducer;
import com.coordsafe.notification.service.NotificationSettingService;
import com.coordsafe.notification.service.VehicleNotificationSettingService;
import com.coordsafe.notification.service.WardNotificationSettingService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.service.VehicleService;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.service.WardService;



@Controller
@SessionAttributes("ward")
@RequestMapping(value="/notification")
public class NotificationController {
	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
	@Autowired
	private CompanyService companyService;
	@Autowired
	private NotificationSettingService notificationSettingService;
	@Autowired
	private VehicleNotificationSettingService vehiclenotificationSettingService;
	@Autowired	
	private MessageSource messageSource;
	@Autowired
	private TopicMessageProducer topicNotificationMessageProducer;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private WardService wardService;
	@Autowired
	private WardNotificationSettingService wardNotificationSettingService;
	public enum EventTypeSelf {
		//WARD_IN_DANGEROUS_ZONE,WARD_OUT_SAFE_ZONE,WARD_SEND_ALARM,WARD_OUT_SAFE_DISTANCE
		Dangerous_Zone, Safe_Zone, Panic_Alarm, Mobile_Safe_Zone
	}
	
	@ModelAttribute("eventtypes")
	public EventTypeSelf[] getEventType(){
		return EventTypeSelf.values();
	}
	

	@RequestMapping(value="/set", method = RequestMethod.GET)
	public String vieSetting(@RequestParam(value = "companyid", required = true) String companyid,Model model){
		
		Company company = companyService.findById(new Long(companyid));
		
		List<NotificationSetting> notificationSettings = notificationSettingService.findByCompanyName(company.getName());
		List<Vehicle> vehicles = vehicleService.findVehiclesByCompany(companyid);
		for(Vehicle vehicle:vehicles)
			log.info("The company " + company.getName() + " has vehicle as " + vehicle.getName());
		NotificationSettingsForm nsForm = new NotificationSettingsForm();
		nsForm.setNotificationSettings(notificationSettings);

		List<NotificationSetting> eventTypeList = new ArrayList<NotificationSetting>();
		for(EventTypeSelf e:EventTypeSelf.values()){
			NotificationSetting ns = new NotificationSetting();
			ns.setEventType(e.toString());
			ns.setCompany(company);
			eventTypeList.add(ns);
		}
		nsForm.setEventTypes(eventTypeList);
		
		
		model.addAttribute("nsForm", nsForm);
		
		
		return "notification/set";
	}
	
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("nsForm") NotificationSettingsForm nsForm) {
    	log.info("In the seting post method...");
        List<NotificationSetting> notificationSettings = nsForm.getNotificationSettings();
        List<NotificationSetting> eventTypes = nsForm.getEventTypes();
         
        if(null != notificationSettings && notificationSettings.size() > 0) {
            
            for (NotificationSetting notificationSetting : notificationSettings) {
                log.info(notificationSetting.getEventType() 
                    	+ "******" +notificationSetting.isEmailEnable()
    		            + "******" +notificationSetting.isSmsEnable()
    		            + "******" +notificationSetting.getEmailaddress());
                notificationSettingService.update(notificationSetting);
                
            }
        }else{
            for (NotificationSetting notificationSetting : eventTypes) {
                log.info(notificationSetting.getEventType() 
                	+ "======" +notificationSetting.isEmailEnable()
		            + "======" +notificationSetting.isSmsEnable()
		            + "======" +notificationSetting.getEmailaddress());
                notificationSettingService.create(notificationSetting);
                
            }
        }
         
        return new ModelAndView("notification/set", "nsForm", nsForm);
    }
	
	@ModelAttribute("companys")
	@RequestMapping(value="/search")
	public List<Company> populateNotifications() {
		return companyService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/run")
	public String runTestNotification(@RequestParam(value = "companyid", required = true) String companyid, Model model) {
		log.info("Creating a new Event Notification in Get Request Method...");
		Company company = companyService.findById(new Long(companyid));
		VehicleEvent vEvent = new VehicleEvent();
		vEvent.setCompany(company);
		vEvent.setType("VHC_START");
		vEvent.setMessage("This is a notification for VHC_START at " + new java.util.Date());
		
		boolean resultStatus = topicNotificationMessageProducer.filterEventType(vEvent);
		
		vEvent.setNotifSent(resultStatus);
		
		model.addAttribute("vEvent",vEvent);
		
		return "notification/run";
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/wardNotification")
	
	public String wardNotification(@RequestParam(value = "name", required = true)String wardName, Model model) {
		log.info("Assign special Notification to Ward..." + wardName);

		Ward ward = wardService.findByName(wardName);
		

		List<WardNotificationSetting> notificationSettings = 
				wardNotificationSettingService.findByWardName(wardName);
		
		NotificationSettingsForm nsForm = new NotificationSettingsForm();
		if(notificationSettings.size() > 0 && notificationSettings != null){
			log.info("There is vehicle notification settings " + notificationSettings.size());
			model.addAttribute("notificationSettings",notificationSettings);
			nsForm.setWardNotificationSettings(notificationSettings);
		}else{
			log.info("There is no ward notification settings and use default settings" );

			List<WardNotificationSetting> defaultNotificationSettings = new ArrayList<WardNotificationSetting>();
			for(EventTypeSelf e:EventTypeSelf.values()){
				WardNotificationSetting ns = new WardNotificationSetting();
				ns.setEventType(e.toString());
				ns.setWard(ward);
				defaultNotificationSettings.add(ns);
			}
			nsForm.setWardEventTypes(defaultNotificationSettings);

		}
		
		model.addAttribute("nsForm", nsForm);
		model.addAttribute("ward",ward);
		return "notification/wardSearch";
	}
	
	
    @RequestMapping(value = "/wardNotification", method = RequestMethod.POST)
    public String saveWardNotification(@ModelAttribute("nsForm") NotificationSettingsForm nsForm, HttpSession session,HttpServletRequest req) {
    	log.info("In the Ward seting post method...");
        List<WardNotificationSetting> notificationSettings = nsForm.getWardNotificationSettings();
        List<WardNotificationSetting> eventTypes = nsForm.getWardEventTypes();
        Ward ward = (Ward)session.getAttribute("ward");
        

        
        if(null != notificationSettings && notificationSettings.size() > 0) {
            
            for (WardNotificationSetting notificationSetting : notificationSettings) {
                log.info(notificationSetting.getEventType() 
                    	+ "******" +notificationSetting.isEmailEnable()
    		            + "******" +notificationSetting.isSmsEnable()
    		            + "******" +notificationSetting.getEmailaddress());
                wardNotificationSettingService.update(notificationSetting);
                
            }
        }else{
            for (WardNotificationSetting notificationSetting : eventTypes) {
                log.info(notificationSetting.getEventType() 
                	+ "======" +notificationSetting.isEmailEnable()
		            + "======" +notificationSetting.isSmsEnable()
		            + "======" +notificationSetting.getEmailaddress());
                wardNotificationSettingService.create(notificationSetting);
                
            }
        }
         
         
        //return  "/safe-link/wards/search";
        return "notification/wardSearch";

    }
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value="/edit")
	public Model editCompany(@RequestParam(value = "name", required = true) String name, Model model) {
		model.addAttribute("company", companyService.findByName(name));

		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/edit")
	public String updateCompany(@Valid Company company,
			BindingResult bindingResult) {
/*		if (bindingResult.hasErrors()) {
			return "company/edit";
		}*/

		try {
			companyService.update(company);
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("company", "name",
					messageSource.getMessage("company.nameExists", null,
							null)));
			return "company/edit";
		}

		return "redirect:/" + "company/search";
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/delete")
	public Model deleteCompany(@RequestParam(value = "name", required = true) String name, Model model) {
		
		model.addAttribute("name", name);
		
		return model;
	}
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public String deleteCompany1(@RequestParam(value = "name", required = true) String name) {
		
		try {
			companyService.delete(name);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/" + "company/search";
		}
		
		return "redirect:/" +  "company/search";
	}

}
