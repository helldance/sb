package com.coordsafe.core.codetable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.codetable.exception.CodeTableException;
import com.coordsafe.core.codetable.service.CodeTableService;

@Controller
@RequestMapping("/" + Constants.CODETABLEHOME + Constants.CODETABLEDELETE)
public class CodeTableDeleteController {

	private CodeTableService codeTableService;

	@Autowired
	public void setCodeTableService(CodeTableService codeTableService) {
		this.codeTableService = codeTableService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Model deleteCodeTable(
			@RequestParam(value = Constants.CODETABLETYPEPARAM) String type,
			@RequestParam(value = Constants.CODETABLECODEPARAM) String code,
			Model model) {

		model.addAttribute("type", type);
		model.addAttribute("code", code);

		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String deleteCodeTable1(
			@RequestParam(value = Constants.CODETABLETYPEPARAM) String type,
			@RequestParam(value = Constants.CODETABLECODEPARAM) String code) {

		try {
			codeTableService.delete(type, code);
		} catch (CodeTableException e) {
			e.printStackTrace();
			return "redirect:/" + Constants.CODETABLEHOME + Constants.CODETABLESEARCH;
		}
		
		return "redirect:/" + Constants.CODETABLEHOME + Constants.CODETABLESEARCH;
	}
}
