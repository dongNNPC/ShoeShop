package com.poly.asm.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Invoices")
public class Invoice {
	@Id
	private String id;
	private Date orderDate;
	private String status;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "invoice")
	List<DetailedInvoice> detailedInvoices;
	

	// Các trường khác và getter/setter
}
