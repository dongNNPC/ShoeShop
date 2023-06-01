package com.poly.asm.controller.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.dao.BrandRepository;
import com.poly.asm.model.Brand;

import jakarta.validation.Valid;

public class BrandController {

	@Autowired
	BrandRepository dao; // làm việc với bảng brand

//	@RequestMapping("/index")
//	public String index(Model model) {
//		brand brand = new brand();
//		model.addAttribute("brand", brand);
//		List<brand> brands = dao.findAll();
//		model.addAttribute("brands", brands);
//		return "/index";
//	}

	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") String id) {
		Brand brand = dao.findById(id).get();
		model.addAttribute("brand", brand);

		List<Brand> brands = dao.findAll();
		model.addAttribute("brands", brands);
		return "/admin/views/ui-brand";

	}

	@RequestMapping("/create")
	public String create(Model model, @Valid @ModelAttribute("brand") Brand brand, BindingResult rs) {
		if (rs.hasErrors()) {
			String successMessage = "create failed";
			model.addAttribute("failed", successMessage);
			return "/admin/views/ui-brand";
		}
		dao.save(brand);
		String successMessage = "Create successful";
		model.addAttribute("successMessage", successMessage);
		return "/admin/views/ui-brand";
	}

	@RequestMapping("/update")
	public String update(Model model, @Valid @ModelAttribute("brand") Brand brand, BindingResult rs) {
		if (rs.hasErrors()) {
			String successMessage = "Update failed";
			model.addAttribute("Updatefailed", successMessage);
			return "/admin/views/ui-brand";
		}

		dao.save(brand);
		return "redirect:/shoeshop/admin/list-brand/edit/" + brand.getId();
	}

	@RequestMapping("/edit-update/{id}")
	public String editUpdate(Model model, @PathVariable("id") String id) {

		String successMessage = "Update successful";
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
