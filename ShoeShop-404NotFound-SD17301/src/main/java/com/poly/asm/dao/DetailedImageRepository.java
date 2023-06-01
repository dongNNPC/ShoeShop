package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.DetailedImage;

@Repository
public interface DetailedImageRepository extends JpaRepository<DetailedImage, Integer> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
