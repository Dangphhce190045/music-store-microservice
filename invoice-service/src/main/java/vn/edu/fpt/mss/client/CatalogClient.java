package vn.edu.fpt.mss.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.fpt.mss.client.dto.TrackDto;

@FeignClient(name = "catalog-service", url = "${application.services.catalog-service.url:http://localhost:8081}")
public interface CatalogClient {

    @GetMapping("/api/v1/tracks/{id}")
    TrackDto findTrackById(@PathVariable("id") Integer id);
}
