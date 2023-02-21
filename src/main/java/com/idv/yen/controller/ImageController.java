package com.idv.yen.controller;

import com.idv.yen.domain.Image;
import com.idv.yen.service.ImageService;
import com.idv.yen.service.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @PutMapping("/sellerImageUpdate")
    public Result updateSellerImage(@RequestPart(value = "file") MultipartFile file, @RequestBody Image image) {
        return imageService.updateSellerImage(file, image);
    }
}
