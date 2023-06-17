package com.poly.asm.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
	@Id
//	@Size(min = 4, message = "{Size.Product.id}")
//	@NotEmpty(message = "{NotEmpty.Product.id}")
	private String id;
	@NotEmpty(message = "{NotEmpty.Product.name}")
	private String name;

	@NotNull(message = "{NotNull.Product.quantity}")
	private int quantity = 0;

	@NotNull(message = "{NotNull.Product.price}")
	private float price;

	@NotEmpty(message = "{NotEmpty.Product.description}")
	private String description;

	private boolean status;

	@ManyToOne
	private Category category;

	@ManyToOne
	private Brand brand;

	@OneToMany(mappedBy = "product")
	List<DetailedInvoice> detailedInvoices;

	@OneToMany(mappedBy = "product")
	List<Cart> carts;

	@OneToMany(mappedBy = "product")
	List<DetailedImage> detailedImages;

	// Getter và Setter cho các thuộc tính
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<DetailedInvoice> getDetailedInvoices() {
		return detailedInvoices;
	}

	public void setDetailedInvoices(List<DetailedInvoice> detailedInvoices) {
		this.detailedInvoices = detailedInvoices;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public List<DetailedImage> getDetailedImages() {
		return detailedImages;
	}

	public void setDetailedImages(List<DetailedImage> detailedImages) {
		this.detailedImages = detailedImages;
	}

}
