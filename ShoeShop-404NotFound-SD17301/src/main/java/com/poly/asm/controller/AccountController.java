package com.poly.asm.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.poly.asm.config.short_method.Mail;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.MailInfo2;
import com.poly.asm.model.User;
import com.poly.asm.service.CookieService;
import com.poly.asm.service.MailerService2;
import com.poly.asm.service.ParamService;
import com.poly.asm.service.SessionService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop")
public class AccountController {

	@Value("${recaptcha.secretkey}")
	private String recaptchaSecretKey;

	@Autowired
	UserRepository dao; // dao của user

	@Autowired
	CookieService cookieService;

	@Autowired
	ParamService paramService;

	@Autowired
	SessionService sessionService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	SessionService session;

	@Autowired
	private MailerService2 mailerService2;

	@Autowired
	private Mail mail;

	@GetMapping("/login")
	public String login(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("failed", session.getString("mess"));
		user.setEmail(cookieService.getValue("email"));
		session.remove("mess");
		return "/account/login";
	}

	private boolean verifyRecaptcha(String recaptchaResponse) {
		// Gửi yêu cầu xác thực reCAPTCHA đến Google
		String url = "https://www.google.com/recaptcha/api/siteverify";
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("secret", recaptchaSecretKey);
		body.add("response", recaptchaResponse);

		ResponseEntity<Map> recaptchaResponseEntity = restTemplate.postForEntity(url, body, Map.class);
		Map<String, Object> responseBody = recaptchaResponseEntity.getBody();

		int statusCode = recaptchaResponseEntity.getStatusCodeValue();
		System.out.println(responseBody);
		System.out.println("Mã trạng thái: " + statusCode);

		// Kiểm tra kết quả từ Google reCAPTCHA
		boolean success = (Boolean) responseBody.get("success");

		return success;
	}

