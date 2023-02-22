package com.idv.yen.controller;

import com.idv.yen.domain.Product;
import com.idv.yen.service.ProductService;
import com.idv.yen.service.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public Result addProduct(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "product") Product product) {
        return productService.addProduct(file, product);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public Result deleteProduct(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/updateProduct")
    public Result updateProduct(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "product") Product product) {
        return productService.updateProduct(file, product);
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
