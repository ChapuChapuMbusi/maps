package cartographish.maps.maps.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cartographish.maps.maps.models.WaterBody;
import cartographish.maps.maps.service.implementations.ExternalApiServiceImpl;


@RestController
@RequestMapping("/api/external")
public class ExternalApiController {

    private final ExternalApiServiceImpl externalApiService;

    public ExternalApiController(ExternalApiServiceImpl externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/water-bodies")
    public ResponseEntity<List<WaterBody>> fetchAndSave(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        List<WaterBody> saved = externalApiService.fetchAndSavWaterBodies(lat, lon);
        return ResponseEntity.ok(saved);
    }
}
