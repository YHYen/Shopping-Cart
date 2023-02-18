package com.idv.yen.controller;

import com.idv.yen.controller.Utils.Result;
import com.idv.yen.domain.Product;
import com.idv.yen.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Result save(@RequestBody Product product) {
        return new Result(true, productService.save(product));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        return new Result(productService.delete(id));
    }

    @PutMapping
    public Result update(@RequestBody Product product) {
        return new Result(true, productService.update(product));
    }

    @GetMapping
    public Result selectAll() {
        return new Result(true, productService.selectAll());
    }


}
