package com.poly.asm.controller.admin.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.model.Category;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop/admin/list-category")
public class CategoryController {

	@Autowired
	CategoryRepository dao; // làm việc với bảng category

	@Autowired
	SessionService session;

	@Autowired
	private IndexController indexController;

//	@RequestMapping("/index")
//	public String index(Model model) {
//		category category = new category();
//		model.addAttribute("category", category);
//		List<category> categorys = dao.findAll();
//		model.addAttribute("categorys", categorys);
//		return "/index";
//	

	public static String generateRandomNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
			sb.append(digit);
		}
		return sb.toString();
	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") String id, @ModelAttribute("user") User user) {

		indexController.checkUser(model);

		Category category = dao.findById(id).get();
		model.addAttribute("category", category);
		List<Category> categories = dao.findAll();
		model.addAttribute("categories", categories);
		return "/admin/views/ui-category";

	}

	@RequestMapping("/create")
	public String create(Model model, @Valid @ModelAttribute("category") Category category, BindingResult rs,
			@ModelAttribute("user") User user) {
		indexController.checkUser(model);

		if (rs.hasErrors()) {
			String successMessage = "create failed";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-category";
		}
		List<Category> categories = dao.findAll();
		for (Category b : categories) {
			if (b.getId().equalsIgnoreCase(category.getId())) {
				String successMessage = "ID đã tồn tại !";
				model.addAttribute("failed", successMessage);
				return "/admin/views/ui-category";
			}
			if (b.getName().equalsIgnoreCase(category.getName())) {
				String successMessage = "Tên danh mục đã tồn tại !";
				model.addAttribute("failed", successMessage);
				return "/admin/views/ui-category";
			}
		}

		if (category.getId().equals("")) {
			category.setId(generateRandomNumber());
		}
		dao.save(category);
		String successMessage = "Create successful";
		model.addAttribute("successMessage", successMessage);
		return "/admin/views/ui-category";
	}

	@RequestMapping("/update")
	public String update(Model model, @Valid @ModelAttribute("category") Category category, BindingResult rs,
			@ModelAttribute("user") User user) {

		indexController.checkUser(model);

		if (rs.hasErrors()) {
			String successMessage = "Update failed";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-category";
		}
		List<Category> categories = dao.findAll();
		for (Category b : categories) {
			if (b.getId().equalsIgnoreCase(category.getId())) {
				dao.save(category);
				return "redirect:/shoeshop/admin/list-category/edit-update/" + category.getId();
			}
		}
		String successMessage = "ID không tồn tại!";
		model.addAttribute("failed", successMessage);

		return "/admin/views/ui-category";

	}

	@RequestMapping("/edit-update/{id}")
	public String editUpdate(Model model, @PathVariable("id") String id, @ModelAttribute("user") User user) {

		indexController.checkUser(model);

		String successMessage = "Update successful";
		model.addAttribute("successMessage", successMessage);
		Category category = dao.findById(id).get();
		model.addAttribute("category", category);
		List<Category> categorys = dao.findAll();
		model.addAttribute("categorys", categorys);
		return "/admin/views/ui-category";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id, @ModelAttribute("category") Category category, Model model) {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			String successMessage = "Danh mục này đang có sản phẩm!";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-category";
		}
		return "redirect:/shoeshop/admin/list-category";
	}

}
