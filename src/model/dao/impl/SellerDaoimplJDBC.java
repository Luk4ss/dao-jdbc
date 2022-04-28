package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		PreparedStatement st = null;
	
		
		try {
			st = conn.prepareStatement("INSERT INTO seller\r\n"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) \r\n"
					+ "VALUES \r\n"
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, sell.getName());
			st.setString(2, sell.getEmail());
			st.setDate(3, new java.sql.Date(sell.getBirthDate().getTime()));
			st.setDouble(4, sell.getBaseSalary());
			st.setInt(5, sell.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					sell.setId(id);
				}
				
				DB.closeResultSet(rs);
			}else {
				
				throw new DbException("Unexpected error! No rows affected");
				
				
			}
						
			
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			
		}
		
	}

	@Override
	public void update(Seller sell) {
		PreparedStatement st = null;
	
		
		try {
			st = conn.prepareStatement("UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ? ", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, sell.getName());
			st.setString(2, sell.getEmail());
			st.setDate(3, new java.sql.Date(sell.getBirthDate().getTime()));
			st.setDouble(4, sell.getBaseSalary());
			st.setInt(5, sell.getDepartment().getId());
			st.setInt(6, sell.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
		}finally {
			
			DB.closeStatement(st);
			
		}	
		
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
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName \r\n"
					+ "FROM seller INNER JOIN department \r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "ORDER BY Name\r\n");
			
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
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
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
