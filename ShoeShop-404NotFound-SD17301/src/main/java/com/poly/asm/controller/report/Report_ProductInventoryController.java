package com.poly.asm.controller.report;

import java.util.List;

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
import com.poly.asm.dao.StockReceiptRepository;
import com.poly.asm.model.ProductInventory;
import com.poly.asm.model.Report;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;



@Controller
@RequestMapping("/shoeshop")
public class Report_ProductInventoryController {

    @Autowired
    private StockReceiptRepository stockReceiptRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired ProductRepository daoProductRepository;	
    @GetMapping("admin/report/productInventory")
    public String productInventory(
            Model model,
            @RequestParam(defaultValue = "") String productName,
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
        Page<ProductInventory> productInventoryPage = stockReceiptRepository.getProductInventory(productName, pageable);

        model.addAttribute("productInventory", productInventoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productInventoryPage.getTotalPages());
        model.addAttribute("searchName", productName);

        //System.out.println(productInventoryPage.getContent() + "ssssss");
		
        return "admin/report/product_Inventory";
    }
}

