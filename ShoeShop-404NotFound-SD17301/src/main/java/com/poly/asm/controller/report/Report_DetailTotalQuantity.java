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
import com.poly.asm.model.DetailTotalQuantity;
import com.poly.asm.model.ProductInventory;
import com.poly.asm.model.User;


@Controller
@RequestMapping("/shoeshop")
public class Report_DetailTotalQuantity {
		
	@Autowired ProductRepository dao;
	
	   @GetMapping("admin/report/detailTotalQuantity")
	    public String detailTotalQuantity(
	            Model model,
	            @RequestParam(defaultValue = "") String name,
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
	        Page<DetailTotalQuantity> detailTotalQuantity = dao.getDetailTotalQuantity("%" + name + "%", pageable);
	        
	        model.addAttribute("productInventory", detailTotalQuantity.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", detailTotalQuantity.getTotalPages());
	        model.addAttribute("searchName", name);

	       // System.out.println(DetailTotalQuantity.getContent() + "ssssss");
			
	        return "admin/report/detail_TotalQuantity";
	    }

}
