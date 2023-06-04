package com.poly.asm.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // Các phương thức tùy chọn khác nếu cần thiết (Other optional methods if necessary)

   
}
