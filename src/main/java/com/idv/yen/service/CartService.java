package com.idv.yen.service;

import com.idv.yen.domain.Cart;
import com.idv.yen.service.Utils.Result;
import org.springframework.stereotype.Service;

/**
 * all required method related to the cart process
 */
@Service
public interface CartService {
    Result addToCart(Cart cart);

    Result deleteByCartId(Integer cartId);

    Result updateCart(Cart cart);

    Result findUserCart(Integer userId);

    Result emptyCartByUserId(Integer userId);

}
