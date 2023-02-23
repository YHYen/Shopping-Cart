package com.idv.yen.mapper;


import com.idv.yen.domain.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderMapper {
    /**
     * add order to order table
     *
     * @param order order object containing order information
     * @return int the number of rows changed in the database
     */
    @Insert("insert into " +
            "   tb_order " +
            "values" +
            "   (null, " +
            "   #{userId}, " +
            "   #{price}, " +
            "   #{payType}, " +
            "   #{paymentStatus}, " +
            "   #{shippingStatus}, " +
            "   CURRENT_TIMESTAMP, " +
            "   CURRENT_TIMESTAMP)")
    int insertOrder(Order order);

    /**
     * add cart data to order product merge table by user id
     *
     * @param userId user id to add cart
     * @return int the number of rows changed in the database
     */
    @Insert("insert into " +
            "   tb_order_product" +
            "   (order_id, product_id, quantity) " +
            "(SELECT " +
            "    o.id, cart.product_id, cart.quantity " +
            "FROM" +
            "    tb_order AS o, " +
            "    tb_cart AS cart " +
            "WHERE " +
            "    cart.user_id = #{userId} AND o.user_id = #{userId})")
    int insertOrderProductMergeFromCartTableByUserId(Integer userId);


    @Insert("insert into" +
            "    tb_order_product " +
            "values" +
            "   (null, " +
            "   LAST_INSERT_ID(), " +
            "   #{productId}, " +
            "   #{quantity})")
    int insertOrderProductMergeByProduct(Integer productId, Integer quantity);

    /**
     * delete order by id
     *
     * @param orderId order id to delete
     * @return int the number of rows changed in the database
     */
    @Delete("delete " +
            "  TOP.*, o.* " +
            "from " +
            "   tb_order_product as TOP, " +
            "   tb_order as o " +
            "where " +
            "   TOP.order_id = #{orderId} AND o.id = #{orderId} ")
    int deleteByOrderId(Integer orderId);


    /**
     * update quantity from OrderProductMerge table by orderId and productId
     *
     * @param orderId   Order id with changes
     * @param productId The target product whose quantity is to be changed
     * @param quantity  Quantity to change
     */
    @Update("update " +
            "   tb_order_product as TOP " +
            "set " +
            "   TOP.quantity = #{quantity} " +
            "where " +
            "   TOP.order_id = #{orderId} " +
            "AND " +
            "   TOP.product_id = #{productId}")
    int updateQuantityByOrderIdAndProductId(Integer orderId, Integer productId, Integer quantity);


    /**
     *
     */
    @Update("update " +
            "   tb_order as O " +
            "set " +
            "   O.price = #{price} " +
            "where " +
            "   id = #{id}")
    int updatePriceByOrderId(Order order);


    /**
     * query all order information by user id
     *
     * @param orderId The order id to query
     * @return List<Order> return all the order information of this order and save them in list
     */
    @ResultMap("orderResultMap")
    @Select("SELECT " +
            "    TOP.id AS id," +
            "    TOP.order_id AS order_id," +
            "    TOP.quantity AS quantity," +
            "    product.id AS product_id," +
            "    product.product_name," +
            "    product.price AS product_price," +
            "    product.image_path " +
            "FROM " +
            "    tb_order_product AS TOP, " +
            "    tb_product AS product " +
            "WHERE " +
            "    TOP.order_id = #{orderId} " +
            "        AND product.id = TOP.product_id")
    List<Order> selectAllProductInOrderByOrderId(Integer orderId);


    /**
     * select all order by user id
     *
     * @param userId The user id to query
     * @return List<Order> return all the order information of user and save them in list
     */
    @ResultMap("orderResultMap")
    @Select("SELECT " +
            "    id, " +
            "    user_id, " +
            "    price, " +
            "    pay_type, " +
            "    payment_status, " +
            "    shipping_status, " +
            "    create_time, " +
            "    delivery_time " +
            "FROM " +
            "    tb_order " +
            "WHERE " +
            "    user_id = #{userId} ")
    List<Order> selectAllOrderByUserId(Integer userId);

}
