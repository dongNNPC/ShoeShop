package com.poly.asm.controller.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop/admin/list-user")
public class UserController {

	@Autowired
	UserRepository dao; // làm việc với bảng User

//	@RequestMapping("/index")
//	public String index(Model model) {
//		User user = new User();
//		model.addAttribute("user", user);
//		List<User> users = dao.findAll();
//		model.addAttribute("users", users);
//		return "/index";
//	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") String id) {
		String successMessage = "Update successful";
		model.addAttribute("successMessage", successMessage);
		User user = dao.findById(id).get();
		model.addAttribute("user", user);
		List<User> users = dao.findAll();
		model.addAttribute("users", users);
		return "/admin/views/ui-user";

	}

	@RequestMapping("/create")
	public String create(Model model, @Valid @ModelAttribute("user") User user, BindingResult rs) {
		if (rs.hasErrors()) {
			String successMessage = "create failed";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-user";
		}
		dao.save(user);
		String successMessage = "Create successful";
		model.addAttribute("successMessage", successMessage);
		return "/admin/views/ui-user";
	}

	@RequestMapping("/update")
	public String update(Model model, @Valid @ModelAttribute("user") User user, BindingResult rs) {
		if (rs.hasErrors()) {
			String successMessage = "Update failed";
			model.addAttribute("Updatefailed", successMessage);
			return "/admin/views/ui-user";
		}

		dao.save(user);
		return "redirect:/shoeshop/admin/list-user/edit/" + user.getID();
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		dao.deleteById(id);
		return "redirect:/shoeshop/admin/list-user";
	}

}