	@PostMapping("/login")
	public String loginCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model,
			@RequestParam(name = "remember", defaultValue = "false") boolean remember,
			@RequestParam(name = "g-recaptcha-response") String recaptchaResponse) {
		List<User> users = dao.findAll();
		String input = recaptchaResponse;
		String searchTerm = ",your_test_value";
		String result = input.replace(searchTerm, "");
//		System.out.println("Khóa trang web" + recaptchaResponse);
//		System.out.println("Khóa rs" + result);
//		System.out.println("Khóa bảo mật" + recaptchaSecretKey);

		// Kiểm tra reCAPTCHA
//		if (!verifyRecaptcha(result)) {
//			String errorMessage = "Vui lòng xác nhận bạn không phải là robot.";
//			System.out.println(errorMessage);
//			model.addAttribute("recaptchaError", errorMessage);
//			return "/account/login";
//		} else {
//
//		}

		if (result.equals("")) {
			String errorMessage = "Vui lòng xác nhận bạn không phải là robot.";
			System.out.println(errorMessage);
			model.addAttribute("failed", errorMessage);
			model.addAttribute("recaptchaError", errorMessage);
			return "/account/login";
		}

		if (remember) {
			cookieService.add("email", user.getEmail(), 10);
//			System.out.println(cookieService.getValue("email") + "cookie");
		} else {
			cookieService.remove("email");
		}
		for (User user2 : users) {
			if (user.getEmail().equalsIgnoreCase(user2.getEmail())) {
				if (user.getPassword().equalsIgnoreCase(user2.getPassword())) {
					session.set("user", user2, 30);
//					if (user2.isAdmin()) {
//						
////						User sUser = session.get("user");
////						System.out.println(sUser.getImage());
////						return "redirect:/shoeshop/admin/index";
//					} else {
//						session.set("user", user2, 30);
////						User sUser = session.get("user");
////						System.out.println(sUser.getImage());
////						return "redirect:/shoeshop/index";
//					}
				} else {
					String successMessage = "Tài khoản hoặc mật khẩu không chính xác?";
					model.addAttribute("failed", successMessage);
				}

			}
		}

		return "/account/login";
	}

	// trang signUp
	@GetMapping("/signUp")
	public String signUp(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("dk_user", user);

//		List<User> lits_user = dao.findAll();
//		model.addAttribute("lits_user", lits_user);

		user.setPhone("SĐT NUll");
		return "/account/signUp";
	}

	// trang signUp
	public static String generateRandomNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 9; i++) {
			int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
			sb.append(digit);
		}

		return sb.toString();
	}

	@PostMapping("/signUp")
	public String signUp(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model,
			@RequestParam("pw1") String pw1) {

		List<User> users = dao.findAll();
		if (rs.hasErrors()) {
			String successMessage = "create failed";
			model.addAttribute("failed", successMessage);
			System.out.println(rs.toString());
			return "/account/signUp";
		}

		for (User b : users) {
			if (b.getID().equalsIgnoreCase(user.getID())) {
				String successMessage = "ID đã tồn tại !";
				model.addAttribute("failed", successMessage);
				return "/account/signUp";
			}
		}
		for (User b : users) {
			if (b.getEmail().equalsIgnoreCase(user.getEmail())) {
				String successMessage = "gmail đã tồn tại !";
				model.addAttribute("failed", successMessage);
				return "/account/signUp";
			}
		}

		if (!user.getPassword().equalsIgnoreCase(pw1)) {
			String successMessage = "Mật khẩu không trùng nhau!";
			model.addAttribute("failed", successMessage);
			return "/account/signUp";
		}
		MailInfo2 mailInfo2 = new MailInfo2();
		sendCodeString = generateRandomNumber();

		String body = "<html>" + "<head>" + "<style>" + "body { font-family: Arial, sans-serif; }"
				+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
				+ ".header { background-color: #FF5722; padding: 20px; text-align: center; }"
				+ ".header h2 { margin: 0; color: #FFF; }" + ".content { background-color: #FFFFFF; padding: 20px; }"
				+ ".content p { color: #333; }"
				+ ".content h3 { color: #FFF; text-align: center; background-color: #000; padding: 10px; font-size: 24px; }"
				+ ".footer { background-color: #FF5722; padding: 20px; text-align: center; }"
				+ ".footer p { color: #FFF; }" + "</style>" + "</head>" + "<body>" + "<div class='container'>"
				+ "<div class='header'>" + "<h2>SHOE SHOP CODE</h2>" + "</div>" + "<div class='content'>"
				+ "<p>Đây là mã xác nhận của bạn:</p>"
				+ "<h3 style='background-color: #000; color: #FFF; text-align: center; font-size: 24px; padding: 10px;'>"
				+ sendCodeString + "</h3>" + "</div>" + "<div class='footer'>"
				+ "<p>Hãy nhập mã code để có thể lấy lại được mật khẩu.</p>" + "</div>" + "</div>" + "</body>"
				+ "</html>";
		mail.setMail(user, body);

//		mailInfo2.setFrom("Shoe Shop 404<khanhttpc03027@fpt.edu.vn>");
//		mailInfo2.setTo(user.getEmail());
//		mailInfo2.setSubject("SHOE SHOP CODE");
//		mailInfo2.setBody(body);
//		mailerService2.queue(mailInfo2);
		session.set("userSignUp", user, 5);
//		dao.save(user);

		return "redirect:/shoeshop/sendcodeSignUp";
	}

	@GetMapping("/sendcodeSignUp")
	public String sendCodeSignUp() {

		return "/account/send-code-signUp";
	}

	@PostMapping("/sendcodeSignUp")
	public String sendCodeSignUp(@RequestParam("code") String code, Model model) {
		if (sendCodeString.equals(code)) {
			User user = session.get("userSignUp");
			dao.save(user);
			session.remove("email");
			return "redirect:/shoeshop/login";
		}
		String errorMessage = ("Code bạn nhập không chính xác");
		model.addAttribute("failed", errorMessage);
		return "/account/send-code-signUp";
	}

	// trang logout
	@RequestMapping("/log-out")
	public String logOut() {
		session.remove("user");
		return "redirect:/shoeshop/index";
	}

	// trang kiểm tra password
	@RequestMapping("/ChangePass")
	public String ChangePass(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("ui_user", "active");

		return "/account/ChangePass";
	}

	// trang nhập mật khẩu mới confrimPassword
	@PostMapping("/ChangePass")
	public String ChangePassCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model,
			@RequestParam("password") String password) {
		User a = session.get("user");
		if (a.getPassword().equals(user.getPassword())) {
		
			System.out.println(user.getPassword());
			System.out.println("thành công");

			return "redirect:/shoeshop/ChangeRePass-Change";
		} else {
//			System.out.println("mật khẩu sai");
			String successMessage = "Mật khẩu không trùng nhau!";
			model.addAttribute("failed", successMessage);

		}

		return "/account/ChangePass";
	}

	// trang nhập mật khẩu mới
	@GetMapping("/ChangeRePass-Change")
	public String ChangeRePass(@ModelAttribute("user") User user, Model model) {
		User a = session.get("user");
		model.addAttribute("user", a);
		System.out.println("get" + "Sssssssssssssssssssssssssss");
		System.out.println(a.getEmail() + "email");
		return "/account/ChangeRePass";
	}

	// phương thức thay đổi mật khẩu mới
	@PostMapping("/ChangeRePass-Change")
	public String PassCheck(@Valid @ModelAttribute("user") User user,
			Model model, BindingResult result, @RequestParam("confirmpassword") String confirmpassword) {

		if (result.hasErrors()) {
			System.out.println(result.toString());
			return "/account/ChangeRePass-Change";
		}
		if (!user.getPassword().equalsIgnoreCase(confirmpassword)) {
			String successMessage = "Mật khẩu không trùng nhau! hoặc mật khẩu đã để trống!!";
			model.addAttribute("failed", successMessage);
			return "/account/ChangeRePass";
		}

		User defaultUser = new User();
		model.addAttribute("user", defaultUser);
		User a = session.get("user");

		System.out.println(user.getPassword() + "old");

		a.setPassword(user.getPassword());
		System.out.println(a.getID());
		System.out.println(a.getEmail());
		System.out.println(a.getName());
		System.out.println(a.getPassword());
		System.out.println(a.getPhone());
		dao.save(a);
		System.out.println("post" + "saveeeeeeeeeee");

		return "redirect:/shoeshop/index";
	}

	// trang nhập mật khẩu mới
	@GetMapping("/Forget")
	public String Forget(@ModelAttribute("user") User user) {

		return "/account/Forget";
	}

	// trang nhập mật khẩu mới
	private String sendCodeString = "";

	@PostMapping("/Forget")
	public String ForgetCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model)
			throws MessagingException {
//		User u = session.get("user");
		List<User> user2 = dao.findAll();
		for (User u : user2) {
			if (user.getEmail().equalsIgnoreCase(u.getEmail())) {

				MailInfo2 mailInfo2 = new MailInfo2();
				sendCodeString = generateRandomNumber();
				String body = "<html>" + "<head>" + "<style>" + "body { font-family: Arial, sans-serif; }"
						+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
						+ ".header { background-color: #FF5722; padding: 20px; text-align: center; }"
						+ ".header h2 { margin: 0; color: #FFF; }"
						+ ".content { background-color: #FFFFFF; padding: 20px; }" + ".content p { color: #333; }"
						+ ".content h3 { color: #FFF; text-align: center; background-color: #000; padding: 10px; font-size: 24px; }"
						+ ".footer { background-color: #FF5722; padding: 20px; text-align: center; }"
						+ ".footer p { color: #FFF; }" + "</style>" + "</head>" + "<body>" + "<div class='container'>"
						+ "<div class='header'>" + "<h2>SHOE SHOP CODE</h2>" + "</div>" + "<div class='content'>"
						+ "<p>Đây là mã xác nhận của bạn:</p>"
						+ "<h3 style='background-color: #000; color: #FFF; text-align: center; font-size: 24px; padding: 10px;'>"
						+ sendCodeString + "</h3>" + "</div>" + "<div class='footer'>"
						+ "<p>Hãy nhập mã code để có thể lấy lại được mật khẩu.</p>" + "</div>" + "</div>" + "</body>"
						+ "</html>";
				;
				;

				mail.setMail(user, body);
//				mailInfo2.setFrom("Shoe Shop 404<khanhttpc03027@fpt.edu.vn>");
//				mailInfo2.setTo(u.getEmail());
//				mailInfo2.setSubject("SHOE SHOP CODE");
////				mailInfo2.setBody("Đây là mã xác nhận của bạn: " + sendCodeString);
//				mailInfo2.setBody(body);
//				mailerService2.queue(mailInfo2);
				session.set("userForger", u, 30);

//			mailerService2.send("khanhttpc03027@fpt.edu.vn", "Subjectt", "123");
				return "redirect:/shoeshop/sendcode";
			}
		}

		String errorMessage = ("Email bạn nhập không đúng");
		model.addAttribute("failed", errorMessage);

		return "/account/Forget";
	}

	@GetMapping("/sendcode")
	public String sendCode() {

		return "/account/SendCode";
	}

	@PostMapping("/sendcode")
	public String sendCode(@RequestParam("code") String code, Model model) {
		if (sendCodeString.equals(code)) {
			return "/account/sendCodeChangePass";
		}
		String errorMessage = ("Code bạn nhập không chính xác");
		model.addAttribute("failed", errorMessage);
		return "/account/SendCode";
	}

	@RequestMapping("/sendcode-change")
	public String sendCodeChange(@ModelAttribute("user") User user, Model model) {

		if (user.getPassword().equals("")) {
			return "/account/sendCodeChangePass";
		}
		User u = session.get("userForger");

		u.setPassword(user.getPassword());
		dao.save(u);
		return "redirect:/shoeshop/login";

	}
}
