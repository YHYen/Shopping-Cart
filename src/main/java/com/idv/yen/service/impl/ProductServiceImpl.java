package com.idv.yen.service.impl;

import com.idv.yen.domain.Product;
import com.idv.yen.mapper.ProductMapper;
import com.idv.yen.service.ProductService;
import com.idv.yen.service.Utils.ImageUtil;
import com.idv.yen.service.Utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductMapper productMapper;
    private ImageUtil imageUtil;
    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     *  add product: upload the image first, then insert product data into the database
     * @param product product object containing product information
     * @param file product image file
     * @return Result whether the product is added successfully and process message
     * */
    @Override
    public Result addProduct(MultipartFile file, Product product) {
        // 1. use image util to upload product image
        imageUtil = new ImageUtil();
        Result uploadResult = imageUtil.imageUpload(file, "/src/main/resources/static/images/product/");

        // 2. determine whether product uploaded successfully
        if (uploadResult.getFlag()) {
            // 3. upload successful, insert product information to database
            // 3.1 set product image path
            product.setImagePath(uploadResult.getMessage());

            // 3.2 insert data and determine insertion result
            if (productMapper.insertProduct(product) > 0) {
                // 4. insert data successful, return successful message
                return new Result(true, "Product added successfully");
            }
            // 3.3. Failed to insert data into database, return error message
            return new Result(false, "Failed to add Product");
        }
        // 2.2 Fail to upload image, return uploadResult with error message
        return uploadResult;
    }

    /**
     * delete product data by the product id
     * @param id the id of the product to delete
     * @return Result whether the product deleted successfully and process message
     * */
    @Override
    public Result deleteProduct(Integer id) {
        if (productMapper.deleteById(id) > 0) {
            return new Result(true, "Product deleted successfully");
        }
        return new Result(false, "Failed to delete Product");
    }

    /**
     * update product data by the product object
     * @param product product object containing product information
     * @return Result whether the product data is updated successfully and process message
     * */
    @Override
    public Result updateProduct(Product product) {
        if (productMapper.updateById(product) > 0) {
            return new Result(true, "Product updated successfully");
        }
        return new Result(false, "Failed to update Product");
    }

    /**
     * find all product information
     * @return Result  whether the products data is founded successfully,
     *         Data object containing all products information in List and process message
     * */
    @Override
    public Result selectAll() {
        // 1. get all products information from database
        List<Product> products = productMapper.selectAll();

        // 2. determine whether there is data in the database
        if (products.size() == 0) {
            // 2.1. if there is no data in the database, return false and process message
            return new Result(false, null, "No products found!");
        }


        // 3. traverse products to get product image path
        for (Product product : products) {
            // 3.1. get product image path
            String imagePath = product.getImagePath();

            // 3.2. get product base64Image with imageUtil
            imageUtil = new ImageUtil();
            String base64Image = imageUtil.getBase64Image(imagePath);

            // 3.3. set product base64Image to product path
            product.setImagePath(base64Image);
        }


        return new Result(true, products, "Products found successfully");
    }

    /**
     * find product information by product id
     * @param id the id of the product to find
     * @return Result whether the product data is founded successfully,
     *         Data object containing product information and process message
     * */
    @Override
    public Result selectById(Integer id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            return new Result(false, null, "Product does not exist");
        }
        return new Result(true, product, "Product found successfully");
    }

    /**
     * find product information by seller id
     * @param sellerId the id of the seller to find
     * @return Result whether the product data is founded successfully,
     *         Data object containing all products information of seller in List and process message
     * */
    @Override
    public Result selectBySellerId(Integer sellerId) {
        // 1. get all products information from database
        List<Product> products = productMapper.selectBySellerId(sellerId);
        // 2. determine whether there is data in the database
        if (products.size() == 0) {
            // 2.1. if there is no data in the database, return false and process message
            return new Result(false, null, "No products found!");
        }


        // 3. traverse products to get product image path
        for (Product product : products) {
            // 3.1. get product image path
            String imagePath = product.getImagePath();

            // 3.2. get product base64Image with imageUtil
            imageUtil = new ImageUtil();
            String base64Image = imageUtil.getBase64Image(imagePath);

            // 3.3. set product base64Image to product path
            product.setImagePath(base64Image);
        }

        return new Result(true, products, "Products found successfully");
    }

}
