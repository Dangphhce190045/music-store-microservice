package vn.edu.fpt.mss.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.fpt.mss.client.dto.CustomerDto;

@FeignClient(name = "customer-service", url = "${application.services.customer-service.url:http://localhost:8082}")
public interface CustomerClient {

    @GetMapping("/api/v1/customers/{id}")
    CustomerDto findById(@PathVariable("id") Integer id);
}
