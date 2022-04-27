package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	
	void insert(Seller sell);
	void update(Seller sell);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();

}
