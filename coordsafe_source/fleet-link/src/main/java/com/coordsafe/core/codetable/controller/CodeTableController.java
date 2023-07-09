package com.coordsafe.core.codetable.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.entity.CodeTable;
import com.coordsafe.core.codetable.exception.CodeTableException;
import com.coordsafe.core.codetable.service.CodeTableService;

/**
 * This Controller handles request for CodeTable create and search.
 * @author Darren Mok
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/" + Constants.CODETABLEHOME)
public class CodeTableController {
	
	private CodeTableService codeTableService;
	private MessageSource messageSource;
	
	@Autowired
	public void setCodeTableService(CodeTableService codeTableService) {
		this.codeTableService = codeTableService;
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = Constants.CODETABLESEARCH)
	public Model searchCodeTable(Model model) {
		model.addAttribute("codeTables", codeTableService.findAll());
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = Constants.CREATEPARAM)
	public String addCodeTable(Model model) {
		model.addAttribute(new CodeTable());
		model.addAttribute("codeTables", codeTableService.findAll());
		
		return Constants.CODETABLEHOME + Constants.CODETABLECREATE;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String createCodeTable(@Valid CodeTable codeTable, BindingResult bindingResult, Model model) {
		model.addAttribute("codeTables", codeTableService.findAll());
		
		if (bindingResult.hasErrors()) {
			return Constants.CODETABLEHOME + Constants.CODETABLECREATE;
		}
		
		if (codeTable.getStartDate() == null) {
			bindingResult.addError(new FieldError("codeTable", "startDate",
					messageSource.getMessage("codeTable.startDateBlank", null,
							null)));
			return Constants.CODETABLEHOME + Constants.CODETABLECREATE;
		} else if (codeTable.getEndDate() == null) {
			bindingResult.addError(new FieldError("codeTable", "endDate",
					messageSource.getMessage("codeTable.endDateBlank", null,
							null)));
			return Constants.CODETABLEHOME + Constants.CODETABLECREATE;
		} else if (codeTable.getStartDate().after(codeTable.getEndDate())) {
			bindingResult.addError(new FieldError("codeTable", "endDate",
					messageSource.getMessage("codeTable.endDateError", null,
							null)));
			return Constants.CODETABLEHOME + Constants.CODETABLECREATE;
		}
		
		try {
			codeTableService.save(codeTable);
		} catch (CodeTableException e) {
			e.printStackTrace();
			bindingResult.addError(new FieldError("codeTable", "type", messageSource.getMessage("codeTable.exists", null, null)));
			bindingResult.addError(new FieldError("codeTable", "code", messageSource.getMessage("codeTable.exists", null, null)));
			return Constants.CODETABLEHOME + Constants.CODETABLECREATE;
		}
		
		return "redirect:/" + Constants.CODETABLEHOME + Constants.CODETABLESEARCH;
	}
}
