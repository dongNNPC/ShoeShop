package com.poly.asm.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {
	
	@Id
	String id ;
	String name;
	double quantity ;
	double price ;
}
