package com.idv.yen.mapper;

import com.idv.yen.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@MapperScan("com.idv.yen.mapper")
public class UserMapperTest {


    private UserMapper userMapper;
    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(1);
        Assertions.assertNotNull(user);
    }

    @Test
    void testSelectAll() {
        List<User> users = userMapper.selectAll();
        Assertions.assertNotNull(users);
    }

    @Test
    void testSelectByUsername() {
        String username = "Xiu";
        User user = userMapper.selectByUsername(username);
        Assertions.assertNotNull(user);
    }

    @Test
    void testSelectByUsernameAndPassword() {
        String username = "Xiu";
        String password = "123321";
        User user = userMapper.selectByUsernameAndPassword(username, password);
        Assertions.assertNotNull(user);
    }

    @Test
    @Rollback
    void testInsertUser() {
        User user = new User();
        user.setUsername("言");
        user.setPassword("987654");
        user.setType(1);
        user.setPhoneNumber("0912345677");
        user.setEmail("yen@gmail.com");

        userMapper.insertUser(user);
        User result = userMapper.selectByUsername(user.getUsername());
        Assertions.assertEquals(result.getUsername(), user.getUsername());
    }

    @Test
    @Rollback
    void testUpdateById() {
        User user = new User();
        user.setId(1);
        user.setUsername("言");
        user.setPassword("987654");
        user.setType(1);
        user.setPhoneNumber("0912345677");
        user.setEmail("yen@gmail.com");

        int i = userMapper.updateById(user);
        User result = userMapper.selectByUsername(user.getUsername());
        Assertions.assertEquals(result.getUsername(), user.getUsername());
    }

    @Test
    @Rollback
    void testDeleteById() {
        Integer id = 2;

        userMapper.deleteById(id);
        User result = userMapper.selectById(2);
        Assertions.assertNull(result);
    }

}
