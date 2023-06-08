package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.NewProductTop10;
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


	Page<Product> findAllByNameLike(String keywords, Pageable pageable);

	Page<Product> findAll(Pageable pageable);
	
	//top 10 sản phẩm mới về index
	@Query("SELECT new com.poly.asm.model.NewProductTop10(p.id AS id, p.name AS name, "
	        + "p.quantity AS quantity, p.price AS price, p.description AS description,"
	        + " s.orderDate AS orderDate , b.name AS brandName , di.mainImage AS image)"
	        + " FROM Product p "
	        + "JOIN StockReceipt s ON s.product.id  = p.id "
	        + "JOIN Brand b ON b.id = p.brand.id "
	        + "JOIN DetailedImage di ON di.product.id = p.id" 
	        )
	Page<NewProductTop10> getNewProductTop10(Pageable pageable);





}
