package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
