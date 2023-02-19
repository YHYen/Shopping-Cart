package com.idv.yen.controller;

import com.idv.yen.domain.Image;
import com.idv.yen.domain.Product;
import com.idv.yen.domain.User;
import com.idv.yen.service.ImageService;
import com.idv.yen.service.UserService;
import com.idv.yen.service.Utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/images")
public class ImageController {

    private ImageService imageService;
    private UserService userService;
    @Autowired
    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @PostMapping("/sellerImageUpload")
    public Result uploadSellerImage(@RequestPart(value = "file") MultipartFile file, HttpSession httpSession) throws Exception {
        Result result = imageService.uploadSellerImage(file, httpSession);

        // if update successfully, update user type
        if (result.getFlag()) {
            userService.updateUserProfile((User) result.getData());
        }

        return result;

    }

    @PostMapping("/productImageUpload")
    public Result uploadProductImage(@RequestPart(value = "file") MultipartFile file, Product product) throws Exception {
        return imageService.uploadProductImage(file, product);
    }

    @GetMapping("/getProduct")
    public Result selectProductImageByForeignId(@RequestBody Product product, HttpServletResponse httpServletResponse) throws Exception {
        return imageService.selectProductImageByForeignId(product.getId(), httpServletResponse);
    }

    @PutMapping("/sellerImageUpdate")
    public Result updateSellerImage(@RequestPart(value = "file") MultipartFile file, @RequestBody Image image) {
        return imageService.updateSellerImage(file, image);
    }

    @PutMapping("/productImageUpdate")
    public Result updateProductImage(@RequestPart(value = "file") MultipartFile file, @RequestBody Image image) {
        return imageService.updateSellerImage(file, image);
    }

    @DeleteMapping("/deleteProduct")
    public Result deleteProductImageByForeignId(@RequestBody Product product) {
        return imageService.deleteProductImageByForeignId(product.getId());
    }
}
