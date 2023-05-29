package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
