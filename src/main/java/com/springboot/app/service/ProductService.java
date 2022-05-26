package com.springboot.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.Entity.Product;

public interface ProductService {
	
	String saveProducts(MultipartFile file) throws IOException;
	List<Product> getSuplierProducts(String supplier);
	

	
	
}
