package com.poly.asm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailTotalQuantity {
	@Id
	String id ;
	String name ;
	float quantity ;
	float price ;
}
