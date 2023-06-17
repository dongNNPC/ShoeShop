package com.poly.asm.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Brands")
public class Brand {
	@Id
//	@NotEmpty(message = "{NotEmpty.Brand.id}")
	private String id;

	@NotEmpty(message = "{NotEmpty.Brand.name}")
	private String name;

	@NotEmpty(message = "{NotEmpty.Brand.address}")
	private String address;

	@OneToMany(mappedBy = "brand")
	List<Product> products;

	// Các trường khác và getter/setter
}
