package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoimplJDBC implements DepartmentDao {
	
	private Connection conn;
	
	
	public DepartmentDaoimplJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department depart) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, depart.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					depart.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Error! No rows affected!");
			}
			
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department depart) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			st.setString(1, depart.getName());
			st.setInt(2, depart.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
		}
		
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
		
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()){
				Department dep = instantiateDepartment(rs);
				return dep;
			}
		}
		catch (SQLException e) {
			
			throw new DbException(e.getMessage());
			
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
		
		
		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		
		try {
			st = conn.prepareStatement("SELECT * from department");
			rs = st.executeQuery();
			List<Department> departmentList = new ArrayList<>();
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				departmentList.add(dep);
			}
			
			return departmentList;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

}
