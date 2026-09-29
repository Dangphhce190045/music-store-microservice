package vn.edu.fpt.mss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Service Registry — trung tâm đăng ký và khám phá service.
 *
 * <p>Tất cả microservices đăng ký vào đây khi khởi động.
 * Gateway dùng Eureka để resolve địa chỉ service theo tên
 * thay vì hardcode URL (Load Balancing tự động).
 *
 * <p>Dashboard: http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
