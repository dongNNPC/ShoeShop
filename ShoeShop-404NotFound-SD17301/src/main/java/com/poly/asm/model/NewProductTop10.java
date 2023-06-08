package com.poly.asm.model;

import java.util.Date;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class NewProductTop10 {
	@Id
	String id;
	String name ;
	int quantity ;
	float price ;
	String description ;
	Date orderDate;
	String brandName ;
	String image; 
}
