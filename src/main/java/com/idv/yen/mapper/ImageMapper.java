package com.idv.yen.mapper;

import com.idv.yen.domain.Image;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface ImageMapper {
    //1. add image
    //2. select by id
    //3. update by id
    //4. delete by id

    /**
     * add seller image to image table
     *
     * @param image Image object containing seller image information
     * @retrun int the number of rows changed in the database
     */
    @Insert("insert into tb_seller_image values(null, #{foreignId}, #{imagePath})")
    int insertSellerImage(Image image);

    /**
     * add product image to image table
     *
     * @param image Image object containing product image information
     * @retrun int the number of rows changed in the database
     */
    @Insert("insert into tb_product_image values(null, #{foreignId}, #{imagePath})")
    int insertProductImage(Image image);

    /**
     * use seller id to query product image information
     *
     * @param foreignId seller id
     * @retrun int the number of rows changed in the database
     */
    @Result(column = "seller_id", property = "foreignId")
    @Result(column = "image_path", property = "imagePath")
    @Select("select id, seller_id, image_path from tb_seller_image where seller_id = #{foreign}")
    Image selectSellerImageByForeignId(Integer foreignId);

    /**
     * use product id to query product image information
     *
     * @param foreignId product id
     * @retrun int the number of rows changed in the database
     */
    @Result(column = "product_id", property = "foreignId")
    @Result(column = "image_path", property = "imagePath")
    @Select("select id, product_id, image_path from tb_product_image where product_id = #{foreign}")
    Image selectProductImageByForeignId(Integer foreignId);

    /**
     * update seller image data in seller image table by the image id
     *
     * @param image Image object containing seller image information
     * @return int the number of rows changed in the database
     */
    @Update("update tb_seller_image set seller_id = #{foreignId}, image_path = #{imagePath}")
    int updateSellerImage(Image image);

    /**
     * update product image data in product image table by the image id
     *
     * @param image Image object containing product image information
     * @return int the number of rows changed in the database
     */
    @Update("update tb_product_image set product_id = #{foreignId}, image_path = #{imagePath}")
    int updateProductImage(Image image);

    /**
     * delete seller Image by seller id
     *
     * @param foreignId seller id
     * @return int the number of rows changed in the database
     */
    @Delete("delete from tb_seller_image where seller_id = #{foreignId}")
    int deleteSellerImageForeignId(Integer foreignId);

    /**
     * delete product Image by product id
     *
     * @param foreignId product id
     * @return int the number of rows changed in the database
     */
    @Delete("delete from tb_product_image where product_id = #{foreignId}")
    int deleteProductImageForeignId(Integer foreignId);
}
