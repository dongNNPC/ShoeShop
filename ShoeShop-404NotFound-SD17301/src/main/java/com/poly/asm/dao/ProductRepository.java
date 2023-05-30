package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
