package com.poly.asm.controller.user.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.config.short_method.Mail;
import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.DetailedInvoiceRepository;
import com.poly.asm.dao.InvoiceRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.DetailedInvoice;
import com.poly.asm.model.Invoice;
import com.poly.asm.model.MailInfo2;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.MailerService2;
import com.poly.asm.service.SessionService;
import com.poly.asm.service.ShoppingCartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/shoeshop")
public class PayController {
	@Autowired
	SessionService session;
	@Autowired
	UserRepository dao; // user
	@Autowired
	ProductRepository Pdao; // sản phẩm

	@Autowired
	private IndexController indexController;

	@Autowired
	private ShoppingCartService cart;// gio hang
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private DetailedInvoiceRepository DetailedInvoiceRepository;
	@Autowired
	private MailerService2 mailerService2;

	@Autowired
	private Mail mail;

	@GetMapping("/thanhtoan")
	public String thanhtoan(@ModelAttribute("user") User user, Model model,
			@ModelAttribute("product") Product product) {
		indexController.checkUser(model);
		List<Product> products = new ArrayList<>(cart.getItems());
		List<Product> products2 = Pdao.findAll();
		List<Product> products3 = new ArrayList<>();
		double totalAmount = 0.0;
		for (Product p1 : products) {
			for (Product p2 : products2) {
				if (p1.getId().equalsIgnoreCase(p2.getId())) {
					p2.setQuantity(1);
					products3.add(p2);
					totalAmount += p2.getPrice();

				}
			}
		}

		model.addAttribute("cart", products3);

		// tạo biến Tổng ti lưu tạm trong modal
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("name", products);
		return "/views/thanhtoan";
	}

	public static String generateRandomNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 10; i++) {
			int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
			sb.append(digit);
		}

		return sb.toString();
	}

	@PostMapping("/thanhtoan")
	public String createPay(Model model, @ModelAttribute("product") Product product,
			@RequestParam(name = "itemQuantity", defaultValue = "false") Integer quantity, HttpSession sessionn) {
		indexController.checkUser(model);

		List<Product> products = new ArrayList<>(cart.getItems());
		List<Product> products2 = Pdao.findAll();
		List<Product> products3 = new ArrayList<>();
//		List này dùng đề cập nhật lại sản phẩm đã bán
		List<Product> products4 = Pdao.findAll();

		double totalAmount = 0.0;
//		Lấy time hiện tại

		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = localDate.format(formatter);

//Lấy time hiện tại nhưng bẳng DATE
		Date currentDate = new Date();
		User user = session.get("user");
		Invoice invoice = new Invoice();
		String yearString = formattedDate.replace("-", "");
		String hourString = String.valueOf(currentDate.getHours());
		String minuteString = String.valueOf(currentDate.getMinutes());
		String secondString = String.valueOf(currentDate.getSeconds());
		String userID = String.valueOf(user.getID());
		String result = yearString.concat(hourString).concat(minuteString).concat(secondString).concat(userID);
// invoice
		invoice.setId(result);
		invoice.setStatus("pending");
		invoice.setOrderDate(currentDate);
		invoice.setUser(user);
		invoiceRepository.save(invoice);

//		detail Invoice 
		for (Product p1 : products) {
			for (Product p2 : products2) {
				if (p1.getId().equalsIgnoreCase(p2.getId())) {
					products3.add(p2);
					totalAmount += p2.getPrice();

				}
			}
		}

		for (Product product3 : products3) {
			System.out.println(product3.getId());
			DetailedInvoice detailedInvoice = new DetailedInvoice();
			detailedInvoice.setProduct(product3);
			detailedInvoice.setInvoice(invoice);
			detailedInvoice.setQuantity(quantity);
			detailedInvoice.setPaymentMethod("Thanh toán khi nhận hàng");
			DetailedInvoiceRepository.save(detailedInvoice);
//			Cập nhật lại sản phẩm
			for (Product product4 : products4) {
				if (product3.getId().equals(product4.getId())) {
					product4.setQuantity(product4.getQuantity() - quantity);
					if (product4.getQuantity() <= 0) {
						product4.setStatus(false);
					} else {
						product4.setStatus(true);
					}

					Pdao.save(product4);
				}

			}
		}

//		Gửi mail

		MailInfo2 mailInfo2 = new MailInfo2();

		String body = "<html>" + "<head>" + "<style>" + "body { font-family: Arial, sans-serif; }"
				+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
				+ ".header { background-color: #FFC107; padding: 20px; text-align: center; }"
				+ ".header h2 { margin: 0; color: #fff; }" + ".content { background-color: #FFFFFF; padding: 20px; }"
				+ ".content p { color: #333; }" + ".content .invoice-info { margin-bottom: 10px; }"
				+ ".content .invoice-info strong { color: #FF5722; }"
				+ ".footer { background-color: #f8f8f8; padding: 20px; text-align: center; }"
				+ ".footer p { color: #777; }" + "</style>" + "</head>" + "<body>" + "<div class='container'>"
				+ "<div class='header'>" + "<h2>SHOE SHOP CODE</h2>" + "</div>" + "<div class='content'>"
				+ "<p>Cảm ơn bạn đã mua hàng!</p>" + "<p class='invoice-info'>Mã hóa đơn: <strong>" + invoice.getId()
				+ "</strong></p>" + "<p class='invoice-info'>Ngày thanh toán: <strong>" + currentDate + "</strong></p>"
				+ "<p>Phương thức thanh toán: <strong>Thanh toán khi nhận hàng</strong></p>" + "</div>"
				+ "<div class='footer'>" + "<p>Chi tiết lên hệ: 0829xxxxxxx.</p>" + "</div>" + "</div>" + "</body>"
				+ "</html>";
		;
		mail.setMail(user, body);
//		clear võ hàng
		cart.clear();
		sessionn.setAttribute("cartItemCount", 0);

		return "redirect:/shoeshop/viewpay/" + invoice.getId();
	}

//	 Hóa đơn
	@GetMapping("/viewpay/{id}")
	public String viewpay(@ModelAttribute("user") User user, Model model, @ModelAttribute("product") Product product,
			@PathVariable("id") String iDInvoice) {
		indexController.checkUser(model);
		double totalAmount = 0.0;
//		Optional<Invoice> invoice = invoiceRepository.findById(iDInvoice);
		List<DetailedInvoice> detailedInvoices = DetailedInvoiceRepository.findAll();
		List<DetailedInvoice> detailedInvoices2 = new ArrayList<>();
		for (DetailedInvoice detailedInvoice : detailedInvoices) {
			if (detailedInvoice.getInvoice().getId().equals(iDInvoice)) {
				detailedInvoices2.add(detailedInvoice);
			}
		}

		List<Product> products = productRepository.findByInvoiceId(iDInvoice);
		for (Product p1 : products) {
			totalAmount += p1.getPrice();
		}

		model.addAttribute("iDinvoice", iDInvoice);

		model.addAttribute("cart", detailedInvoices2);

		// tạo biến Tổng ti lưu tạm trong modal
		model.addAttribute("totalAmount", totalAmount);
		return "views/viewPay";
	}

}
