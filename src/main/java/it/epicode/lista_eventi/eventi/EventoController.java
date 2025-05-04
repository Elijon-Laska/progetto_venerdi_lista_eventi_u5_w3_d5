package it.epicode.lista_eventi.eventi;

import it.epicode.lista_eventi.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;
    private final EventoRepository eventoRepository;
    private final CloudinaryService cloudinaryService;


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

    @GetMapping("/organizzatore/{organizzatoreId}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<List<Evento>> getEventiOrganizzatore(@PathVariable Long organizzatoreId) {
        return ResponseEntity.ok(eventoRepository.findByOrganizzatoreId(organizzatoreId));
    }

    @PostMapping("/{eventoId}/upload-immagine")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> uploadImmagine(@PathVariable Long eventoId, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.uploadImage(file);
            eventoService.updateEventoImage(eventoId, imageUrl);
            return ResponseEntity.ok("✅ Immagine caricata con successo: " + imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Errore nell'upload dell'immagine: " + e.getMessage());
        }
    }

}