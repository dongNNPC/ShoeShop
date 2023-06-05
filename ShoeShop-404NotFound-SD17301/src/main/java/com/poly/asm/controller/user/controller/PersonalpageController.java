package com.poly.asm.controller.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.model.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop")
public class PersonalpageController {
	
	@GetMapping("/personal-page")
	public String Personal(@ModelAttribute("user") User user, Model model) {
		return "/account/personalpage";
		
	}

	@PostMapping("/personal-page")
	public String PersonalCheck(@Valid @ModelAttribute("user") User user, Model model) {
		
		
		return "/account/personalpage";
		
		
	}
}
