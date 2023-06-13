package com.poly.asm.controller.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.DetailedInvoiceRepository;
import com.poly.asm.dao.InvoiceRepository;
import com.poly.asm.model.DetailedInvoice;
import com.poly.asm.model.Invoice;
import com.poly.asm.service.SessionService;

@Controller
@RequestMapping("/shoeshop/admin")
public class Manager_Invoice_Detailed {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	SessionService session;

	@Autowired
	DetailedInvoiceRepository detailedInvoiceRepository;

	@Autowired
	private IndexController indexController;

	@RequestMapping("/manager/{id}")
	public String listUIUsetAdmin(Model model, @PathVariable("id") String id) {
		indexController.checkUser(model);
		Invoice invoice = invoiceRepository.findById(id).get();
		System.out.println(invoice.getId());
		List<DetailedInvoice> detailedInvoices = detailedInvoiceRepository.findByInvoiceId(id);
		model.addAttribute("items", detailedInvoices);
		return "/admin/views/ui-manager-invoice";
	}

}
