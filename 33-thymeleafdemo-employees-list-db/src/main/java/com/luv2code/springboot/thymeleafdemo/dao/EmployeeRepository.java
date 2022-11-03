package com.luv2code.springboot.thymeleafdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	
	
	// add a method to sort by last name
	public List<Employee> findAllByOrderByLastNameAsc();
	/*
	 * Spring Data JPA will PARSE the method name (findAllByOrderByLastNameAsc)
	 * 
	 * Looks for a specific format and pattern and Creates appropriate query ... behind the scenes
	 * 
	 * Sta ce ustvari Spring Data JPA procitati => "from Employee order by lastName asc"
	 * 
	 * **za vise ovakvih slucajeva: luv2code.com/query-methods
	 * 
	 */
	
	
}
