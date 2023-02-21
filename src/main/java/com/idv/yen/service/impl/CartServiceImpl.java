package com.idv.yen.service.impl;

import com.idv.yen.domain.Cart;
import com.idv.yen.mapper.CartMapper;
import com.idv.yen.service.CartService;
import com.idv.yen.service.Utils.ImageUtil;
import com.idv.yen.service.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    /**
     * add cart information to cart table
     *
     * @param cart cart object containing cart information
     * @return Result whether the cart is added successfully and process message
     */
    @Override
    public Result addToCart(Cart cart) {

        // 1. Check if the product is already in the user's shopping cart
        Cart cart1 = cartMapper.selectByUserIdAndProductId(cart.getUserId(), cart.getProductId());
        if (cart1 == null) {
            // 2. If it doesn't exist, add it
            if (cartMapper.insertProduct(cart) > 0) {
                return new Result(true, "Add to cart successfully");
            }
            return new Result(false, "Add to Cart failed");
        }
        // 3. If it exists, return an error message
        return new Result(false, "This product is already in your cart");
    }

    /**
     * delete cart data by the cart id
     *
     * @param cartId the id of the cart to delete
     * @return Result whether the cart deleted successfully and process message
     */
    @Override
    public Result deleteByCartId(Integer cartId) {
        // 1. call cartMapper to delete cart data from cart table
        if (cartMapper.deleteById(cartId) > 0) {
            return new Result(true, "Cart deleted successfully");
        }
        return new Result(false, "Failed to delete Cart");
    }

    /**
     * update cart data by the cart object and update
     *
     * @param cart cart object containing cart information
     * @return Result whether the cart data is updated successfully and process message
     */
    @Override
    public Result updateCart(Cart cart) {
        if (cartMapper.updateById(cart) > 0) {
            return new Result(true, "Cart updated successfully");
        }
        return new Result(false, "Failed to update Cart");
    }

    /**
     * find cart information by user id
     *
     * @param userId the id of the user to find
     * @return Result whether the cart data is founded successfully,
     * Data object containing cart information and process message
     */
    @Override
    public Result findUserCart(Integer userId) {
        // 1. get all cart information from cart table
        List<Cart> carts = cartMapper.selectAllProductInCartByUserId(userId);
        // 2. determine whether there is data in cart table
        if (carts.isEmpty()) {
            // 2.1. if there is no data in cart table, return false and process message
            return new Result(false, null, "No products found!");
        }

        // 3. traverse products to get product image path
        for (Cart cart : carts) {
            // 3.1. get product image path
            String imagePath = cart.getProduct().getImagePath();

            // 3.2. get product base64Image with imageUtil
            ImageUtil imageUtil = new ImageUtil();
            String base64Image = imageUtil.getBase64Image(imagePath);

            // 3.3. set product base64Image to product path
            cart.getProduct().setImagePath(base64Image);

            // 3.4 set cart subTotal
            cart.setSubTotal(BigDecimal.valueOf(cart.getQuantity()).multiply(cart.getProduct().getPrice()));
        }

        return new Result(true, carts, "Products found successfully");
    }

    /**
     * empty cart data by the user id
     *
     * @param userId the id of the user to delete
     * @return Result whether the cart deleted successfully and process message
     */
    @Override
    public Result emptyCartByUserId(Integer userId) {
        if (cartMapper.emptyCartByUserId(userId) > 0) {
            return new Result(true, "Delete all data successfully");
        }
        return new Result(false, "There is nothing in your cart");
    }
}
