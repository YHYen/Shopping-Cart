package com.idv.yen.service.impl;

import com.idv.yen.controller.UserController;
import com.idv.yen.domain.Image;
import com.idv.yen.domain.Product;
import com.idv.yen.domain.User;
import com.idv.yen.mapper.ImageMapper;
import com.idv.yen.service.ImageService;
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
    private File path = new File("");
    @Autowired
    public ImageServiceImpl(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    /**
     * get image from path use inputStream
     * @param filePath image location
     * @return InputStream if the image is found, return the image, otherwise return null
     * */
    private InputStream getImgInputStream(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (FileNotFoundException fileNotFoundException) {
            return null;
        }

    }

    /**
     * Image upload: upload seller image
     * @param file seller image file
     * @param httpSession session to get user id
     * @return Result whether the image upload successful and process message
     * */
    @Override
    public Result uploadSellerImage(MultipartFile file, HttpSession httpSession) {
        // String pathServer = httpServletRequest.getServletContext().getRealPath("") + "uploaded";
        // 1. Set image file storage address
        File newFile = new File(path.getAbsolutePath() + "/src/main/resources/static/images/seller/");

        // 1.1 Create the path if it does not exist
        if (!newFile.exists()) {
            newFile.mkdirs();
        }

        // Use system time to set non-repeating file names
        String realFileName = System.currentTimeMillis() + file.getOriginalFilename();
        // get file extension
        // String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1);

        // set new file path
        String newFilePath = newFile.getAbsolutePath() + "\\" + realFileName;

        // There should be ways to improve to get user id
        User user = (User) httpSession.getAttribute(UserController.SESSION_NAME);

        if (sellerImageExists(user.getId()).getFlag()) {
            return new Result(false, "Image already exists");
        }

        try {
            file.transferTo(new File(newFilePath));

            // set Image object
            Image image = new Image();
            image.setForeignId(user.getId());
            image.setImagePath(newFilePath);
            user.setType(1);

            // call mapper to insert data
            if (imageMapper.insertSellerImage(image) > 0) {
                return new Result(true, user, "Image added successfully");
            } else {
                return new Result(false, "Failed to add image");
            }

        } catch (IllegalStateException illegalStateException) {
            return new Result(false, "response already close");
        } catch (IOException ioException) {
            return new Result(false, "Something went wrong~");
        }
    }

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
    @Override
    public Result uploadProductImage(MultipartFile file, Product product) {
        // String pathServer = httpServletRequest.getServletContext().getRealPath("") + "uploaded";
        // 1. Set image file storage address
        File newFile = new File(path.getAbsolutePath() + "/src/main/resources/static/images/product/");

        try {
            System.out.println(newFile.getAbsolutePath());
        } catch(Exception e) {

        }
        // 1.1 Create the path if it does not exist
        if (!newFile.exists()) {
            newFile.mkdirs();
        }

        // Use system time to set non-repeating file names
        String realFileName = System.currentTimeMillis() + file.getOriginalFilename();
        // get file extension
        // String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1);

        // set new file path
        String newFilePath = newFile.getAbsolutePath() + "\\" + realFileName;

        try {
            file.transferTo(new File(newFilePath));

            // set Image object
            Image image = new Image();

            // There should be ways to improve to get user id
            image.setForeignId(product.getId());
            image.setImagePath(newFilePath);

            // call mapper to insert data
            if (imageMapper.insertSellerImage(image) > 0) {
                return new Result(true, "Image added successfully");
            } else {
                return new Result(false, "Failed to add image");
            }
        } catch (IllegalStateException illegalStateException) {
            return new Result(false, "response already close.");
        } catch (IOException ioException) {
            return new Result(false, "Something went wrong~");
        }
    }

    /**
     * Find images from a specific seller
     * @param foreignId seller id
     * @return Result whether the image object find successful and process message
     * */
    @Override
    public Result selectSellerImageByForeignId(Integer foreignId, HttpServletResponse httpServletResponse) throws IOException {
        // use foreignId to find image
        Image image = imageMapper.selectSellerImageByForeignId(foreignId);


        if (image == null) {
            // Image not in database
            return new Result(false, null, "Image does not exist");
        }

        final InputStream inputStream = getImgInputStream(image.getImagePath());

        if(inputStream == null) {
            // Image is not in the path display location
            return new Result(false, null, "your image disappeared");
        }

        httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
        return new Result(true, image, "Search successful");
    }

    /**
     * Find images from a specific product
     * @param foreignId product id
     * @return Result whether the image object find successful and process message
     * */
    @Override
    public Result selectProductImageByForeignId(Integer foreignId, HttpServletResponse httpServletResponse) throws IOException {
        // use foreignId to find image
        Image image = imageMapper.selectProductImageByForeignId(foreignId);


        if (image == null) {
            // Image not in database
            return new Result(false, null, "Image does not exist");
        }

        final InputStream inputStream = getImgInputStream(image.getImagePath());

        if(inputStream == null) {
            // Image is not in the path display location
            return new Result(false, null, "your image disappeared");
        }

        httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
        return new Result(true, image, "Search successful");
    }

    /**
     * Image update: update seller image
     * @param file seller image file
     * @param image Image object containing image information
     * @return Result whether the seller image update successful and process message
     * */
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
     * Image update: update product image
     * @param file product image file
     * @param image Image object containing image information
     * @return Result whether the product image update successful and process message
     * */
    @Override
    public Result updateProductImage(MultipartFile file, Image image) {
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
     * @param foreignId seller id
     * @return Result whether the seller image delete successful and process message
     * */
    @Override
    public Result deleteSellerImageByForeignId(Integer foreignId) {
        if (imageMapper.deleteSellerImageForeignId(foreignId) > 1) {
            return new Result(true, "Image delete successful");
        }
        return new Result(false, "Failed to delete image");
    }

    /**
     * Delete images from a specific product
     * @param foreignId product id
     * @return Result whether the product image delete successful and process message
     * */
    @Override
    public Result deleteProductImageByForeignId(Integer foreignId) {
        if (imageMapper.deleteProductImageForeignId(foreignId) > 1) {
            return new Result(true, "Image delete successful");
        }
        return new Result(false, "Failed to delete image");
    }
}
