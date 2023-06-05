package com.poly.asm.service;

import java.util.Collection;
import java.util.List;

import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.Product;

public interface ShoppingCartService {

	  Product add(String id);

	    
	    void remove(String id);

	    
	    
	    Product update(String id, int qty);

	    /**
	     * Xóa sạch các mặt hàng trong giỏ
	     */
	    void clear();

	    /**
	     * Lấy tất cả các mặt hàng trong giỏ
	     */
	    Collection<Product> getItems();
	   
	    /**
	     * Lấy tổng số lượng các mặt hàng trong giỏ
	     */
	    int getCount();

	    /**
	     * Lấy tổng số tiền tất cả các mặt hàng trong giỏ
	     */
	    double getAmount();

}
