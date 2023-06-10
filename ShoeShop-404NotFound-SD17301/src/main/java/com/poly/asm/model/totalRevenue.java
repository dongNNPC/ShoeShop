package com.poly.asm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class totalRevenue {

	@Id
	String id;
	String name;
	double price;
	double quantity;
	double total;
}
