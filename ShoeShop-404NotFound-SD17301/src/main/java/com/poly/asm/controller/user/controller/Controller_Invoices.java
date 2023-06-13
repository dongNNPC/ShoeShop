package com.poly.asm.controller.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.BrandRepository;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.dao.DetailedInvoiceRepository;
import com.poly.asm.dao.InvoiceRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.StockReceiptRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.DetailedInvoice;
import com.poly.asm.model.Invoice;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;

//Quản lý hóa đơn cập nhật trạng thái đã hủy

@Controller
@RequestMapping("/shoeshop/invoices")
public class Controller_Invoices {
	@Autowired
	UserRepository daoUser; // làm việc với bảng User

	@Autowired
	CategoryRepository daoCategory; // Làm việc với Category

	@Autowired
	ProductRepository daoProduct; // Làm việc với product

	@Autowired
	DetailedImageRepository daoDetailedImage; // DetailedImageRepository

	@Autowired
	BrandRepository daoBrandRepository;

	@Autowired
	SessionService session;

	@Autowired
	StockReceiptRepository daoReceiptRepository;

	@Autowired
	DetailedInvoiceRepository daodetailedInvoiceRepository;

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	private IndexController indexController;

	@RequestMapping("/list_invoices")
	public String invoiceList(Model model, @ModelAttribute("user") User user,
			@RequestParam("keywords") Optional<String> kw, @RequestParam("p") Optional<Integer> p,
			@RequestParam("field") Optional<String> field, HttpServletRequest request) {
		user = session.get("user");
		indexController.checkUser(model);
		String order = request.getParameter("sortOrder");

		Sort sort = Sort.by(Direction.ASC, field.orElse("id"));
		if (order != null) {
			if (order.equals("desc")) {
				sort = Sort.by(Direction.DESC, field.orElse("id"));
			}
		}

		session.remove("keywords");
		String kwords = kw.orElse(session.get("keywords"));
		session.set("keywords", kwords, 30);
		model.addAttribute("keywords", session.get("keywords"));
		Pageable pageable = PageRequest.of(p.orElse(0), 5, sort);
		List<String> statusList = Arrays.asList("delivered", "pending");

		if (kw.isPresent()) {
			Page<Invoice> page = invoiceRepository.findByUserIdAndIdContainingAndStatus(user.getID(), kwords,
					statusList, pageable);

			model.addAttribute("invoices", page);

		} else {
			Page<Invoice> items = invoiceRepository.findByUserIdAndStatus(user.getID(), "delivered", "pending",
					pageable);

			model.addAttribute("invoices", items);

		}
		return "/views/list-invoices";
	}

//	xem chi tiết

	@RequestMapping("/invoice/manager/{id}")
	public String listinvoice(Model model, @PathVariable("id") String id) {
		indexController.checkUser(model);
		Invoice invoice = invoiceRepository.findById(id).get();
		System.out.println(invoice.getId());
		List<DetailedInvoice> detailedInvoices = daodetailedInvoiceRepository.findByInvoiceId(id);
		model.addAttribute("items", detailedInvoices);
		return "/views/ui-manager-invoice";
	}
//	cancel

	@GetMapping("/cancelled/{id}")
	public String edit(Model model, @PathVariable("id") String id, @ModelAttribute("user") User user) {
		indexController.checkUser(model);

		Invoice invoice = invoiceRepository.findById(id).get();
		invoice.setStatus("cancelled");
		invoiceRepository.save(invoice);
		return "redirect:/shoeshop/invoices/list_invoices";

	}

}
