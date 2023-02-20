package com.idv.yen.service.Utils;

import com.idv.yen.domain.Image;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

public class ImageUtil {

    private File path = new File("");

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
     * upload image
     *
     * */
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
     * */
    public String getBase64Image(String path) {
        try {
            byte[] bytes = IOUtils.toByteArray(getImgInputStream(path));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            return "fail";
        }


    }

}
