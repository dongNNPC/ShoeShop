package com.poly.asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
	Page<Brand> findAllByNameLike(String keywords, Pageable pageable);

	Page<Brand> findAll(Pageable pageable);
}
