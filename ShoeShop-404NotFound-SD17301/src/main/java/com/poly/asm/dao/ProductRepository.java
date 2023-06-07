package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.poly.asm.model.Product;
import com.poly.asm.model.ProductInventory;
import com.poly.asm.model.Report;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	//tổng số lượng hàng 
	@Query (" SELECT  new com.poly.asm.model.Report( SUM(p.quantity) AS totalQuantity )"
			+ " FROM Product p")
	List<Report> getTotalQuantity();
	
	//tổng doanh thu 
	@Query("SELECT new com.poly.asm.model.Report(SUM(d.quantity * p.price) AS totalRevenue) " +
		       "FROM DetailedInvoice d " +
		       "JOIN d.product p")
		List<Report> getTotalRevenue();

	//12345678
}
