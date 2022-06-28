package com.springboot.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.Entity.Product;
import com.springboot.app.repository.ProductRepository;
import com.springboot.app.service.impl.ProductServiceImpl;
import com.springboot.app.validator.CsvFileValidator;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@PostMapping("/upload")
	public ResponseEntity uploadData(@RequestParam("file") MultipartFile file) throws Exception
	{
		CsvFileValidator csvFileValidator = new CsvFileValidator();
		if(!csvFileValidator.isTheFileValied(file))
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		productServiceImpl.saveProducts(file);
		return ResponseEntity.ok().build();
	}
	//Testing
	@RequestMapping(value="/suplier_products/{supplier}", method = RequestMethod.GET)
	public List<Product> getSuplierProductsList(@PathVariable("supplier") String supplier,
			@RequestParam(value="product_name", required = false) String name,
			@RequestParam(value="expired", required = false) Boolean exp,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "100") Integer limit) throws Exception
	{
		if(name != null)
		return productServiceImpl.getSuplierProductsListFilteredWithProductName(supplier, name, page, limit);
		else if(exp != null)
		return	productServiceImpl.getSuplierProductsListFilteredWithExpiry(supplier, exp, page, limit);
		else
		return productServiceImpl.getSuplierProductsInStock(supplier, page, limit);
	}
}
