package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.DetailedInvoice;
import com.poly.asm.model.Report;

@Repository
public interface DetailedInvoiceRepository extends JpaRepository<DetailedInvoice, Long> {
	// Các phương thức tùy chọn khác nếu cần thiết
	// tổng số lượng hoá đơn
	@Query("SELECT new com.poly.asm.model.Report(SUM(d.quantity) AS totalODer) " + "FROM DetailedInvoice d ")
	List<Report> getTotalODer();

	List<DetailedInvoice> findByInvoiceId(String invoiceId);

}
