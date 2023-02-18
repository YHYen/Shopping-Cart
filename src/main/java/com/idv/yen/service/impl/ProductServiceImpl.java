package com.idv.yen.service.impl;

import com.idv.yen.domain.Product;
import com.idv.yen.mapper.ProductMapper;
import com.idv.yen.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    final ProductMapper productMapper;
    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     *  add product
     * @param product product object containing product information
     * @return Boolean whether the product is added successfully
     * */
    @Override
    public Boolean save(Product product) {
        return productMapper.insertProduct(product) > 0;
    }

    /**
     * update product data by the product object
     * @param product product object containing product information
     * @return Boolean whether the product data is updated successfully
     * */
    @Override
    public Boolean update(Product product) {
        return productMapper.updateById(product) > 0;
    }

    /**
     * delete product data by the product id
     * @param id the id of the product to delete
     * @return Boolean whether the product deleted successfully
     * */
    @Override
    public Boolean delete(Integer id) {
        return productMapper.deleteById(id) > 0;
    }

    /**
     * find product information by product id
     * @param id the id of the product to find
     * @return Product object containing product information
     * */
    @Override
    public Product selectById(Integer id) {
        return productMapper.selectById(id);
    }

    /**
     * find all product information
     * @return List<Product> List containing all product information
     * */
    @Override
    public List<Product> selectAll() {
        return productMapper.selectAll();
    }


}
