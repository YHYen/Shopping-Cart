package com.idv.yen.service.impl;

import com.idv.yen.domain.Product;
import com.idv.yen.mapper.ProductMapper;
import com.idv.yen.service.ProductService;
import com.idv.yen.service.Utils.ImageUtil;
import com.idv.yen.service.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private ImageUtil imageUtil;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * add product: upload the image first, then insert product data into the database
     *
     * @param file    product image file
     * @param product product object containing product information
     * @return Result whether the product is added successfully and process message
     */
    @Override
    public Result addProduct(MultipartFile file, Product product) {
        // 1. use image util to upload product image
        System.out.println(product);
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
        // 2.1 Fail to upload image, return uploadResult with error message
        return uploadResult;
    }

    /**
     * delete product data by the product id
     *
     * @param id the id of the product to delete
     * @return Result whether the product deleted successfully and process message
     */
    @Override
    public Result deleteProduct(Integer id) {
        // 1. select Product by product id to get image path
        Product originProduct = productMapper.selectById(id);

        // 2. Determine the product exists or not
        if(originProduct == null) {
            // 2.1. if product does not exist
            return new Result(false, "Product does not exist");
        }

        // 3. select successful, delete product information from table
        try {
            if (productMapper.deleteById(originProduct.getId()) > 0) {
                // 3. use image util to delete product image
                imageUtil = new ImageUtil();
                imageUtil.imageDelete(originProduct.getImagePath());

                // 5. delete data successful, return successful message
                return new Result(true, "Product deleted successfully");
            }
            // 4.1. Failed to delete data into database, return error message
            return new Result(false, "Failed to delete Product");
        } catch (Exception e) {
            return new Result(false, "Your item has already been purchased and cannot be deleted");
        }

    }

    /**
     * update product data by the product object and update
     *
     * @param file    product object containing product information
     * @param product product object containing product information
     * @return Result whether the product data is updated successfully and process message
     */
    @Override
    public Result updateProduct(MultipartFile file, Product product) {

        // 1. select Product by product id to get image path
        Product originProduct = productMapper.selectById(product.getId());

        // 2. use image util to delete and update product image
        imageUtil = new ImageUtil();
        imageUtil.imageDelete(originProduct.getImagePath());
        Result uploadResult = imageUtil.imageUpload(file, "/src/main/resources/static/images/product/");

        // 3. determine whether product uploaded successfully
        if (uploadResult.getFlag()) {
            // 4. upload successful, update product information to database
            // 4.2 set new product image path
            product.setImagePath(uploadResult.getMessage());
            // 4.1 update data and determine updated result
            if (productMapper.updateById(product) > 0) {
                // 5. update data successful, return successful message
                return new Result(true, "Product updated successfully");
            }
            // 4.2. Failed to update data into database, return error message
            return new Result(false, "Failed to add Product");
        }
        // 3.1 Fail to upload image, return uploadResult with error message
        return uploadResult;
    }

    /**
     * find all product information
     *
     * @return Result  whether the products data is founded successfully,
     * Data object containing all products information in List and process message
     */
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
     *
     * @param id the id of the product to find
     * @return Result whether the product data is founded successfully,
     * Data object containing product information and process message
     */
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
     *
     * @param sellerId the id of the seller to find
     * @return Result whether the product data is founded successfully,
     * Data object containing all products information of seller in List and process message
     */
    @Override
    public Result selectBySellerId(Integer sellerId) {
        // 1. get all products information from database
        List<Product> products = productMapper.selectBySellerId(sellerId);
        // 2. determine whether there is data in the database
        if (products.isEmpty()) {
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
