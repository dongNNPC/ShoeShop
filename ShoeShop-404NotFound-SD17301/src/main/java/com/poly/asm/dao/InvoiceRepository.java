package com.poly.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	// Các phương thức tùy chọn khác nếu cần thiết
}
