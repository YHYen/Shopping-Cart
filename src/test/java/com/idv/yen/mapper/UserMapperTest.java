package com.idv.yen.mapper;

import com.idv.yen.domain.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@MapperScan("com.idv.yen.mapper")
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectById() {
        User user = userMapper.selectById(2);
        System.out.println(user);
    }


}
