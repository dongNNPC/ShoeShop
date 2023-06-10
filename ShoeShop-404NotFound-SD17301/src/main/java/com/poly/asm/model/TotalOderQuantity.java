package com.poly.asm.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalOderQuantity {
	
	
	@Id
	String id;
	String userName;
	String productName;
	long quantity;
}
