package com.idv.yen.controller;

import com.idv.yen.service.ImageService;
import com.idv.yen.service.Utils.Result;
import com.idv.yen.domain.Product;
import com.idv.yen.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private ImageService imageService;

    @Autowired
    public ProductController(ProductService productService, ImageService imageService) {
        this.productService = productService;
        this.imageService = imageService;
    }

    @PostMapping("/addProduct")
    public Result addProduct(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "product") Product product) {
        return productService.addProduct(file, product);
    }

    @DeleteMapping("deleteProduct/{id}")
    public Result deleteProduct(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/updateProduct")
    public Result updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @GetMapping("/findAllProducts")
    public Result findAllProducts() {
        return productService.selectAll();
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id) {
        return productService.selectById(id);
    }

    @GetMapping("/findSellerProducts/{sellerId}")
    public Result findSellerProducts(@PathVariable Integer sellerId) {
        return productService.selectBySellerId(sellerId);
    }

}
