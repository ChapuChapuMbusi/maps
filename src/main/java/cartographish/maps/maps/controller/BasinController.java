package cartographish.maps.maps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.service.implementations.BasinServiceImpl;

@RestController
@RequestMapping("/api/external")
public class BasinController {
private final BasinServiceImpl externalBasinService;

    public BasinController(BasinServiceImpl externalBasinService) {
        this.externalBasinService = externalBasinService;
    }

    @GetMapping("/fetch-basins")
    public String fetchBasins() throws CustomException {
        externalBasinService.fetchAndSaveExternalBasins();
        return "Fetch dei bacini completato!";
    }
}
