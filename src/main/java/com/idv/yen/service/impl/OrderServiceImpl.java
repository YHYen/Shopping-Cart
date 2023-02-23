package com.idv.yen.service.impl;

import com.idv.yen.domain.Cart;
import com.idv.yen.domain.Order;
import com.idv.yen.mapper.OrderMapper;
import com.idv.yen.service.OrderService;
import com.idv.yen.service.Utils.ImageUtil;
import com.idv.yen.service.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * add order information to order table and order_product_merge table
     *
     * @param order order object containing order information
     * @return Result whether the order is added successfully and process message
     * */
    @Override
    public Result addOrder(Order order) {
        // 1. Add data to order table
        // 1.1 set order data
        order.setPayType(1);
        order.setPaymentStatus(1);
        order.setShippingStatus(5);

        // 1.2 Add data to order table
        if(orderMapper.insertOrder(order) > 0) {
            // Add successfully
            // 2. Add data to order_product_merge table
            Cart cart = order.getCarts().get(0);
            if (orderMapper.insertOrderProductMergeFromCartTableByUserId(cart.getUserId()) > 0) {
                return new Result(true, "Data added successfully");
            }
            return new Result(false, "Add to order_product_merge table failed");
        }
        return new Result(false, "Add to Order failed");

    }

    /**
     * add order information by one product
     *
     * @param order order object containing order information
     * @return Result whether the order is added successfully and process message
     * */
    @Override
    public Result addProductToOrder(Order order) {
        // 1. add price info and user info to order table
        // 1.1 set order data
        order.setPrice(order.getProduct().getPrice());
        order.setPayType(1);
        order.setPaymentStatus(1);
        order.setShippingStatus(5);

        // 1.2 Add data to order table
        if(orderMapper.insertOrder(order) > 0) {
            // Add successfully
            // 2. add product info and quantity info to product order merge table
            if (orderMapper.insertOrderProductMergeByProduct(order.getProduct().getId(), 1) > 0) {
                return new Result(true, "Data added successfully");
            }
            return new Result(false, "Add to order_product_merge table failed");
        }
        return new Result(false, "Add to Order failed");
    }

    /**
     * delete order data from order table and order_product_merge table by the order id
     * Because of foreign key bindings.
     * Therefore,the data in the child table must be deleted before deleting the data from the main table
     *
     * @param orderId the id of the order to delete
     * @return Result whether the order deleted successfully and process message
     * */
    @Override
    public Result deleteOrderById(Integer orderId) {
        // 1. call orderMapper to delete order data from order table and order_product_merge table
        // 1.1 The child table must be deleted first
        if (orderMapper.deleteOrderProductMergeDataByOrderId(orderId) > 0) {
            if (orderMapper.deleteOrderDataByOrderId(orderId) > 0) {
                // Both tables are deleted successfully
                // 2. return successful message
                return new Result(true, "Order deleted successfully");
            }
            // Failed to delete, return failure message
            return new Result(false, "Failed to delete the order from main table");
        }
        return new Result(false, "Failed to delete the order from child table");
    }

    @Override
    public Result selectAllProductInOrder(Integer orderId) {
        // 1. get all order information from order table
        List<Order> orders = orderMapper.selectAllProductInOrderByOrderId(orderId);

        // 2. determine whether there is data in order table
        if (orders.isEmpty()) {
            // 2.1 if there is no data in order table, return false and process message
            return new Result(false, null, "No products found!");
        }

        // 3. traverse products to get product image path
        for (Order order : orders) {
            // 3.1. get product image path
            String imagePath = order.getProduct().getImagePath();

            // 3.2. get product base64Image with imageUtil
            ImageUtil imageUtil = new ImageUtil();
            String base64Image = imageUtil.getBase64Image(imagePath);

            // 3.3. set product base64Image to product path
            order.getProduct().setImagePath(base64Image);

            // 3.4 set cart subTotal
            order.setPrice(BigDecimal.valueOf(order.getProduct().getQuantity()).multiply(order.getProduct().getPrice()));
        }

        return new Result(true, orders, "Products found successfully");
    }

    @Override
    public Result selectAllOrderByUserId(Integer userId) {
        // 1. get all order information from database
        List<Order> orders = orderMapper.selectAllOrderByUserId(userId);
        // 2. determine whether there is data in order table
        if(orders.isEmpty()) {
            // 2.1 if there is no data in order table, return false and process message
            return new Result(false, null, "No order found!");
        }

        // 3. return orders
        return new Result(true, orders, "Orders found successfully");
    }
}
