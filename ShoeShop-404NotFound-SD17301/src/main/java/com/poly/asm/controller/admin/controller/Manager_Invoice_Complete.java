package com.poly.asm.controller.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.InvoiceRepository;
import com.poly.asm.model.Invoice;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

@Controller
@RequestMapping("/shoeshop/admin/dilivered")
public class Manager_Invoice_Complete {
	@Autowired
	private IndexController indexController;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	SessionService session;

	@GetMapping("/complete/{id}")
	public String edit(Model model, @PathVariable("id") String id, @ModelAttribute("user") User user) {

		indexController.checkUser(model);

		Invoice invoice = invoiceRepository.findById(id).get();
		invoice.setStatus("complete");
		invoiceRepository.save(invoice);

		String successMessage = "Đơn hàng đã được hoàn thành!";
		model.addAttribute("successMessage", successMessage);
		return "redirect:/shoeshop/admin/delivered-update";

	}

}
