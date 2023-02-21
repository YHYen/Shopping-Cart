package com.idv.yen.service;

import com.idv.yen.domain.Image;
import com.idv.yen.service.Utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * all required method related to the image process
 */
@Service
public interface ImageService {
    Result uploadSellerImage(MultipartFile file, Integer userId);

    Result sellerImageExists(Integer foreignId);

    Result selectSellerImageByForeignId(Integer foreignId, HttpServletResponse httpServletResponse) throws Exception;

    Result updateSellerImage(MultipartFile file, Image image);

    Result deleteSellerImageByForeignId(Integer foreignId);

}
