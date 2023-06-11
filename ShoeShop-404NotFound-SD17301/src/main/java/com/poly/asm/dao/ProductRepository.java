package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.DetailTotalQuantity;
import com.poly.asm.model.NewProductTop10;
import com.poly.asm.model.Product;
import com.poly.asm.model.ProductDetail;
import com.poly.asm.model.Report;
import com.poly.asm.model.TotalOderQuantity;
import com.poly.asm.model.totalRevenue;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	// tổng số lượng hàng
	@Query(" SELECT  new com.poly.asm.model.Report( SUM(p.quantity) AS totalQuantity )" + " FROM Product p")
	List<Report> getTotalQuantity();

	// tổng doanh thu
	@Query("SELECT new com.poly.asm.model.Report(SUM(d.quantity * p.price) AS totalRevenue) "
			+ "FROM DetailedInvoice d " + "JOIN d.product p")
	List<Report> getTotalRevenue();

	Page<Product> findAllByNameLike(String keywords, Pageable pageable);

	Page<Product> findAll(Pageable pageable);

	// top 10 sản phẩm mới về index
	@Query("SELECT new com.poly.asm.model.NewProductTop10(p.id AS id, p.name AS name, "
			+ "p.quantity AS quantity, p.price AS price, p.description AS description,"
			+ " s.orderDate AS orderDate , b.name AS brandName , di.mainImage AS image)" + " FROM Product p "
			+ "JOIN StockReceipt s ON s.product.id  = p.id " + "JOIN Brand b ON b.id = p.brand.id "
			+ "JOIN DetailedImage di ON di.product.id = p.id")
	Page<NewProductTop10> getNewProductTop10(Pageable pageable);

	// tìm kiếm theo tên &&&& report chi tiết tổng số lượng nhập kho
	@Query("SELECT new com.poly.asm.model.DetailTotalQuantity(p.id AS id, p.name AS name, SUM(s.quantity) AS quantity, s.price AS price) "
			+ "FROM StockReceipt s " + "JOIN Product p " + "ON p.id = s.product.id "
			+ "WHERE lower(p.name) LIKE lower(concat('%', :productName, '%')) " + "GROUP BY p.id, p.name, s.price "
			+ " ORDER BY quantity ASC")
	Page<DetailTotalQuantity> getDetailTotalQuantity(@Param("productName") String productName, Pageable pageable);

	// report tổng số lượng doanh thu
	@Query("SELECT new com.poly.asm.model.totalRevenue"
			+ "( p.id AS id, p.name AS name, p.price AS price, d.quantity AS quantity, SUM(p.price * d.quantity) AS total) "
			+ " FROM Product p " + " JOIN DetailedInvoice d " + " ON p.id = d.product.id "
			+ " WHERE p.name LIKE %:name% " + " GROUP BY p.id, p.name, p.price, d.quantity")
	Page<totalRevenue> getTotalRevenue(@Param("name") String name, Pageable pageable);

	// report tổng số lượng mua hàng
	@Query("SELECT new com.poly.asm.model.TotalOderQuantity"
			+ "(u.id AS id, u.name AS userName, p.name AS productName, SUM(d.quantity) AS quantity) "
			+ "FROM DetailedInvoice d " + "JOIN Product p ON p.id = d.product.id "
			+ "JOIN Invoice i ON i.id = d.invoice.id " + "JOIN User u ON u.id = i.user.id "
			+ "WHERE p.name LIKE %:name% " + "GROUP BY u.name, p.name, u.id")
	Page<TotalOderQuantity> getTotalOderQuantity(@Param("name") String name, Pageable pageable);

	@Query("SELECT new com.poly.asm.model.ProductDetail"
			+ "(id AS id ,name AS name , quantity AS quantity ,price AS price) " + "FROM Product d "
			+ "WHERE name LIKE %:name% ")
	Page<ProductDetail> getProductDetail(@Param("name") String name, Pageable pageable);

	@Query("SELECT p FROM Product p INNER JOIN p.detailedInvoices di WHERE di.invoice.id = :invoiceId")
	List<Product> findByInvoiceId(@Param("invoiceId") String invoiceId);
}
