package com.poly.asm.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.asm.model.ProductInventory;
import com.poly.asm.model.StockReceipt;

public interface StockReceiptRepository extends JpaRepository<StockReceipt, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
	//tổng giá trị tổng kho mỗi sản phẩm

	 @Query("SELECT new com.poly.asm.model.ProductInventory(p.id AS id, p.name AS productName, SUM(sr.quantity * sr.price)) AS totalValue "
	 +  "FROM StockReceipt sr " + "JOIN Product p ON sr.product.id  = p.id " +
	 " GROUP BY p.id, p.name")
	
	
	Page<ProductInventory> getProductInventory(Pageable pageable);

	 @Query("SELECT new com.poly.asm.model.ProductInventory(p.id AS id, p.name AS productName, SUM(sr.quantity * sr.price)) AS totalValue "
				+ "FROM StockReceipt sr " + "JOIN Product p ON sr.product.id = p.id " +
				"WHERE p.name LIKE %:productName% " +
				"GROUP BY p.id, p.name")
	Page<ProductInventory> getProductInventory(@Param("productName") String productName, Pageable pageable);

	 



}
