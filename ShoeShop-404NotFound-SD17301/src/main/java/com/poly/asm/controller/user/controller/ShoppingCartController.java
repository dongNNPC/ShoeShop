package com.poly.asm.controller.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;
import com.poly.asm.service.ShoppingCartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShoppingCartController {
	@Autowired
	ShoppingCartService cart; // Tiêm Spring Bean đã viết ở bài trước

	@Autowired
	ProductRepository dao;

	@Autowired
	DetailedImageRepository daoIMG;

	@Autowired
	SessionService session;

	@Autowired
	DetailedImageRepository daoDetailedImageRepository;

	@Autowired
	private IndexController indexController;

	@RequestMapping("/shoeshop/cart/view")
	public String viewCart(Model model, @ModelAttribute("user") User user) {

		indexController.checkUser(model);

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
	public String addItemToCart(@PathVariable("id") String id, RedirectAttributes redirectAttributes, Model model,
			HttpSession session) {
		Product product = cart.add(id);
		List<Product> products = dao.findAll();
		List<DetailedImage> de = daoIMG.findAll();

		List<DetailedImage> detailedImages = daoDetailedImageRepository.findAll();
		Product p2 = new Product();
		for (Product p : products) {
			if (p.getId().equals(id)) {
				model.addAttribute("p", p);
				for (DetailedImage d : detailedImages) {
					if (p.getId().equalsIgnoreCase(d.getProduct().getId())) {
						model.addAttribute("imagePath", d.getMainImage());
					}
				}
			}
		}

		boolean addedToCart = false; // biến xác định thêm vào giỏ hàng hay chưa

		if (product != null) {

			addedToCart = true; // Gán giá trị true cho biến cờ khi sản phẩm được thêm vào giỏ hàng
			String name = product.getName();
			session.setAttribute("successMessage", "Đã thêm sản phẩm " + name + "  vào giỏ hàng!");

			// Lưu số lượng sản phẩm vào session
			int cartItemCount = session.getAttribute("cartItemCount") != null
					? (int) session.getAttribute("cartItemCount")
					: 0;
			session.setAttribute("cartItemCount", cartItemCount + 1);
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to add item to cart");
		}

		model.addAttribute("addedToCart", addedToCart); // Truyền giá trị của biến cờ vào view

		return "redirect:/shoeshop/index";
	}

	// sử lý load trang
	@RequestMapping("/clear-success-message")
	public void clearSuccessMessage(HttpSession session) {
		session.removeAttribute("successMessage");
	}

	// button xoá từng sản phẩm
	@RequestMapping("/cart/remove/{id}")
	public String removeItemFromCart(@PathVariable("id") String id, RedirectAttributes redirectAttributes,
			HttpSession session) {
		cart.remove(id);
		redirectAttributes.addFlashAttribute("successMessage", "Removed item from cart successfully");

		// Giảm số lượng sản phẩm trong giỏ hàng trong session đi 1
		int cartItemCount = session.getAttribute("cartItemCount") != null ? (int) session.getAttribute("cartItemCount")
				: 0;
		if (cartItemCount > 0) {
			session.setAttribute("cartItemCount", cartItemCount - 1);
		}

		return "redirect:/shoeshop/cart/view";
	}

	// button xoá tất cả
	@RequestMapping("/cart/clear")
	public String clearCart(RedirectAttributes redirectAttributes, HttpSession session) {
		cart.clear();
		session.setAttribute("cartItemCount", 0);

		redirectAttributes.addFlashAttribute("successMessage", "Cleared cart successfully");
		return "redirect:/shoeshop/cart/view";
	}

	// sử lý cập nhật
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

}
