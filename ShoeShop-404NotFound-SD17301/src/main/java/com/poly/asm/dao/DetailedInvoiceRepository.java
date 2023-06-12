package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.DetailedInvoice;
import com.poly.asm.model.Report;
import com.poly.asm.model.UserOderPayment;

@Repository
public interface DetailedInvoiceRepository extends JpaRepository<DetailedInvoice, Long> {
	// Các phương thức tùy chọn khác nếu cần thiết
	// tổng số lượng hoá đơn
	@Query("SELECT new com.poly.asm.model.Report(SUM(d.quantity) AS totalODer) " + "FROM DetailedInvoice d ")
	List<Report> getTotalODer();

	List<DetailedInvoice> findByInvoiceId(String invoiceId);
	
	
	//tổng hợp các user - oder - trạng thái - phương thức thanh toán
	@Query("SELECT new com.poly.asm.model.UserOderPayment"
			+ "(u.name AS name,c.orderDate AS purchaseDate, i.orderDate AS deliveryDate , i.status AS status ,d.paymentMethod AS payment  ) "
			+ "FROM DetailedInvoice d "
			+ "JOIN Invoice i ON i.id = d.invoice.id "
			+ "JOIN User u ON u.id = i.user.id "
			+ "JOIN Cart c ON c.user.id = u.id" )
	Page<UserOderPayment> getUserOderPay(Pageable pageable);
	
}
