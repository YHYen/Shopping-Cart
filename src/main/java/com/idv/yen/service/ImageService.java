package com.idv.yen.service;

import com.idv.yen.domain.Image;
import com.idv.yen.domain.Product;
import com.idv.yen.service.Utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * all required method related to the image process
 * */
@Service
public interface ImageService {
    Result uploadSellerImage(MultipartFile file, HttpSession httpSession);
    Result sellerImageExists(Integer foreignId);
    Result uploadProductImage(MultipartFile file, Product product);
    Result selectSellerImageByForeignId(Integer foreignId, HttpServletResponse httpServletResponse) throws Exception;
    Result selectProductImageByForeignId(Integer foreignId, HttpServletResponse httpServletResponse) throws Exception;
    Result updateSellerImage(MultipartFile file, Image image);
    Result updateProductImage(MultipartFile file, Image image);
    Result deleteSellerImageByForeignId(Integer foreignId);
    Result deleteProductImageByForeignId(Integer foreignId);
}
