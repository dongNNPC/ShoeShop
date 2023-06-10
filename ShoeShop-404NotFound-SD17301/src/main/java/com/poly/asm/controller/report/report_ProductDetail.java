package com.poly.asm.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Product;
import com.poly.asm.model.ProductDetail;
import com.poly.asm.model.User;
import com.poly.asm.model.totalRevenue;

@Controller
@RequestMapping("/shoeshop")
public class report_ProductDetail {
	
	
	@Autowired ProductRepository dao;
	
	   @GetMapping("admin/report/productDetail")
	    public String productDetail(
	            Model model,
	            @RequestParam(defaultValue = "") String name1,
	            @RequestParam(defaultValue = "0") int page,
	            @ModelAttribute("user") User user
	    ) {
	        if (user == null) {
	            // Xử lý khi session là null
	            // Ví dụ: Tạo một đối tượng User mặc định
	            user = new User();
	            model.addAttribute("user", user);
	        } else {
	            model.addAttribute("user", user);
	        }

	        int pageSize = 5; // Số lượng kết quả hiển thị trên mỗi trang
	        Pageable pageable = PageRequest.of(page, pageSize);
	        Page<ProductDetail> productDetail = dao.getProductDetail("%" + name1 + "%", pageable);

	        model.addAttribute("productDetail", productDetail.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", productDetail.getTotalPages());
	        model.addAttribute("searchName1", name1);

	       // System.out.println(DetailTotalQuantity.getContent() + "ssssss");
			
	        return "admin/report/product_detail";
	    }
}
