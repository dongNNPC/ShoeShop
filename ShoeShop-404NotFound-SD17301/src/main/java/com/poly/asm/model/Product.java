package com.poly.asm.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
	@Id
	private String id;
	private String name;
	private int quantity;
	private float price;
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

	// Các trường khác và getter/setter
}
