package com.poly.asm.controller.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop/admin/list-product")
public class DetailedImageController {

	@Autowired
	DetailedImageRepository daoDetailedImage; // làm việc với ảnh chi tiết

	@Autowired
	SessionService session;

	@RequestMapping("/updateImage")
	public String updateImage(Model model, @Valid @ModelAttribute("detailedImage") DetailedImage detailedImage,
			BindingResult rs, @ModelAttribute("user") User user) {

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
		daoDetailedImage.save(detailedImage);

		return "redirect:/shoeshop/admin/list-product/edit/" + detailedImage.getProduct().getId();
	}
}
