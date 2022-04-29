package application;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) throws ParseException {
		
		
		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		List<Department> departmentList = new ArrayList<>();
		
		System.out.println("\n==== TEST 1: department findAll ====");
		departmentList = depDao.findAll();
		for(Department dep: departmentList) {
			System.out.println(dep);
		}
		
		System.out.println("\n==== TEST 2: Department findById ====");
		Department dep = depDao.findById(2);
		System.out.println(dep);
		
	/*	System.out.println("\n==== TEST 3: Department insert ====");
		Department depart = new Department(null, "Videogames");
		depDao.insert(depart);
		System.out.println("Department inserted!"); */
		
		System.out.println("\n==== TEST 3: Department update ====");
		dep.setName("Eletronics and Electrical Equipament");
		depDao.update(dep);
		System.out.println("Department updated!"); 
		

	}

}
