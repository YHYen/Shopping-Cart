package com.idv.yen.mapper;

import com.idv.yen.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductMapper {
    /**
     * query all products information
     * @return List<product> return all products information and save them in list
     * */
    @Result(property = "productName", column = "product_name")
    @Result(property = "sellerId", column = "seller_id")
    @Select("select " +
            "   id, product_name, price, quantity, seller_id " +
            "from " +
            "   tb_product ")
    List<Product> selectAll();

    /**
     * use products id to query product information
     * @param id products id
     * @return Product return the product information and encapsulate it into Product object
     * */
    @Result(property = "productName", column = "product_name")
    @Result(property = "sellerId", column = "seller_id")
    @Select("select " +
            "   product_name, price, quantity, seller_id " +
            "from " +
            "   tb_product " +
            "where " +
            "   id = #{id}")
    Product selectById(Integer id);

    /**
     * add product to product table
     * @param product product object containing product information
     * @return int the number of rows changed in the database
     * */
    @Insert("insert into " +
            "   tb_product " +
            "values" +
            "   (null, #{productName}, #{price}, #{quantity}, #{sellerId})")
    int insertProduct(Product product);


    /**
     * update product data in product table by the product id
     * @param product product object containing product information
     * @return int the number of rows changed in the database
     * */
    @Update("update " +
            "   tb_product " +
            "set " +
            "   product_name = #{productName}, " +
            "   price = #{price}, " +
            "   quantity = #{quantity}, " +
            "   seller_id = #{sellerId} " +
            "where " +
            "   id = #{id}")
    int updateById(Product product);


    /**
     * delete product by id
     * @param id product id
     * @return int the number of rows changed in the database
     * */
    @Delete("delete from " +
            "   tb_product " +
            "where " +
            "   id = #{id}")
    int deleteById(Integer id);
}
