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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Categories")
public class Category {
//	@NotEmpty(message = "{NotEmpty.Category.id}")
	@Id
	private String id;

	@NotEmpty(message = "{NotEmpty.Category.name}")
	private String name;

	@NotEmpty(message = "{NotEmpty.Category.description}")
	private String description;

	@OneToMany(mappedBy = "category")
	List<Product> products;

	// Các trường khác và getter/setter
}
