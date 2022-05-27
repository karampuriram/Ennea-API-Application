package com.springboot.app.service.impl;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


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
	public String saveProducts(MultipartFile file) throws Exception{
		
		List<Product> productList = new ArrayList<>();
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(setting);
		List<Record> parseRecords = parser.parseAllRecords(inputStream);
		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
		parseRecords.forEach(record -> {
			Product product = new Product();
			product.setCode(record.getString("code"));
			product.setName(record.getString("name"));
			product.setBatch(record.getString("batch"));
			product.setStock(Integer.parseInt(record.getString("stock")));
			product.setDeal(record.getString("deal"));
			product.setFree(record.getString("free"));
			product.setMrp(record.getString("mrp"));
			product.setRate(record.getString("rate"));
			try {
				product.setExp(sdformat.parse(record.getString("exp")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			product.setCompany(record.getString("company"));
			product.setSupplier(record.getString("supplier"));
			productList.add(product);
		});
		productRepository.saveAll(productList);
		return "Upload Succesful";
	}

	@Override
	public List<Product> getSuplierProductsInStock(String supplier, Integer page, Integer limit) {
		Pageable pages = PageRequest.of(page, limit);
		return productRepository.findByStockGreaterThanAndSupplier(0, supplier, pages);
	}

	@Override
	public List<Product> getSuplierProductsListFilteredWithProductName
		(String supplier, String name, Integer page, Integer limit) {
		Pageable pages = PageRequest.of(page, limit);
			return productRepository.findByStockGreaterThanAndSupplierAndName(0, supplier, name, pages);
	}

	@Override
	public List<Product> getSuplierProductsListFilteredWithExpiry(String supplier, Boolean exp, Integer page,
			Integer limit) throws Exception {
		Pageable pages = PageRequest.of(page, limit);
		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
		Date currentDate = sdformat.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		return exp ? productRepository.findByStockGreaterThanAndExpLessThanAndSupplier(0, currentDate, supplier, pages):
			productRepository.findByStockGreaterThanAndExpGreaterThanAndSupplier(0, currentDate, supplier, pages)	;
	}

	
	
}
