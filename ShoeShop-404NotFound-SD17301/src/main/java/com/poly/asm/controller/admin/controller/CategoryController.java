package com.poly.asm.controller.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.model.Category;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop/admin/list-category")
public class CategoryController {

	@Autowired
	CategoryRepository dao; // làm việc với bảng category

//	@RequestMapping("/index")
//	public String index(Model model) {
//		category category = new category();
//		model.addAttribute("category", category);
//		List<category> categorys = dao.findAll();
//		model.addAttribute("categorys", categorys);
//		return "/index";
//	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") String id) {
		Category category = dao.findById(id).get();
		model.addAttribute("category", category);
		List<Category> categories = dao.findAll();
		model.addAttribute("categories", categories);
		return "/admin/views/ui-category";

	}

	@RequestMapping("/create")
	public String create(Model model, @Valid @ModelAttribute("category") Category category, BindingResult rs) {
		if (rs.hasErrors()) {
			String successMessage = "create failed";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-category";
		}
		dao.save(category);
		String successMessage = "Create successful";
		model.addAttribute("successMessage", successMessage);
		return "/admin/views/ui-category";
	}

	@RequestMapping("/update")
	public String update(Model model, @Valid @ModelAttribute("category") Category category, BindingResult rs) {
		if (rs.hasErrors()) {
			String successMessage = "Update failed";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-category";
		}
		dao.save(category);
		return "redirect:/shoeshop/admin/list-category/edit-update/" + category.getId();
	}

	@RequestMapping("/edit-update/{id}")
	public String editUpdate(Model model, @PathVariable("id") String id) {

		String successMessage = "Update successful";
		model.addAttribute("successMessage", successMessage);
		Category category = dao.findById(id).get();
		model.addAttribute("category", category);
		List<Category> categorys = dao.findAll();
		model.addAttribute("categorys", categorys);
		return "/admin/views/ui-category";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		dao.deleteById(id);
		return "redirect:/shoeshop/admin/list-category";
	}

}
