package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.StockReceipt;

@Repository
public interface StockReceiptRepository extends JpaRepository<StockReceipt, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
	

	
}
