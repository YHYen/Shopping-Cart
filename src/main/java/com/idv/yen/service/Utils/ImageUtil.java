package com.idv.yen.service.Utils;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

public class ImageUtil {

    private File path = new File("");

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
     * upload image
     *
     * @param file      image file to upload
     * @param staticPth Current static web path
     * @return Result Whether the image is uploaded successfully and the path of the image
     */
    public Result imageUpload(MultipartFile file, String staticPth) {
        // 1. Set image file storage address
        File newFile = new File(path.getAbsolutePath() + staticPth);

        // 1.1 Create the path if it does not exist
        if (!newFile.exists()) {
            newFile.mkdirs();
        }

        // 2. Use system time to set non-repeating file names
        String realFileName = System.currentTimeMillis() + file.getOriginalFilename();
        // get file extension
        // String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1);

        // 3. set new file path
        String newFilePath = newFile.getAbsolutePath() + "\\" + realFileName;
        try {
            file.transferTo(new File(newFilePath));

            return new Result(true, newFilePath);

        } catch (IllegalStateException illegalStateException) {
            return new Result(false, "response already close");
        } catch (IOException ioException) {
            return new Result(false, "Something went wrong~");
        }

    }

    /**
     * response image
     *
     * @param path The path of the image to display
     * @return String Image to Base64 String
     */
    public String getBase64Image(String path) {
        try {
            InputStream imgInputStream = getImgInputStream(path);
            assert imgInputStream != null;
            byte[] bytes = IOUtils.toByteArray(imgInputStream);
            imgInputStream.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            return "fail";
        }
    }

    /**
     * delete image
     *
     * @param path The path of the image to delete
     */
    public void imageDelete(String path) {
        System.out.println(path);
        File file = new File(path);
        file.delete();
    }

}
