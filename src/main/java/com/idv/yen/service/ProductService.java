package com.idv.yen.service;

import com.idv.yen.domain.Product;

import java.util.List;

public interface ProductService {
    Boolean save(Product product);
    Boolean update(Product product);
    Boolean delete(Integer id);
    Product selectById(Integer id);
    List<Product> selectAll();
}
