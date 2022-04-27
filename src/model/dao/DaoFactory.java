package model.dao;

import model.dao.impl.SellerDaoimplJDBC;

public class DaoFactory {
	public static SellerDao createSellerDao() {
		return new SellerDaoimplJDBC();
	}
}
