package com.idv.yen;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.idv.yen.dao")
class ShoppingCartApplicationTests {

    @Test
    void contextLoads() {

    }

}
