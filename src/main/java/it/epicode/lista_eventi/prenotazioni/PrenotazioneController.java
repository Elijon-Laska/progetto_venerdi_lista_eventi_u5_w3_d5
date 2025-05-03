package it.epicode.lista_eventi.prenotazioni;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @PostMapping("/crea/{utenteId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Prenotazione> creaPrenotazione(@PathVariable Long utenteId, @RequestBody PrenotazioneRequest request) {
        return ResponseEntity.ok(prenotazioneService.creaPrenotazione(request, utenteId));
    }

    @GetMapping("/utente/{utenteId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Prenotazione>> getPrenotazioniUtente(@PathVariable Long utenteId) {
        return ResponseEntity.ok(prenotazioneService.getPrenotazioniUtente(utenteId));
    }

    @DeleteMapping("/{prenotazioneId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> cancellaPrenotazione(@PathVariable Long prenotazioneId) {
        prenotazioneService.cancellaPrenotazione(prenotazioneId);
        return ResponseEntity.ok("Prenotazione cancellata con successo!");
    }
}