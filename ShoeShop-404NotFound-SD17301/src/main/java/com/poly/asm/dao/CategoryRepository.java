package com.poly.asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.poly.asm.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
	Page<Category> findAllByNameLike(String keywords, Pageable pageable);
	
	Page<Category> findAll(Pageable pageable);
	
}
