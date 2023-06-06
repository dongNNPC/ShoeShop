package com.poly.asm.controller.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.dao.StockReceiptRepository;
import com.poly.asm.model.ProductInventory;
import com.poly.asm.model.UserOrderCountDTO;



@Controller
@RequestMapping("/shoeshop")
public class Report_ProductInventoryController {
	
	@Autowired StockReceiptRepository dao;
	
//	
}
