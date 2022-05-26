package com.springboot.app.controller;

import java.util.List;
import java.util.ArrayList;
import java.io.Console;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.springboot.app.Entity.Product;
import com.springboot.app.repository.ProductRepository;
import com.springboot.app.service.impl.ProductServiceImpl;
import com.univocity.parsers.common.record.Record;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	
	@PostMapping("/upload")
	public String uploadData(@RequestParam("file") MultipartFile file) throws Exception
	{
		//ProductServiceImpl productServiceImpl = new ProductServiceImpl(productRepository);
		return productServiceImpl.saveProducts(file);
	}
	
	@GetMapping("/suplier_products/{supplier}")
	public List<Product> getSuplierProductsList(@PathVariable("supplier") String supplier)
	{
		return productServiceImpl.getSuplierProducts(supplier);
	}			
}
