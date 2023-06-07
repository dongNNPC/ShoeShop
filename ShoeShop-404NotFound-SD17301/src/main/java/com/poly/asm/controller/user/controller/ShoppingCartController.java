package com.poly.asm.controller.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;
import com.poly.asm.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
	@Autowired
	private ShoppingCartService cart; // Tiêm Spring Bean đã viết ở bài trước

	@Autowired
	ProductRepository dao;
	@Autowired
	DetailedImageRepository daoIMG;
	@Autowired
	SessionService session;
	@Autowired
	DetailedImageRepository daoDetailedImageRepository;

	@RequestMapping("/shoeshop/cart/view")
	public String viewCart(Model model, @ModelAttribute("user") User user) {

		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");

			model.addAttribute("user", user);
		}

//		model.addAttribute("cart", cart);

		List<Product> products = new ArrayList<>(cart.getItems());
		List<Product> products2 = dao.findAll();
		List<Product> products3 = new ArrayList<>();
		for (Product p : products) {
			for (Product p2 : products2) {
				if (p.getId().equalsIgnoreCase(p2.getId())) {
					products3.add(p2);
				}
			}
		}

		model.addAttribute("cart", products3);

		// Check nếu cart rỗng sẽ ẩn đi button clear cart
		boolean hasProducts = !cart.getItems().isEmpty();
		model.addAttribute("hasProducts", hasProducts);

		return "/views/giohang";
	}

	@RequestMapping("/cart/add/{id}")
	public String addItemToCart(@PathVariable("id") String id, RedirectAttributes redirectAttributes, Model model) {
		Product product = cart.add(id);
		List<Product> products = dao.findAll();
		List<DetailedImage> de = daoIMG.findAll();

		List<DetailedImage> detailedImages = daoDetailedImageRepository.findAll();
		Product p2 = new Product();
		for (Product p : products) {
			if (p.getId().equals(id)) {
//				System.out.println(p.getDetailedImages());
				model.addAttribute("p", p);
				for (DetailedImage d : detailedImages) {
					if (p.getId().equalsIgnoreCase(d.getProduct().getId())) {
						model.addAttribute("imagePath", d.getMainImage());

					}
				}

			}
		}

		if (product != null) {
			redirectAttributes.addFlashAttribute("successMessage", "Thành công!");
			model.addAttribute("item", p2);
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to add item to cart");
		}

		return "redirect:/shoeshop/index";
	}

	@RequestMapping("/cart/remove/{id}")
	public String removeItemFromCart(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		cart.remove(id);
		redirectAttributes.addFlashAttribute("successMessage", "Removed item from cart successfully");
		return "redirect:/shoeshop/cart/view";
	}

	@RequestMapping("/cart/update/{id}")
	public String updateItemQuantityInCart(@PathVariable("id") String id, @RequestParam("qty") int qty,
			RedirectAttributes redirectAttributes) {
		Product product = cart.update(id, qty);
		if (product != null) {
			redirectAttributes.addFlashAttribute("successMessage", "Updated item quantity in cart successfully");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to update item quantity in cart");
		}
		return "redirect:/shoeshop/cart/view";
	}

	@RequestMapping("/cart/clear")
	public String clearCart(RedirectAttributes redirectAttributes) {
		cart.clear();
		redirectAttributes.addFlashAttribute("successMessage", "Cleared cart successfully");
		return "redirect:/shoeshop/cart/view";
	}

	@ResponseBody
	@PostMapping("/cart/item/update")
	public Product updateCardItem(@RequestBody Product item) {
		// Process the data received via AJAX
		Product updatedItem = cart.update(item.getId(), item.getQuantity());
		return updatedItem;
	}
}
