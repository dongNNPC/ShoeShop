package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.ProductInventory;
import com.poly.asm.model.StockReceipt;

@Repository
public interface StockReceiptRepository extends JpaRepository<StockReceipt, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
	//tổng giá trị tổng kho mỗi sản phẩm
	/*
	 * @Query(value =
	 * "SELECT new com.poly.asm.model.ProductInventory(s.productId, p.name, SUM(s.quantity) AS totalInventory) FROM StockReceipts s INNER JOIN Products p ON p.id = s.productId GROUP BY s.productId, p.name"
	 * ) Page<ProductInventory> getProductInventory(Pageable pageable);
	 */


}
