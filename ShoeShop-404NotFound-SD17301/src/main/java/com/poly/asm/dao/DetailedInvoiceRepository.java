package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.DetailedInvoice;

@Repository
public interface DetailedInvoiceRepository extends JpaRepository<DetailedInvoice, Long> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
