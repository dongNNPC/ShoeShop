package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.asm.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
