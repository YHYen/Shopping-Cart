package com.idv.yen.controller;

import com.idv.yen.domain.Cart;
import com.idv.yen.service.CartService;
import com.idv.yen.service.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {

        this.cartService = cartService;
    }

    @PostMapping("addToCart")
    public Result addToCart(@RequestBody Cart cart) {
        return cartService.addToCart(cart);
    }

    @GetMapping("/findUserCart/{userId}")
    public Result findUserCart(@PathVariable Integer userId) {
        return cartService.findUserCart(userId);
    }

    @DeleteMapping("/deleteCart/{cartId}")
    public Result deleteCart(@PathVariable Integer cartId) {
        return cartService.deleteByCartId(cartId);
    }

    @PutMapping("/updateCart")
    public Result updateCart(@RequestBody Cart cart) {
        return cartService.updateCart(cart);
    }

    @DeleteMapping("/emptyCart/{userId}")
    public Result emptyCart(@PathVariable Integer userId) {
        return cartService.emptyCartByUserId(userId);
    }
}
