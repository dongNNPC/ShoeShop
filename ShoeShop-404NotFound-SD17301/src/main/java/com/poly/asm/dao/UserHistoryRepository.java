package com.poly.asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.User;
import com.poly.asm.model.UserHistory;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
	@Query("SELECT h FROM UserHistory h WHERE h.user = :user ORDER BY h.idHistory DESC")
	Page<UserHistory> findAllByUserOrderByHistoryIdDesc(@Param("user") User user, Pageable pageable);

}