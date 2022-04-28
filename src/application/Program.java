package application;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		
		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		List<Seller> sellerList = new ArrayList<>();
		
		System.out.println("==== TEST 1: seller findById ====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n==== TEST 2: Department findById ====");
		Department dep = depDao.findById(2);
		System.out.println(dep);
		
		System.out.println("\n==== TEST 3: seller findByDepartment ====");
		sellerList = sellerDao.findByDepartment(dep);
		for(Seller sel: sellerList) {
			System.out.println(sel);
		}
		
		System.out.println("\n==== TEST 4: seller findAll ====");
		sellerList = sellerDao.findAll();
		for(Seller sel: sellerList) {
			System.out.println(sel);
		}
	}

}
