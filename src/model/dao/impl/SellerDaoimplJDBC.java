package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoimplJDBC implements SellerDao {
	
	private Connection conn;
	
	
	
	public SellerDaoimplJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller sell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller sell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if(rs.next()) {
				
				Department dep = instantiateDepartment(rs);
				
				Seller sell = instantiateSeller(rs, dep);
				
				return sell;
			}
			return null;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
		
		
	}
	
	
	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.id " 
					+ "WHERE DepartmentId = ? " 
					+ "ORDER BY Name ");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> sellerList = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();
			
	
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller sell = instantiateSeller(rs, dep);
				sellerList.add(sell);
			}
				
				
			return sellerList;
						
		
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
				
		
		
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sell = new Seller();
		sell.setId(rs.getInt("Id"));
		sell.setName(rs.getString("Name"));
		sell.setEmail(rs.getString("Email"));
		sell.setBaseSalary(rs.getDouble("BaseSalary"));
		sell.setBirthDate(rs.getDate("BirthDate"));
		sell.setDepartment(dep);
		return sell;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

}
