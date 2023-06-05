package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.asm.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
