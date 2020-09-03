package com.sakura.user;

import com.sakura.security.annotation.EnableSakuraFeignClients;
import com.sakura.security.annotation.EnableSakuraResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author Mr.Zhou
 */
@EnableSakuraResourceServer
@EnableSakuraFeignClients
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
