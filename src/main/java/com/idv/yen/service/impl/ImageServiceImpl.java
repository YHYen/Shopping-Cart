package com.idv.yen.service.impl;

import com.idv.yen.controller.UserController;
import com.idv.yen.domain.Image;
import com.idv.yen.domain.Product;
import com.idv.yen.domain.User;
import com.idv.yen.mapper.ImageMapper;
import com.idv.yen.service.ImageService;
import com.idv.yen.service.Utils.ImageUtil;
import com.idv.yen.service.Utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * use to implement the ImageService interface
 */
@Service
public class ImageServiceImpl implements ImageService {
    private ImageMapper imageMapper;
    private ImageUtil imageUtil;
    private File path = new File("");

    @Autowired
    public ImageServiceImpl(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    /**
     * get image from path use inputStream
     *
     * @param filePath image location
     * @return InputStream if the image is found, return the image, otherwise return null
     */
    private InputStream getImgInputStream(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (FileNotFoundException fileNotFoundException) {
            return null;
        }

    }

    /**
     * Image upload: upload seller image
     *
     * @param file   seller image file
     * @param userId the user id to apply for
     * @return Result whether the image upload successful and process message
     */
    @Override
    public Result uploadSellerImage(MultipartFile file, Integer userId) {
        // 1. check if the user has applied
        if (sellerImageExists(userId).getFlag()) {
            return new Result(false, "Image already exists");
        }

        // if not applied, use image util to upload seller image
        imageUtil = new ImageUtil();
        Result uploadResult = imageUtil.imageUpload(file, "/src/main/resources/static/images/seller/");

        // determine whether apply photo uploaded successfully
        if (uploadResult.getFlag()) {
            // set Image object
            Image image = new Image();
            image.setForeignId(userId);
            image.setImagePath(uploadResult.getMessage());

            // call mapper to insert data
            if (imageMapper.insertSellerImage(image) > 0) {
                return new Result(true, "Image added successfully");
            } else {
                return new Result(false, "Failed to add image");
            }
        }

        // if failed to upload photo , return upload error message
        return uploadResult;
    }

    /**
     * check if the image of user is already upload
     *
     * @param foreignId seller id
     * @return Result whether the image has been uploaded
     */
    @Override
    public Result sellerImageExists(Integer foreignId) {
        if (imageMapper.selectSellerImageByForeignId(foreignId) == null)
            return new Result(false);
        return new Result(true);
    }

    /**
     * Image upload: upload product image
     * @param file product image file
     * @param product product object to get product id
     * @return Result whether the image upload successful and process message
     * */


    /**
     * Find images from a specific seller
     *
     * @param foreignId seller id
     * @return Result whether the image object find successful and process message
     */
    @Override
    public Result selectSellerImageByForeignId(Integer foreignId, HttpServletResponse httpServletResponse) throws IOException {
        // use foreignId to find image
        Image image = imageMapper.selectSellerImageByForeignId(foreignId);


        if (image == null) {
            // Image not in database
            return new Result(false, null, "Image does not exist");
        }

        final InputStream inputStream = getImgInputStream(image.getImagePath());

        if (inputStream == null) {
            // Image is not in the path display location
            return new Result(false, null, "your image disappeared");
        }

        httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
        return new Result(true, image, "Search successful");
    }

    /**
     * Image update: update seller image
     *
     * @param file  seller image file
     * @param image Image object containing image information
     * @return Result whether the seller image update successful and process message
     */
    @Override
    public Result updateSellerImage(MultipartFile file, Image image) {
        String filePath = image.getImagePath();

        try {
            file.transferTo(new File(filePath));
            return new Result(true, "Image update successfully");

        } catch (IllegalStateException illegalStateException) {
            return new Result(false, "response already close.");
        } catch (IOException ioException) {
            return new Result(false, "Something went wrong~");
        }
    }

    /**
     * Delete images from a specific seller
     *
     * @param foreignId seller id
     * @return Result whether the seller image delete successful and process message
     */
    @Override
    public Result deleteSellerImageByForeignId(Integer foreignId) {
        if (imageMapper.deleteSellerImageForeignId(foreignId) > 1) {
            return new Result(true, "Image delete successful");
        }
        return new Result(false, "Failed to delete image");
    }
}
