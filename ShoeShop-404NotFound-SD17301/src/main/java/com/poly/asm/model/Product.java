package com.poly.asm.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@Size(min = 4, message = "{Size.Product.id}")
	@NotEmpty(message = "{NotEmpty.Product.id}")
	private String id;
	@NotEmpty(message = "{NotEmpty.Product.name}")
	private String name;

	@NotNull(message = "{NotNull.Product.quantity}")
	private int quantity;

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
}
