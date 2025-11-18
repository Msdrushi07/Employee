package com.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

	Employee findByEmpId(String empid);
	Employee  findByName(String name);
	Employee findByNameAndSallary(String name,String sallary);
	void deleteByName(String name);
	@Query("SELECT p FROM Product p WHERE p.price > :minPrice")
    List<Product> findByMinPrice(@Param("minPrice") Double minPrice);

    @Query(value = "SELECT * FROM products WHERE name LIKE %:name%", nativeQuery = true)
    List<Product> searchByName(@Param("name") String name);

}
