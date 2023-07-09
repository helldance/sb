/**
 * TripController.java
 * May 31, 2013
 * Yang Wei
 */
package com.coordsafe.workorder.controller;

import java.util.ArrayList;
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
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.service.VehicleGroupService;
import com.coordsafe.vehicle.service.VehicleService;
import com.coordsafe.workorder.entity.BasicOrder;
import com.coordsafe.workorder.entity.OrderList;
import com.coordsafe.workorder.entity.OrderStatus;
import com.coordsafe.workorder.entity.SampleOrder;
import com.coordsafe.workorder.service.OrderListService;
import com.coordsafe.workorder.service.OrderService;
import com.coordsafe.workorder.util.OrderNoGenerator;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/workorder")
@SessionAttributes("order")
public class WorkorderController {
	private static final Logger logger = Logger.getLogger(WorkorderController.class);
	
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
	
	@ModelAttribute("orders")
	@RequestMapping(value="/search")
	public List<? extends BasicOrder> getAllOrders(@RequestParam String companyid) {
		
		return orderSvrs.findByCompany(new Long(companyid));
		//return vehicleService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/create")
	public String creatOrder(Model model){
		model.addAttribute("order", new SampleOrder());
		
		return "workorder/create";
	}
	
	@RequestMapping(value="/json")
	public @ResponseBody List<SampleOrder> retrieveOrders(@RequestParam String companyid) {
		
		return (List<SampleOrder>) orderSvrs.findByCompany(new Long(companyid));
		//return vehicleService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/create")
	public void creatOrder(@Valid SampleOrder order, BindingResult result, HttpServletRequest request, 
			HttpServletResponse response){
		String createBy = request.getUserPrincipal().getName();
		User user = userSvrs.findByUsername(createBy);
		
		if (user == null){
			//TODO error message
			logger.info("no permission to create work order");
			return;
		}
		
		order.setCreateBy(createBy);
		order.setCompany(user.getCompany());
		order.setOrderDt(new Date());
		order.setStatus(OrderStatus.NEW);
		order.setOrderNumber(OrderNoGenerator.generate());
		
		orderSvrs.create(order);
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String editOrder (@RequestParam long orderId, Model model){	
		BasicOrder order = orderSvrs.find(orderId);
		
		model.addAttribute("order", order);
		
		return "workorder/edit";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String getAddToTask (@RequestParam long orderId, Model model){	
		BasicOrder order = orderSvrs.find(orderId);
		
		model.addAttribute("order", order);
		model.addAttribute("orderLists", orderListSvrs.findByCompany(order.getCompany().getId()));
		
		return "workorder/add";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addToTask (@RequestParam(value = "id", required = true) long orderId,
			@RequestParam(value = "orderList", required = true) long orderListId){
		BasicOrder order = orderSvrs.find(orderId);
		order.setOrderList(orderListSvrs.getById(orderListId));
		
		orderSvrs.update(order);
		
		//TODO update task list
		
		return "redirect:/workorder/search?companyid=" + order.getCompany().getId();
	}
	
	@RequestMapping(value="/{orderId}", method=RequestMethod.GET)
	public @ResponseBody BasicOrder getOrder (@PathVariable("orderId") long orderId){
		return orderSvrs.find(orderId);
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public @ResponseBody BasicOrder updateOrder (@Valid @ModelAttribute("order") BasicOrder order){
		//BasicOrder order = orderSvrs.find(orderId);
		
		orderSvrs.update(order);
		
		return order;
	}
	
	@RequestMapping(value="/autoplan", method=RequestMethod.GET)
	public String autoPlan (@RequestParam long companyId){
		orderSvrs.autoPlanAll(companyId);
		
		return "redirect:/workorder/search?companyid=" + companyId;
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete (@RequestParam long orderId){
		BasicOrder order = orderSvrs.find(orderId);
		
		orderSvrs.delete(orderId);
		
		return "redirect:/workorder/search?companyid=" + order.getCompany().getId();
	}
	
	
	@RequestMapping(value="/assign", method=RequestMethod.GET)
	public String getAssignOrder (@RequestParam long orderId, Model model){
		BasicOrder order = orderSvrs.find(orderId);
		
		model.addAttribute("order", order);
		model.addAttribute("vehicles", vehicleSvrs.findVehiclesByCompany(String.valueOf((order.getCompany().getId()))));
		
		return "workorder/assign";
	}
	
	@RequestMapping(value="/assign", method=RequestMethod.POST)
	public String assignOrder (@RequestParam(value = "id", required = true) long orderId,
			@RequestParam(value = "assignee", required = true) long vehicleId){
		BasicOrder order = orderSvrs.find(orderId);
		
		Vehicle v = vehicleSvrs.findVehicleById(vehicleId);
		
		order.setAssignee(v);
		order.setStatus(OrderStatus.ASSIGNED);
				
		// also update orderList
		OrderList ol = orderListSvrs.getCurrentByAssignee(v);
		
		if (ol != null){
			List<? extends BasicOrder> ols = ol.getOrders();
			List<SampleOrder> sol = (ols != null)? (List<SampleOrder>) ols : new ArrayList<SampleOrder>();
			
			logger.info("prev order: " + ol.getTaskListNum() + " " + sol.size() + " " + ols.size());
			
			if (!sol.contains(order)){
				sol.add((SampleOrder) order);
				ol.setOrders(sol);
			
				order.setOrderList(ol);
			
				orderSvrs.update(order);
				orderListSvrs.update(ol);
			}
			else {
				logger.info("order already in task list");
			}
		}
		else {
			// create new task list for this vehicle
			logger.info("create new task list for assigned order..");
			
			OrderList nl = new OrderList();
			List<SampleOrder> sol = new ArrayList<SampleOrder>();
			
			nl.setAssignee(v);
			nl.setCompany(v.getCompany());
			nl.setCompletion(0);
			nl.setCreateBy("System");
			nl.setCreateDt(new Date());
			nl.setTaskListNum(OrderNoGenerator.generate());
			sol.add((SampleOrder) order);
			/*List<SampleOrder> ods = new ArrayList<SampleOrder>();
			ods.add((SampleOrder) order);
			
			nl.setOrders(ods);*/
			nl.setOrders(sol);
			
			orderListSvrs.create(nl);
			
			// link order to orderlist
			order.setOrderList(nl);
			
			orderSvrs.update(order);
		}
		
		updateOrderList(order, OrderStatus.NEW);
		
		return "redirect:/workorder/search?companyid=" + order.getCompany().getId();
	}
	
	@RequestMapping(value="/invalidate", method=RequestMethod.GET)
	public String invalidateOrder (@RequestParam long orderId){
		BasicOrder order = orderSvrs.find(orderId);
		order.setStatus(OrderStatus.INVALIDATED);
		
		orderSvrs.update(order);
		
		updateOrderList(order, OrderStatus.INVALIDATED);
		
		return "redirect:/workorder/search?companyid=" + order.getCompany().getId();
	}
	
	@RequestMapping(value="/done", method=RequestMethod.GET)
	public String completeOrder (@RequestParam long orderId){
		BasicOrder order = orderSvrs.find(orderId);
		
		order.setStatus(OrderStatus.COMPLETED);
		order.setCompletionDt(new Date());
		
		orderSvrs.update(order);
		
		updateOrderList(order, OrderStatus.COMPLETED);
		
		return "redirect:/workorder/search?companyid=" + order.getCompany().getId();
	}
	
	@RequestMapping(value="/reopen", method=RequestMethod.GET)
	public String reopenOrder (@RequestParam long orderId){
		BasicOrder order = orderSvrs.find(orderId);
		order.setStatus(OrderStatus.REOPENED);
		
		orderSvrs.update(order);
		
		updateOrderList(order, OrderStatus.REOPENED);
		
		return "redirect:/workorder/search?companyid=" + order.getCompany().getId();
	}
	
	// update task completion
	private void updateOrderList (BasicOrder order, OrderStatus status){
		// update task completion
		OrderList ol = order.getOrderList();
		
		if (ol != null){			
			int size = ol.getOrders().size();
			double completion = ol.getCompletion();
			
			logger.info("updating task completion.." + ol.getTaskListNum() + " " + size + " " + completion);
			
			if (size != 0){					
				switch (status){
				// NEW-0, COMPLETED: 1, INVALIDATED: 2, REOPENED: 3
				case NEW:
					logger.info("0 //");
					completion = (100 * (completion * (size - 1) / 100))/size;

					break;
				case COMPLETED:
					logger.info("1 //");
					completion = (100 * (((completion/100)*size + 1)))/size;

					break;
				case INVALIDATED:
					logger.info("2 //");
					if (size > 1)
						completion = 100 * ((completion*size/100)/(size - 1));

					break;
				case REOPENED:
					logger.info("3 //");
					completion = 100 * ((completion * size / 100 - 1)/size);

					break;
				default:
					break;
				}
				
				ol.setCompletion(completion);
				
				orderListSvrs.update(ol);
			}
		}
	}
}
