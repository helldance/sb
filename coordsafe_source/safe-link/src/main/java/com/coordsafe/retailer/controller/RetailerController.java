package com.coordsafe.retailer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.constants.Constants;
import com.coordsafe.exception.kinds.CoordSafeResponse;
import com.coordsafe.exception.kinds.ErrorMessage;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.retailer.entity.Simcard;
import com.coordsafe.retailer.form.RetailerInputForm;
import com.coordsafe.retailer.service.SimcardService;


@Controller
@RequestMapping(value="/retailer")
public class RetailerController {
	
    protected static Logger logger = LoggerFactory.getLogger(RetailerController.class);
    
    @Autowired
    private MessageSource messageSource;

	@Autowired
	private SimcardService simcardService;
	
	@Autowired
	private LocatorService locatorService;
	
    @RequestMapping(value="/search", method = RequestMethod.GET)
    public Model getSIMs(@RequestParam(value = "retailer", required = true) String name, Model model){
    	logger.info("In the search SIM GET..." + name);
    	List<Simcard> simcards = simcardService.findByGuardian(name);
    	model.addAttribute("simcards", simcards);
    	return model; 
    }
    
    

	
	@RequestMapping(method = RequestMethod.GET, value="create")
	public String createWard(Model model) {
		RetailerInputForm retailerInputForm = new RetailerInputForm();
		model.addAttribute("retailerInputForm",retailerInputForm);
		model.addAttribute("create", true);
		return "retailer" + Constants.CREATE;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="create")
	public @ResponseBody CoordSafeResponse addSIM(@Valid RetailerInputForm retailerInputForm, BindingResult bindingResult, HttpServletRequest request, 
			Model model) {
		// model.addAttribute("resourceType",
		// codeTableService.findByType("RESOURCE_TYPE"));
		logger.info("In the SIM create POST method...");
		
		String imeiCode = retailerInputForm.getSimimei().trim();
		String deviceid = retailerInputForm.getDeviceid().trim();
		String simphone = retailerInputForm.getSimphone().trim();
		String devicepasswd = retailerInputForm.getDevicepasswd().trim();
		
		Locator locator = locatorService.findLocatorByImei(deviceid);
		if(locator == null || locator.isSpoiled()==false){
			bindingResult
			.addError(new FieldError("retailerInputForm", "deviceid", messageSource
					.getMessage("retailerInputForm.deviceid", null, null)));
			return validateObject(bindingResult,700);
		}
		
		Simcard simcard = new Simcard();
		simcard.setDeviceid(deviceid);
		simcard.setSimimei(imeiCode);
		simcard.setSimretailer(request.getUserPrincipal().getName());
		simcard.setSimphone(simphone);
		simcard.setIssuedate(new Date());
		
		if(simcardService.findByID(imeiCode) != null){
			
			bindingResult
			.addError(new FieldError("retailerInputForm", "simimei", messageSource
					.getMessage("retailerInputForm.simimei", null, null)));
			return validateObject(bindingResult,700);
			
		}
		
		simcardService.create(simcard);

		locator.setDevicePassword(devicepasswd);
		locatorService.updateLocator(locator);
		return validateObject(bindingResult,600);
	}
	
	public CoordSafeResponse validateObject(BindingResult bindingResult, long csCode){
		CoordSafeResponse result = new CoordSafeResponse();

		result.csCode = csCode;
		List<FieldError> allErrors = bindingResult.getFieldErrors();
		List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
		for (FieldError objectError : allErrors) {
			errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getDefaultMessage()));
		}
		result.setErrorMessageList(errorMesages);
		result.machineMsg = "";
		result.userMsg = "";
		
		return result;
	}

}
