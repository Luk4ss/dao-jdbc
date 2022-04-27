package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat  sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		
		Department depart = new Department(1, "Books");
		System.out.println(depart);
		
		Seller seller = new Seller(2, "Lucas", "lucas@gmail.com", sdf.parse("20/05/1995"), 2550.85, depart);
		System.out.println(seller);
	}

}
