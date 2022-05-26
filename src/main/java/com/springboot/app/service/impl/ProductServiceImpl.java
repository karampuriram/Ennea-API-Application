package com.springboot.app.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.Entity.Product;
import com.springboot.app.repository.ProductRepository;
import com.springboot.app.service.ProductService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Service
@Configuration
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	@Override
	public String saveProducts(MultipartFile file) throws IOException{
		
		List<Product> productList = new ArrayList<>();
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(setting);
		List<Record> parseRecords = parser.parseAllRecords(inputStream);
		parseRecords.forEach(record -> {
			Product product = new Product();
			product.setCode(record.getString("code"));
			product.setName(record.getString("name"));
			product.setBatch(record.getString("batch"));
			product.setStock(record.getString("stock"));
			product.setDeal(record.getString("deal"));
			product.setFree(record.getString("free"));
			product.setMrp(record.getString("mrp"));
			product.setRate(record.getString("rate"));
			product.setExp(record.getString("exp"));
			product.setCompany(record.getString("company"));
			product.setSupplier(record.getString("supplier"));
			productList.add(product);
		});
		productRepository.saveAll(productList);
		return "Upload Succesful";
	}

	@Override
	public List<Product> getSuplierProducts(String supplier) {
		return productRepository.findBySupplier(supplier);	
	}


	
}
