package com.idv.yen.service;

import com.idv.yen.domain.Order;
import com.idv.yen.service.Utils.Result;
import org.springframework.stereotype.Service;

/**
 * all required method related to the order process
 */
@Service
public interface OrderService {
    Result addOrder(Order order);

    Result deleteOrderById(Integer orderId);

    Result selectAllProductInOrder(Integer orderId);

    Result selectAllOrderByUserId(Integer userId);

    /**
     Normally, there are no changes to an order after it has been sent,
     only cancellations or additions.
     I need to add it after checking

     // Result updateQuantity(Integer orderId, Integer productId, Integer quantity);
     // Result updateOrderPrice(Order order);
     */
}
