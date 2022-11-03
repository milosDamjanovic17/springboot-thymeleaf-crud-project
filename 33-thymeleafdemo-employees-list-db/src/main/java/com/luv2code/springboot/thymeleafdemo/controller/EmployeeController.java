package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	// define Employee service
	private final EmployeeService employeeService;
	
	@Autowired // since we have only one constructor injection, @Autowired is optional
	public EmployeeController(EmployeeService theEmployeeService) {
		
		employeeService = theEmployeeService;
	}
	
	// add GET mapping for listing all employees
	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		
		List<Employee> theEmployees = employeeService.findAll();
		
		theModel.addAttribute("employees", theEmployees); // bindujemo attribute koji cemo koristiti u html strani kao referencu za prolazak kroz listu
		
		return "employees/list-employees"; // posto koristimo Thymeleaf, Spring ce traziti ovu stranu u src/main/resources/templates/employees/list-employees.html
	}
	
	
	// preusmeri na Add New Employee formu i prosledi objekat Employee
	@GetMapping("/showFormForAdd") 
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee", theEmployee); // u html strani employee-form cemo koristiti "employee" bind
		
		return "/employees/employee-form"; //metoda sluzi SAMO da prebaci usera na formu za dodavanje novog Employee i da prosledi objekat Employee koji ce popuniti sacuvati u DB 
	}
	
	// lets show form for update and pre-populate it
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
		
		// get the employee from the Service
		Employee theEmployee = employeeService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee); // bindujemo objekat theEmployee u attribut "employee koji cemo koristiti na view stranici"
		
		// send over to our form
		return "employees/employee-form";
	}
	
	
	// sacuvaj popunjenu Add New Employee/showFormForAdd formu
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) { // binduj modelAtribute sa parametrom Employee theEmployee
		
		// save the employee
		employeeService.save(theEmployee);
		
		// use a redirect to prevent duplicate submission Post/Redirect/Get pattern
		return "redirect:/employees/list";
	}
	
	// let's delete an Employee
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		
		// delete an employee
		employeeService.deleteById(theId);
		
		// use a redirect to prevent duplicate submission Post/Redirect/Get pattern
		return "redirect:/employees/list"; 
	}
	
	
	
	
	
	
}
