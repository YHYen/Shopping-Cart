package com.idv.yen.service;

import com.idv.yen.domain.Product;
import com.idv.yen.service.Utils.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * all required method related to the Product
 */
public interface ProductService {
    Result addProduct(MultipartFile file, Product product);

    Result deleteProduct(Integer id);

    Result updateProduct(MultipartFile file, Product product);

    Result selectAll();

    Result selectById(Integer id);

    Result selectBySellerId(Integer sellerId);

}
