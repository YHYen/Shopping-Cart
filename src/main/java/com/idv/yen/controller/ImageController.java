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


    @PutMapping("/sellerImageUpdate")
    public Result updateSellerImage(@RequestPart(value = "file") MultipartFile file, @RequestBody Image image) {
        return imageService.updateSellerImage(file, image);
    }
}
