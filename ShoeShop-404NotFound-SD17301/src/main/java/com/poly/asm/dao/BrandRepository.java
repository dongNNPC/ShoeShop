package com.poly.asm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Brand;
import com.poly.asm.model.Report;
import com.poly.asm.model.reportPieChart;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
	Page<Brand> findAllByNameLike(String keywords, Pageable pageable);

	Page<Brand> findAll(Pageable pageable);

	
	  @Query("SELECT new com.poly.asm.model.reportPieChart(b.name AS BrandNameAdidas, SUM(d.quantity) AS TotalQuantityAdidas) "
	  + "FROM Product p " + "JOIN DetailedInvoice d ON d.product.id = p.id " +
	  "JOIN Brand b ON b.id = p.brand.id " + "WHERE b.name LIKE 'Adidas' " +
	  "GROUP BY b.name") List<reportPieChart> getAdiDas();
	 
	 @Query("SELECT new com.poly.asm.model.reportPieChart(b.name AS BrandNameNike, SUM(d.quantity) AS TotalQuantityNike) "
	  + "FROM Product p " + "JOIN DetailedInvoice d ON d.product.id = p.id " +
	 "JOIN Brand b ON b.id = p.brand.id " + "WHERE b.name LIKE 'Nike' " +
	 "GROUP BY b.name") List<reportPieChart> getNike();
	 
	 @Query("SELECT new com.poly.asm.model.reportPieChart(b.name AS BrandNameGucci, SUM(d.quantity) AS TotalQuantityGucci) "
	  + "FROM Product p " + "JOIN DetailedInvoice d ON d.product.id = p.id " +
	  "JOIN Brand b ON b.id = p.brand.id " + "WHERE b.name LIKE 'Gucci' " +
	 "GROUP BY b.name") List<reportPieChart> getGucci();
	 

}
