package com.poly.asm.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_receipts")
public class StockReceipt {
	@Id
	private String id;

	private Date orderDate;
	@ManyToOne
	private Brand brand;

	@ManyToOne
	private Product product;

	private int quantity;
	private float price;

	// Các trường khác và getter/setter
}
