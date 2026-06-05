package cartographish.maps.maps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.service.interfaces.IBasinService; // Importiamo l'interfaccia

@RestController
@RequestMapping("/api/external")
public class BasinController {

    // Modificato da BasinServiceImpl a IBasinService
    private final IBasinService externalBasinService;

    // Anche il costruttore ora accetta l'interfaccia
    public BasinController(IBasinService externalBasinService) {
        this.externalBasinService = externalBasinService;
    }

    @GetMapping("/fetch-basins")
    public String fetchBasins() throws CustomException {
        externalBasinService.fetchAndSaveExternalBasins();
        return "Fetch dei bacini completato!";
    }
}