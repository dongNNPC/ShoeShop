package com.poly.asm.model;

import java.util.Date;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOderPayment {
	
	@Id
	String id;
	String name;
	Date purchaseDate;
	Date deliveryDate;
	String status;
	String payment;

}
