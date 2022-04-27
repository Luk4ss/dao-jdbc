package model.dao;

import db.DB;
import model.dao.impl.SellerDaoimplJDBC;

public class DaoFactory {
	public static SellerDao createSellerDao() {
		return new SellerDaoimplJDBC(DB.getConnection());
	}
}
