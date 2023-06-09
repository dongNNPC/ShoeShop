package com.poly.asm.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class reportPieChart {
	
	@Id
	String id ;
	
	long TotalQuantityAdidas ;
	String BrandNameAdidas;
	
	long TotalQuantityGucci ;
	String BrandNameGucci;
	
	long TotalQuantityNike ;
	String BrandNameNike;
}
