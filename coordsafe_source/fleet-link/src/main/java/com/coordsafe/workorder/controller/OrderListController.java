/**
 * TripController.java
 * May 31, 2013
 * Yang Wei
 */
package com.coordsafe.workorder.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.service.UserService;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.vehicle.service.VehicleGroupService;
import com.coordsafe.vehicle.service.VehicleService;
import com.coordsafe.workorder.entity.BasicOrder;
import com.coordsafe.workorder.entity.OrderList;
import com.coordsafe.workorder.entity.SampleOrder;
import com.coordsafe.workorder.service.OrderListService;
import com.coordsafe.workorder.service.OrderService;
import com.coordsafe.workorder.util.OrderNoGenerator;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/orderlist")
@SessionAttributes("orderList")
public class OrderListController {
	private static final Logger logger = Logger.getLogger(OrderListController.class);
	
	@Autowired
	private OrderService orderSvrs;
	
	@Autowired
	private OrderListService orderListSvrs;
	
	@Autowired
	private UserService userSvrs;
	
	@Autowired
	private LocatorService locatorService;
	
	@Autowired
	private VehicleService vehicleSvrs;
	
	@Autowired
	private VehicleGroupService vehicleGroupService;
	
	@ModelAttribute("orderLists")
	@RequestMapping(value="/search")
	public List<OrderList> getAllOrderLists(@RequestParam String companyid) {
		
		return orderListSvrs.findByCompany(new Long(companyid));
		//return vehicleService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/create")
	public String creatOrder(Model model, @RequestParam long companyId){
		OrderList orderList = new OrderList();
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("vehicles", vehicleSvrs.findVehiclesByCompany(String.valueOf(companyId)));
		
		return "orderlist/create";
	}
	
	@RequestMapping(value="/json")
	public @ResponseBody List<OrderList> retrieveOrders(@RequestParam String companyid) {
		
		return orderListSvrs.findByCompany(new Long(companyid));
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/create")
	public void creatOrderList(@Valid OrderList orderList, BindingResult result, HttpServletRequest request, 
			HttpServletResponse response){
		String createBy = request.getUserPrincipal().getName();
		User user = userSvrs.findByUsername(createBy);
		
		if (user == null){
			//TODO error message
			logger.info("no permission to create orderList");
			return;
		}
		
		orderList.setCreateBy(createBy);
		orderList.setCompany(user.getCompany());
		orderList.setCreateDt(new Date());
		orderList.setCompletion(0);
		orderList.setTaskListNum(OrderNoGenerator.generate());
		
		orderListSvrs.create(orderList);
	}
	
	@RequestMapping(value="/edit/{orderId}", method=RequestMethod.GET)
	public String editOrder (@PathVariable("orderId") long orderListId, Model model){	
		OrderList orderList = orderListSvrs.getById(orderListId);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("orders", orderSvrs.findByCompany(orderList.getCompany().getId()));
		
		return "orderlist/edit";
	}
	
	@RequestMapping(value="/{orderListId}", method=RequestMethod.GET)
	public @ResponseBody OrderList getOrder (@PathVariable("orderListId") long orderListId){
		return orderListSvrs.getById(orderListId);
	}
	
	@RequestMapping(value="/print", method=RequestMethod.GET)
	public String getPrintOrderList (Model model, @RequestParam long orderId){
		OrderList ol = orderListSvrs.getById(orderId);
		
		model.addAttribute("orderList", ol);
		
		return "orderlist/print";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public @ResponseBody OrderList updateOrderList (@Valid @ModelAttribute("orderList") OrderList orderList){
		//BasicOrder order = orderSvrs.find(orderId);
		
		orderListSvrs.update(orderList);
		
		return orderList;
	}
	
	@RequestMapping(value="/delete/{orderListId}", method=RequestMethod.GET)
	public String deleteOrderList (@PathVariable("orderListId") long orderListId){
		logger.info("deleting task.." + orderListId);
		
		OrderList ol = orderListSvrs.getById(orderListId);
		List<? extends BasicOrder> orders = ol.getOrders();
		
		// remove all child relation
		for (BasicOrder so : orders){
			so.setOrderList(null);
			
			orderSvrs.update(so);
		}
		
		orderListSvrs.deleteById(orderListId);
		
		return "redirect:/orderlist/search?companyid=" + ol.getCompany().getId();
	}
}
