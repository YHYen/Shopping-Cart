package com.idv.yen.mapper;

import com.idv.yen.domain.Cart;
import com.idv.yen.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartMapper {

    /**
     * add cart to cart table
     *
     * @param cart cart object containing cart information
     * @return int the number of rows changed in the database
     */
    @Insert("insert into " +
            "   tb_cart " +
            "values" +
            "   (null, #{userId}, #{productId}, #{quantity}, CURRENT_TIMESTAMP)")
    int insertProduct(Cart cart);

    /**
     * delete cart by id
     *
     * @param id cart id
     * @return int the number of rows changed in the database
     */
    @Delete("delete from " +
            "   tb_cart " +
            "where " +
            "   id = #{id}")
    int deleteById(Integer id);

    /**
     * update cart data in cart table by the cart id
     *
     * @param cart cart object containing cart information
     * @return int the number of rows changed in the database
     */
    @Update("update " +
            "   tb_cart " +
            "set " +
            "   quantity = #{quantity} " +
            "where " +
            "   id = #{id}")
    int updateById(Cart cart);


    /**
     * query all cart information by user id
     *
     * @return List<Cart> return all the cart information of user and save them in list
     */
    @ResultMap("cartResultMap")
    @Select("select " +
            "   cart.id as cart_id, " +
            "   cart.quantity as cart_quantity, " +
            "   cart.user_id as user_id, " +
            "   product.id as product_id, " +
            "   product.product_name, " +
            "   product.price as product_price, " +
            "   product.image_path " +
            "from " +
            "   tb_cart as cart, " +
            "   tb_product as product " +
            "where " +
            "   cart.user_id = #{userId} AND product.id = cart.product_id")
    List<Cart> selectAllProductInCartByUserId(Integer userId);

    /**
     * use cart id to query cart information
     *
     * @param id cart id
     * @return Cart return the cart information and encapsulate it into Cart object
     */
    @ResultMap("cartResultMap")
    @Select("select " +
            "   id, user_id, product_id, quantity, create_time " +
            "from " +
            "   tb_cart " +
            "where " +
            "   id = #{id}")
    Cart selectById(Integer id);

    /**
     * use user id and product id to query cart information
     *
     * @param userId user id
     * @param productId product id
     * @return Cart return the cart information and encapsulate it into Cart object
     * */
    @ResultMap("cartResultMap")
    @Select("select " +
            "   id, " +
            "   user_id, " +
            "   product_id, " +
            "   quantity " +
            "from " +
            "   tb_cart " +
            "where " +
            "   user_id = #{userId} AND product_id = #{productId}")
    Cart selectByUserIdAndProductId(Integer userId, Integer productId);


    /**
     * Delete cart data by user id
     * @param userId user id
     * @return int the number of rows change in database
     * */
    @Delete("delete from " +
            "   tb_cart " +
            "where " +
            "   user_id = #{userId}")
    int emptyCartByUserId(Integer userId);
}
