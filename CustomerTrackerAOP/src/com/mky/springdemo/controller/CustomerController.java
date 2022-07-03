package com.mky.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mky.springdemo.entity.Customer;
import com.mky.springdemo.service.CustomerService;
import com.mky.springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	//@RequestMapping("/list")
	@GetMapping("/list")
	public String listCustomer(Model theModel,@RequestParam(required = false)String sort) {
		
		//Get Customers from Dao
		List<Customer>theCustomers=null;
		
		if(sort!=null) {
			
			int theSortField = Integer.parseInt(sort);
			theCustomers=customerService.getCustomers(theSortField);
		}
		else {
			// no sort field provided ... default to sorting by last name
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
		}		
				
				
				customerService.getCustomers();
		theModel.addAttribute("customers",theCustomers);
		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Customer theCustomer = new Customer();
		theModel.addAttribute("customer",theCustomer);
		return "customer-form";
	}
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){
		
		customerService.saveCustomer(theCustomer);
		return"redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId,Model theModel) {
			
		/*Get the customer from database*/
		Customer theCustomer =customerService.getCustomer(theId);
		
		/*Set the customer as a model attribute to prepopulate the form*/
		theModel.addAttribute("customer",theCustomer);
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		customerService.deleteCustomer(theId);
		return"redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {
		List<Customer> theCustomers=customerService.getCustomers(theSearchName);
		theModel.addAttribute("customers",theCustomers);
		return "list-customers";
	}
	
}
