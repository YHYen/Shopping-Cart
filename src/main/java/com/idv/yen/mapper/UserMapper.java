package com.idv.yen.mapper;

import com.idv.yen.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    /**
     * query all user information
     * @return List<User> return all user information and save them in list
     * */
    @Result(property = "phoneNumber", column = "phone_number")
    @Select("select " +
            "   id, username, password, type, phone_number " +
            "from " +
            "   tb_user ")
    List<User> selectAll();

    /**
     * use user id to query user information
     * @param id user id
     * @return User return the user information and encapsulate it into User object
     * */
    @Result(property = "phoneNumber", column = "phone_number")
    @Select("select " +
            "   id, username, password, type, phone_number " +
            "from " +
            "   tb_user " +
            "where " +
            "   id = #{id}")
    User selectById(Integer id);

    /**
     * use username to query user id
     * @return Integer return the user id
     * */
    @Result(property = "phoneNumber", column = "phone_number")
    @Select("select " +
            "   id, username, password, type, phone_number " +
            "from " +
            "   tb_user " +
            "where " +
            "   username = #{username}")
    User selectByUsername(String username);

    /**
     * use username and password to query user id
     * @return Integer return the user id
     * */
    @Result(property = "phoneNumber", column = "phone_number")
    @Select("select " +
            "   id, username, password, type, phone_number " +
            "from " +
            "   tb_user " +
            "where " +
            "   username = #{username} and password = #{password}")
    User selectByUsernameAndPassword(String username, String password);

    /**
     * add user to user table
     * @param user User object containing user information
     * @return int the number of rows changed in the database
     * */
    @Insert("insert into " +
            "   tb_user " +
            "values" +
            "   (null, #{username}, #{password}, #{type}, #{phoneNumber}, #{email})")
    int insertUser(User user);


    /**
     * update user data in user table by the user id
     * @param user User object containing user information
     * @return int the number of rows changed in the database
     * */
    @Update("update " +
            "   tb_user " +
            "set " +
            "   username = #{username}, " +
            "   password = #{password}, " +
            "   type = #{type}, " +
            "   phone_number = #{phoneNumber} " +
            "where " +
            "   id = #{id}")
    int updateById(User user);


    /**
     * delete user by id
     * @param id user id
     * @return int the number of rows changed in the database
     * */
    @Delete("delete from " +
            "   tb_user " +
            "where " +
            "   id = #{id}")
    int deleteById(Integer id);
}
