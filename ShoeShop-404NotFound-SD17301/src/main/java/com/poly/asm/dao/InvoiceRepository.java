package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Invoice;
import com.poly.asm.model.MonthlySalesStatistics;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	// Các phương thức tùy chọn khác nếu cần thiết

	@Query("SELECT new com.poly.asm.model.MonthlySalesStatistics (MONTH(orderDate), COUNT(*)) FROM Invoice WHERE status = 'Pending' GROUP BY MONTH(orderDate)")
	List<MonthlySalesStatistics> getMonthlySalesStatistics();

//	@Query("SELECT new com.poly.asm.model.MonthlySalesStatistics (COUNT(*)) FROM Invoice WHERE status = 'Pending' GROUP BY MONTH(orderDate)")
//	List<MonthlySalesStatistics> getMonthlySalesStatistics();

}
