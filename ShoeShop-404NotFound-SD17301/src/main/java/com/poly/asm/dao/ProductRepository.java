package com.poly.asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.poly.asm.model.Product;
import com.poly.asm.model.ProductInventory;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("SELECT new com.poly.asm.model.ProductInventory(p.id, p.name, SUM(s.quantity) AS totalInventory)" +
		       "FROM StockReceipt s " +
		       "INNER JOIN Product p ON p.id = s.id " +
		       "GROUP BY p.id, p.name")
		Page<ProductInventory> getProductInventory(Pageable pageable);
}
