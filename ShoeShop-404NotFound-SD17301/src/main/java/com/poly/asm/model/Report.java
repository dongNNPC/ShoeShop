package com.poly.asm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
	@Id
	String id ;
	long totalQuantity ;
	long totalInventory;
	double totalRevenue;
	long totalODer;
}
