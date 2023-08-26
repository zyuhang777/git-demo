package com.zyh.smartApi;

import com.zyh.apiclient.httpUtil.HelloHttpClient;
import com.zyh.apiclient.model.User;
import com.zyh.smartApi.config.WxOpenConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
class MainApplicationTests {

    @Resource
    private WxOpenConfig wxOpenConfig;
    @Autowired
    private HelloHttpClient helloHttpClient;

    @Test
    void contextLoads() {
       User user =  new User();
       user.setAge("11");
       user.setName("ababa");
       helloHttpClient.httpPostHello(user);
    }

}
