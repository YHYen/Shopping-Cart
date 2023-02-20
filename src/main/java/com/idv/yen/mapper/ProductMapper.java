package com.idv.yen.mapper;

import com.idv.yen.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductMapper {
    //1. add Product
    //2. delete Product
    //3. update Product
    //4. find Products
    //5. find Product

    /**
     * add product to product table
     *
     * @param product product object containing product information
     * @return int the number of rows changed in the database
     */
    @Insert("insert into " +
            "   tb_product " +
            "values" +
            "   (null, #{productName}, #{price}, #{quantity}, #{sellerId}, #{imagePath})")
    int insertProduct(Product product);

    /**
     * delete product by id
     *
     * @param id product id
     * @return int the number of rows changed in the database
     */
    @Delete("delete from " +
            "   tb_product " +
            "where " +
            "   id = #{id}")
    int deleteById(Integer id);

    /**
     * update product data in product table by the product id
     *
     * @param product product object containing product information
     * @return int the number of rows changed in the database
     */
    @Update("update " +
            "   tb_product " +
            "set " +
            "   product_name = #{productName}, " +
            "   price = #{price}, " +
            "   quantity = #{quantity}, " +
            "   seller_id = #{sellerId}, " +
            "   image_path = #{imagePath} " +
            "where " +
            "   id = #{id}")
    int updateById(Product product);

    /**
     * query all products information
     *
     * @return List<product> return all products information and save them in list
     */
    @Result(property = "productName", column = "product_name")
    @Result(property = "sellerId", column = "seller_id")
    @Result(property = "imagePath", column = "image_path")
    @Select("select " +
            "   id, product_name, price, quantity, seller_id, image_path " +
            "from " +
            "   tb_product ")
    List<Product> selectAll();

    /**
     * use products id to query product information
     *
     * @param id products id
     * @return Product return the product information and encapsulate it into Product object
     */
    @Result(property = "productName", column = "product_name")
    @Result(property = "sellerId", column = "seller_id")
    @Result(property = "imagePath", column = "image_path")
    @Select("select " +
            "   product_name, price, quantity, seller_id, image_path  " +
            "from " +
            "   tb_product " +
            "where " +
            "   id = #{id}")
    Product selectById(Integer id);

    /**
     * use seller id to query product information
     *
     * @param sellerId seller id
     * @return Product return the product information and encapsulate it into Product object
     */
    @Result(property = "productName", column = "product_name")
    @Result(property = "sellerId", column = "seller_id")
    @Result(property = "imagePath", column = "image_path")
    @Select("select " +
            "   id, product_name, price, quantity, seller_id, image_path  " +
            "from " +
            "   tb_product " +
            "where " +
            "   seller_id = #{sellerId}")
    List<Product> selectBySellerId(Integer sellerId);

}
