package com.poly.asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	Page<Product> findAllByNameLike(String keywords, Pageable pageable);

	Page<Product> findAll(Pageable pageable);

}
