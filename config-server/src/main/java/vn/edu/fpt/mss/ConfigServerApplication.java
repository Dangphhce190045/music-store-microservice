package vn.edu.fpt.mss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Spring Cloud Config Server — Centralized configuration repository for all MSS microservices.
 *
 * <p>Profiles supported:
 * <ul>
 *   <li>native (loads configurations from local folder or classpath)</li>
 *   <li>git (loads configurations from remote Git repository)</li>
 * </ul>
 *
 * <p>Port: 8888 | Registered on Eureka: http://localhost:8761
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
