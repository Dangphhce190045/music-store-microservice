package vn.edu.fpt.mss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway — Single Entry Point cho toàn bộ MSS microservices.
 *
 * <p>Tất cả requests từ client đều đi qua Gateway trước khi được
 * route tới service phù hợp thông qua Eureka Service Discovery.
 *
 * <p>Chức năng chính:
 * <ul>
 *   <li>Reverse Proxy / Routing</li>
 *   <li>Load Balancing (lb:// URIs qua Eureka)</li>
 *   <li>JWT Authentication Filter (Phase 5)</li>
 *   <li>Global CORS Configuration</li>
 *   <li>Rate Limiting (Phase 6)</li>
 * </ul>
 *
 * <p>Port: 8080 | Eureka Dashboard: http://localhost:8761
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
