package com.springboot.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.springboot.app.Entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{
	
	List<Product> findByStockGreaterThanAndSupplier(Integer stock, String supplier, Pageable pageable);
	List<Product> findByStockGreaterThanAndSupplierAndName(Integer stock, String supplier, String name, Pageable pageable);
	List<Product> findByStockGreaterThanAndExpGreaterThanAndSupplier(Integer stock,  Date Exp, String supplier, Pageable pageable);
	List<Product> findByStockGreaterThanAndExpLessThanAndSupplier(Integer stock,  Date Exp, String supplier, Pageable pageable);
}
