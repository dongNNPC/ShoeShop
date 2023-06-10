package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Invoice;
import com.poly.asm.model.MonthlySalesStatistics;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

//	câu truy vấn in ra số lượng sản phẩm bán đi theo tháng
	@Query("SELECT new com.poly.asm.model.MonthlySalesStatistics (MONTH(orderDate), COUNT(*)) FROM Invoice WHERE status = 'Pending' GROUP BY MONTH(orderDate)")
	List<MonthlySalesStatistics> getMonthlySalesStatistics();

//	Câu truy vấn in ra  hóa đơn cần duyệt có page
	Page<Invoice> findByStatus(String status, Pageable pageable);

//	danh sách các hóa đơn có trạng thái status hoặc có id chứa keywords.
	@Query("SELECT i FROM Invoice i WHERE i.status = :status AND i.id LIKE %:kwords%")
	Page<Invoice> findByStatusAndIdContaining(@Param("status") String status, @Param("kwords") String kwords,
			Pageable pageable);

//	page cơ bản
	Page<Invoice> findAllByIdLike(String keywords, Pageable pageable);

	Page<Invoice> findAll(Pageable pageable);

//	

}
