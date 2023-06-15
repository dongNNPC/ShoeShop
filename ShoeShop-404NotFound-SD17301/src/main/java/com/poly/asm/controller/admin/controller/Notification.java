package com.poly.asm.controller.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.controller.IndexAdminController;
import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.MailInfo2;
import com.poly.asm.model.User;
import com.poly.asm.service.MailerService2;

@Controller
@RequestMapping("/shoeshop/admin")
public class Notification {
	@Autowired
	private IndexController indexController;
	@Autowired
	private IndexAdminController indexAdminController;

	@Autowired
	private MailerService2 mailerService2;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/notification")
	public String mail(Model model, @RequestParam(name = "body", required = false) String info) {
		indexController.checkUser(model);
		indexAdminController.notification(model);
		MailInfo2 mailInfo2 = new MailInfo2();
		List<User> users = userRepository.findAll();
		if (info != null) {
			for (User user : users) {
				String body = "<html>" + "<head>" + "<style>" + "body { font-family: Arial, sans-serif; }"
						+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
						+ ".header { background-color: #FFC107; padding: 20px; text-align: center; }"
						+ ".header h2 { margin: 0; color: #fff; }"
						+ ".content { background-color: #FFFFFF; padding: 20px; }" + ".content p { color: #333; }"
						+ ".content .invoice-info { margin-bottom: 10px; }"
						+ ".content .invoice-info strong { color: #FF5722; }"
						+ ".footer { background-color: #f8f8f8; padding: 20px; text-align: center; }"
						+ ".footer p { color: #777; }" + "</style>" + "</head>" + "<body>" + "<div class='container'>"
						+ "<div class='header'>" + "<h2>SHOE SHOP CODE</h2>" + "</div>" + "<div class='content'>"
						+ "<p><strong>Thông tin đến tất cả khách hàng</strong></p>"
						+ "<p class='invoice-info'>Xin chào: <strong>" + user.getName() + "</strong></p>"
						+ "<p class='invoice-info'>Thông báo: <strong>" + info + "</strong></p>" + "" + "</div>"
						+ "<div class='footer'>" + "<p>Chi tiết lên hệ: 0829xxxxxxx.</p>" + "</div>" + "</div>"
						+ "</body>" + "</html>";
				mailInfo2.setFrom("Shoe Shop 404<khanhttpc03027@fpt.edu.vn>");
				mailInfo2.setTo(user.getEmail());
				mailInfo2.setSubject("SHOE SHOP CODE");
				mailInfo2.setBody(body);
				mailerService2.queue(mailInfo2);
				// Delay trong 1 giây (1000 milliseconds)
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		return "admin/views/notification";
	}
}
