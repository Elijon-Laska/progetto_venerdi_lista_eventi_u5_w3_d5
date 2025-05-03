package it.epicode.lista_eventi.eventi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PostMapping("/crea/{organizzatoreId}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Evento> createEvento(@PathVariable Long organizzatoreId, @RequestBody EventoRequest request) {
        return ResponseEntity.ok(eventoService.createEvento(request, organizzatoreId));
    }

    @GetMapping
    public ResponseEntity<List<Evento>> getAllEventi() {
        return ResponseEntity.ok(eventoService.getAllEventi());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.getEventoById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
        return ResponseEntity.ok("Evento eliminato con successo!");
    }
}