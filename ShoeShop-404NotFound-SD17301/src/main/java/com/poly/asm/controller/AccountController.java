package com.poly.asm.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.dao.support.DaoSupport;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;

import com.poly.asm.service.ParamService;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.User;
import com.poly.asm.service.CookieService;
import com.poly.asm.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop")
public class AccountController {

	@Autowired
	UserRepository dRepository; //dao của user
	
	@Autowired
	CookieService cookieService;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	HttpServletRequest request;
	
//	@Autowired
//	private UserRepository userRepository;

	
//	//text xem dữ liệu có lên không
//		@ResponseBody
//		@RequestMapping("/loadDuLieu")
//		public List<User> load() {
//			return dUserRepository.findAll();
//		}
	// trang đăng nhập
	@RequestMapping("/login")
	public String login(@ModelAttribute("user") User user, Model model) {
		
		
		model.addAttribute("ui_user", "active");
//		model.addAttribute("user", user);
//		List<User> users = dRepository.findAll();
//		model.addAttribute("users", users);
		return "/account/login";
	}	
	@PostMapping("/login")
	public String loginCheck( @Valid @ModelAttribute("user")User user,BindingResult rs, Model model) {
		
		model.addAttribute("user", user);
		List<User> users = dRepository.findAll();
		model.addAttribute("user", user);		
				
		String em = request.getParameter("email");
		String pw = request.getParameter("password");
		boolean rm = paramService.getBoolean("remember", false);
	
		if (user.getEmail().equalsIgnoreCase(em) && user.getPassword().equalsIgnoreCase(pw)) {
		
			model.addAttribute("user", user);
			//cookie
			if (rm == true) {
			cookieService.add("user", em, 10);
			
			model.addAttribute("user", cookieService.getValue("user"));
		}else {
			cookieService.remove(em);
		}			
		//chuyển admin hoặc user
//dùng phương thức for
			try {
				for(int  i = 0 ;i < dRepository.findAll().size() ;i ++) {
					if (dRepository.findAll().get(i).getEmail().equals(em)) {
						if (dRepository.findAll().get(i).getPassword().equals(pw)) {
							if (dRepository.findAll().get(i).isAdmin() == true) { // xét phân quyền
								return "redirect:/shoeshop/admin/index"; // trang admin
							}else {
								return "redirect:/shoeshop/index"; //trang index
							}
						}else {
							System.out.println("mật khẩu không đúng");
						}
					}else {
						System.out.println("không tồn tại");
					}
				}
			} catch (Exception e) {
				System.out.println("thành công");
			}

		}
	return "/account/login";
	}

	// trang signUp
	@GetMapping("/signUp")
	public String signUp(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("dk_user", user);
//		List<User> lits_user = dRepository.findAll();
//		model.addAttribute("lits_user", lits_user);
		
		return "/account/signUp";
	}

	// trang signUp
	
	@PostMapping("/signUp")
	public String signUp(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		
		
		user.setPhone(null);
		user.setAddress(null);
		
		dRepository.save(user);
//		
//		model.addAttribute("user", user);
//		List<User> users = dRepository.findAll();
//		model.addAttribute("lits_user", users);		
////		
//		String um = request.getParameter("name");
//		String em = request.getParameter("email");
//		String pw = request.getParameter("password");
////		String cp = request.getParameter("confirm password");
////		
//		 
//	//	user.getEmail().equalsIgnoreCase(em)
//	 if (user.getName().equals(user.getName()) && user.getEmail().equals(user.getEmail()) && user.getPassword().equals(user.getPassword())) {
//		try {
//			for(int  i = 0 ;i < dRepository.findAll().size() ;i ++) {
//				if ( !dRepository.findAll().get(i).getName().equals(user.getName())
//				&& !dRepository.findAll().get(i).getEmail().equals(user.getEmail())	) {
////				
////					user.setName(user.getName());
////					user.setEmail(user.getEmail());
////				user.setPassword(user.getPassword());
////				
//				user.setAdmin(false);
//				System.out.println(user.getID());
//				System.out.println(user.getName());
//				System.out.println(user.getEmail());
//				System.out.println(user.getPassword());
//					dRepository.save(user); // thêm
//					System.out.println("thành công");
//				return "redirect:/account/login";					
//				}			
//			}
//			
//	} catch (Exception e) {
//		System.out.println(e);
//	}
//		
//	}
//		
//		return "/account/signUp";
		return "redirect:/shoeshop/login";
	}

	// trang kiểm tra password
	@RequestMapping("/ChangePass")
	public String ChangePass(@ModelAttribute("user") User user) {
		return "/account/ChangePass";
	}

	// trang nhập mật khẩu mới
	@PostMapping("/ChangePass")
	public String ChangePassCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		if (user.getPassword().equalsIgnoreCase("123")) {

			return "redirect:/shoeshop/ChangeRePass";
		}
		return "/account/ChangePass";
	}

	// trang nhập mật khẩu mới
	@RequestMapping("/ChangeRePass")
	public String ChangeRePass() {
		return "/account/ChangeRePass";
	}

	// trang nhập mật khẩu mới
	@RequestMapping("/Forget")
	public String Forget(@ModelAttribute("user") User user) {
		return "/account/Forget";
	}

	// trang nhập mật khẩu mới
	@PostMapping("/Forget")
	public String ForgetCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		if (user.getEmail().equalsIgnoreCase("user@gmail.com")) {

			return "redirect:/shoeshop/ChangeRePass";
		}
		return "/account/Forget";
	}
}
