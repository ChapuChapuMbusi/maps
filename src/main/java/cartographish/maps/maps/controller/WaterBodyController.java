package cartographish.maps.maps.controller;

import cartographish.maps.maps.dto.WaterBodyDTO;
import cartographish.maps.maps.exception.CustomException;
import cartographish.maps.maps.service.interfaces.IWaterBodyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maps/water-bodies")
public class WaterBodyController {

    private final IWaterBodyService waterBodyService;

    public WaterBodyController(IWaterBodyService waterBodyService) {
        this.waterBodyService = waterBodyService;
    }

    // Riceve tutte le acque registrate
    @GetMapping
    public ResponseEntity<List<WaterBodyDTO>> getAll() {
        return ResponseEntity.ok(waterBodyService.getAllWaterBodies());
    }

    // Ricerca un tratto d'acqua specifico per ID
    @GetMapping("/{id}")
    public ResponseEntity<WaterBodyDTO> getById(@PathVariable Integer id) throws CustomException {
        // Aggiunto 'throws CustomException' per gestire l'eccezione controllata del servizio
        return ResponseEntity.ok(waterBodyService.getWaterBodyById(id));
    }

    // Ricerca testuale parziale (es: "Tevere", "Po")
    @GetMapping("/search")
    public ResponseEntity<List<WaterBodyDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(waterBodyService.searchByPartialName(name));
    }
}