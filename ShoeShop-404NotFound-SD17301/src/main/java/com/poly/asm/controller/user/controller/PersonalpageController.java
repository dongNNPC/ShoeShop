package com.poly.asm.controller.user.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/shoeshop")
public class PersonalpageController {
	
	@Autowired
	UserRepository dao; //dao của user
	@Autowired
	SessionService session;
//	
	@GetMapping("/personal-page")
	public String Personal(@ModelAttribute("user") User user, Model model) {
		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			model.addAttribute("user", user);
		}		
		return "/account/personalpage";
		
	}

	@PostMapping("/personal-page")
	public String PersonalCheck(@Valid @ModelAttribute("user") User user, Model model, BindingResult rs) {
		if (rs.hasErrors()) {
//			String successMessage = "create failed";
//			model.addAttribute("failed", successMessage);
			System.out.println(rs.toString());
			return "/account/personalpage";
		}
//		a = dao.findById(id).get();
		dao.save(user);
		model.addAttribute("message", "cập nhật thành công");
		
	return "/account/personalpage";
		
		
	}
	
//	//cập nhật
//	@RequestMapping(value = "/personal-page", method = RequestMethod.POST)
//	public String updatePersonalPage(@ModelAttribute("user") User user) {
//	  User currentUser = session.get("user");
//	  
//	  currentUser.setName(user.getName());
//	  currentUser.setEmail(user.getEmail());
//	  currentUser.setPassword(user.getPassword());
//	  currentUser.setPhone(user.getPhone());
//	  currentUser.setAddress(user.getAddress());
//	  
//	  dao.save(currentUser);
//	  
//	  return "redirect:/personal-page/" + currentUser.getID();
//	}
//	@RequestMapping("/update")
//	public String update (Model model, @Valid @ModelAttribute("user") User user, BindingResult result) {
//		if (result.hasErrors()) {
//			String successMessage = "Cập nhật thất bại";
//			model.addAttribute("Updatefailed", successMessage);
//			return "/account/personalpage";
//		}
//		List<User> users = dao.findAll();
//		for (User user2 : users) {
//			if (user2.getID().equalsIgnoreCase(user.getID())) {
//				dao.save(user);
//				return "/account/personalpage";
//			}
//		}
//		String successMessage = "ID Không tồn tại !";
//		model.addAttribute("failed", successMessage);
//		return "/account/personalpage";
//		
//	}
}
