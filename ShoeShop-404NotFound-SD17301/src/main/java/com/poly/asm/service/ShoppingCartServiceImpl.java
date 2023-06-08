package com.poly.asm.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Product;

@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	private final ProductRepository productRepository;
	private final Map<String, Product> cartItems = new HashMap<>();

	@Autowired
	public ShoppingCartServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Product add(String id) {
		Optional<Product> optionalProduct = productRepository.findById(String.valueOf(id));
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			cartItems.put(product.getId(), product);
			return product;
		}
		return null;
	}

	@Override
	public void remove(String id) {
		cartItems.remove(String.valueOf(id));
	}

	@Override
	public Product update(String id, int qty) {

		String productId = String.valueOf(id);
		if (cartItems.containsKey(productId)) {
			Product product = cartItems.get(productId);
			product.setQuantity(qty);
			return product;
		}
		return null;
	}

	@Override
	public void clear() {
		cartItems.clear();
	}

	@Override
	public Collection<Product> getItems() {
		return cartItems.values();
	}

	@Override
	public int getCount() {
		return cartItems.size();
	}

	@Override
	public double getAmount() {
		double amount = 0;
		for (Product product : cartItems.values()) {
			amount += product.getPrice() * product.getQuantity();
		}
		return amount;
	}
}
