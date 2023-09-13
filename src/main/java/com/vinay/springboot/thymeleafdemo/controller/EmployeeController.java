package com.vinay.springboot.thymeleafdemo.controller;

import com.vinay.springboot.thymeleafdemo.entity.Employee;
import com.vinay.springboot.thymeleafdemo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService theEmployeeService){
		this.employeeService=theEmployeeService;
	}

	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		List<Employee> theEmployees=employeeService.findAll();
		theModel.addAttribute("employees", theEmployees);
		return "employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel){
		Employee theEmployee=new Employee();
		theModel.addAttribute("employee", theEmployee);
		return "employees/employee-form";
	}
	@PostMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
									Model theModel) {
		Employee theEmployee = employeeService.findById(theId);
		theModel.addAttribute("employee", theEmployee);
		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("employee") Employee theEmployee, BindingResult theBindingResult){
		if (theBindingResult.hasErrors()) {
			return "employees/employee-form";
		}
		else {
			employeeService.save(theEmployee);
		}
		return "redirect:/employees/list";
	}
	@PostMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		employeeService.deleteById(theId);
		return "redirect:/employees/list";
	}

}









