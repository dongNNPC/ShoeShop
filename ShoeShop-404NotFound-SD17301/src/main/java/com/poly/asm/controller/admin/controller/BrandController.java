package com.poly.asm.controller.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.BrandRepository;
import com.poly.asm.model.Brand;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop/admin/list-brand")
public class BrandController {

	@Autowired
	BrandRepository dao; // làm việc với bảng brand

	@Autowired
	private IndexController indexController;

//	@RequestMapping("/index")
//	public String index(Model model) {
//		brand brand = new brand();
//		model.addAttribute("brand", brand);
//		List<brand> brands = dao.findAll();
//		model.addAttribute("brands", brands);
//		return "/index";
//	}
	@Autowired
	SessionService session;

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") String id, @ModelAttribute("user") User user) {

		indexController.checkUser(model);

		Brand brand = dao.findById(id).get();
		model.addAttribute("brand", brand);

		List<Brand> brands = dao.findAll();
		model.addAttribute("brands", brands);
		return "/admin/views/ui-brand";

	}

	@RequestMapping("/create")
	public String create(Model model, @Valid @ModelAttribute("brand") Brand brand, BindingResult rs,
			@ModelAttribute("user") User user) {
		indexController.checkUser(model);

		if (rs.hasErrors()) {
			String successMessage = "create failed";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-brand";
		}
		List<Brand> brands = dao.findAll();
		for (Brand b : brands) {
			if (b.getId().equalsIgnoreCase(brand.getId())) {
				String successMessage = "ID đã tồn tại !";
				model.addAttribute("failed", successMessage);
				return "/admin/views/ui-brand";
			}
		}

		dao.save(brand);
		String successMessage = "Create successful";
		model.addAttribute("successMessage", successMessage);
		return "/admin/views/ui-brand";
	}

	@RequestMapping("/update")
	public String update(Model model, @Valid @ModelAttribute("brand") Brand brand, BindingResult rs,
			@ModelAttribute("user") User user) {

		indexController.checkUser(model);

		if (rs.hasErrors()) {
			String successMessage = "Update failed";
			model.addAttribute("Updatefailed", successMessage);
			return "/admin/views/ui-brand";
		}
		List<Brand> brands = dao.findAll();
		for (Brand b : brands) {
			if (b.getId().equalsIgnoreCase(brand.getId())) {
				dao.save(brand);
				return "redirect:/shoeshop/admin/list-brand/edit-update/" + brand.getId();
			}
		}
		String successMessage = "ID không tồn tại!";
		model.addAttribute("failed", successMessage);
		return "/admin/views/ui-brand";

	}

	@RequestMapping("/edit-update/{id}")
	public String editUpdate(Model model, @PathVariable("id") String id, @ModelAttribute("user") User user) {
		indexController.checkUser(model);
		String successMessage = "Cập nhật thành công";
		model.addAttribute("successMessage", successMessage);
		Brand brand = dao.findById(id).get();
		model.addAttribute("brand", brand);

		List<Brand> brands = dao.findAll();
		model.addAttribute("brands", brands);
		return "/admin/views/ui-brand";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		dao.deleteById(id);
		return "redirect:/shoeshop/admin/list-brand";
	}

}
