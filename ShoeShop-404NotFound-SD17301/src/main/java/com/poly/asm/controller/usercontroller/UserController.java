package com.poly.asm.controller.usercontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.User;

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

		User user = dao.findById(id).get();

		model.addAttribute("user", user);
		List<User> users = dao.findAll();
		model.addAttribute("users", users);
		return "/admin/views/list-user";
	}

	@RequestMapping("/create")
	public String create(User user) {
		dao.save(user);
		return "redirect:/shoeshop/admin/list-user";
	}

	@RequestMapping("/update")
	public String update(User user) {
		dao.save(user);
		return "redirect:/shoeshop/admin/list-user/edit/" + user.getID();
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		dao.deleteById(id);
		return "redirect:/shoeshop/admin/list-user";
	}

}
