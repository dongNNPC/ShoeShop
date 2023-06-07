package com.poly.asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.asm.model.User;
import com.poly.asm.model.UserOrderCountDTO;

public interface UserRepository extends JpaRepository<User, String> {
	// Get the list of users and the total count of orders for each user
	// câu lệnh truy vấn người dùng đã oder
	@Query("SELECT new com.poly.asm.model.UserOrderCountDTO(u.id, u.name, COUNT(i.id) AS totalOrders) " + "FROM User u "
			+ "INNER JOIN Invoice i ON u.id = i.user.id " + "GROUP BY u.id, u.name")
	Page<UserOrderCountDTO> getUserOrderCounts(Pageable pageable);

	// tìm kiếm người đã mua theo tên
	@Query("SELECT new com.poly.asm.model.UserOrderCountDTO(u.id, u.name, COUNT(i.id) AS totalOrders) " + "FROM User u "
			+ "INNER JOIN Invoice i ON u.id = i.user.id " + "WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')) "
			+ "GROUP BY u.id, u.name")
	Page<UserOrderCountDTO> getUserOrderCountsByName(@Param("name") String name, Pageable pageable);

	Page<User> findAllByNameLike(String keywords, Pageable pageable);

	Page<User> findAll(Pageable pageable);
}
