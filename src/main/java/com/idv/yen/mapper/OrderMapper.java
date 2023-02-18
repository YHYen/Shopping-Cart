package com.idv.yen.mapper;


import com.idv.yen.domain.Cart;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderMapper {
    /**
     * query all order information
     * @return List<Order> return all order information and save them in list
     * */
    @Result(property = "userId", column = "user_id")
    @Result(property = "pay_type", column = "pay_type")
    @Result(property = "shippingStatus", column = "shipping_status")
    @Select("select " +
            "   id, user_id, product_id, quantity " +
            "from " +
            "   tb_cart ")
    List<Cart> selectAll();

    /**
     * use cart id to query cart information
     * @param id cart id
     * @return Cart return the cart information and encapsulate it into Cart object
     * */
    @Result(property = "userId", column = "user_id")
    @Result(property = "productId", column = "product_id")
    @Select("select " +
            "   user_id, product_id, quantity " +
            "from " +
            "   tb_cart " +
            "where " +
            "   id = #{id}")
    Cart selectById(Integer id);

    /**
     * add cart to cart table
     * @param cart cart object containing cart information
     * @return int the number of rows changed in the database
     * */
    @Insert("insert into " +
            "   tb_cart " +
            "values" +
            "   (null, #{userId}, #{productId}, #{quantity})")
    int insertProduct(Cart cart);


    /**
     * update cart data in cart table by the cart id
     * @param cart cart object containing cart information
     * @return int the number of rows changed in the database
     * */
    @Update("update " +
            "   tb_cart " +
            "set " +
            "   user_id = #{userId}, " +
            "   product_id = #{productId}, " +
            "   quantity = #{quantity} " +
            "where " +
            "   id = #{id}")
    int updateById(Cart cart);


    /**
     * delete cart by id
     * @param id cart id
     * @return int the number of rows changed in the database
     * */
    @Delete("delete from " +
            "   tb_cart " +
            "where " +
            "   id = #{id}")
    int deleteById(Integer id);
}
