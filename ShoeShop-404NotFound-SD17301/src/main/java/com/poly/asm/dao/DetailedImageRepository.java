package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.asm.model.DetailedImage;

public interface DetailedImageRepository extends JpaRepository<DetailedImage, Integer> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
