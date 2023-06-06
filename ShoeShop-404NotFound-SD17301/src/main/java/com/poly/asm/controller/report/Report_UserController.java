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

import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.User;
import com.poly.asm.model.UserOrderCountDTO;
import com.poly.asm.service.SessionService;

@Controller
@RequestMapping("/shoeshop")
public class Report_UserController {

    @Autowired
    private UserRepository repository;

	@Autowired
	SessionService session;
    
    @GetMapping("admin/users/order-counts")
    public String orderCounts(Model model, @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") int page , @ModelAttribute("user") User user) {
       
    	if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			// System.out.println(user.getImage() + "ssssssssssssssssssssssssssss");
			model.addAttribute("user", user);
		}
    	
    	int pageSize = 2; // Số lượng kết quả hiển thị trên mỗi trang
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<UserOrderCountDTO> orderCountsPage = repository.getUserOrderCountsByName(name, pageable);

        model.addAttribute("orderCounts", orderCountsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderCountsPage.getTotalPages());
        model.addAttribute("searchName", name);

        return "admin/report/users_order-counts";
    }


}
