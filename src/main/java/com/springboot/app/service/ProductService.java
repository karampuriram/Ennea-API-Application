package com.springboot.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.Entity.Product;

public interface ProductService {
	
	String saveProducts(MultipartFile file) throws Exception;
	List<Product> getSuplierProductsInStock(String supplier, Integer page, Integer limit);
	List<Product> getSuplierProductsListFilteredWithProductName(String supplier, String name, Integer page, Integer limit);
	List<Product> getSuplierProductsListFilteredWithExpiry(String supplier, Boolean exp, Integer page, Integer limit) throws Exception;

	
	
}
