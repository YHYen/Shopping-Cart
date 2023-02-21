package com.idv.yen.controller;


import com.idv.yen.domain.Order;
import com.idv.yen.service.CartService;
import com.idv.yen.service.OrderService;
import com.idv.yen.service.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    /**
     * add order information to order table and order_product_merge table
     * after add the order, let the cart of user empty
     *
     * @param order order object containing order information
     * @return Result whether the order is added and cart is deleted successfully and process message
     */
    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody Order order) {
        // 1. Add data to order and order_product_merge table
        if (orderService.addOrder(order).getFlag()) {
            // Data added successfully
            // 2. Delete the data that becomes order from the cart table
            if (cartService.emptyCartByUserId(order.getUserId()).getFlag()) {
                // Data deleted successfully
                // 3. return add successfully message
                return new Result(true, "Data added successfully");
            }
            return new Result(false, "Shopping cart data deletion failed");
        }
        return new Result(false, "Failed to add order information");
    }

    @GetMapping("/findUserOrder/{userId}")
    public Result findUserOrder(@PathVariable Integer userId) {
        return orderService.selectAllOrderByUserId(userId);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public Result deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrderById(orderId);
    }

    @GetMapping("/findOrderProduct/{orderId}")
    public Result findOrderProduct(@PathVariable Integer orderId) {
        return orderService.selectAllProductInOrder(orderId);
    }
}
