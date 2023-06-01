package com.poly.asm.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "Detailedimages")
public class DetailedImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_image;
	@ManyToOne
	private Product product;
	private String mainImage;
	private String detailedOne;
	private String detailedTwo;
	private String detailedThree;

	// Các trường khác và getter/setter
}
