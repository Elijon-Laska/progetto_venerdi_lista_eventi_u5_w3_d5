package it.epicode.lista_eventi.prenotazioni;

import it.epicode.lista_eventi.eventi.Evento;
import it.epicode.lista_eventi.eventi.EventoRequest;
import it.epicode.lista_eventi.eventi.EventoService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
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
    private final EventoService eventoService;

    @PostMapping("/crea/{utenteId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> creaPrenotazione(@PathVariable Long utenteId, @RequestBody PrenotazioneRequest request) {
        try {
            return ResponseEntity.ok(prenotazioneService.creaPrenotazione(request, utenteId));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Errore nell'invio dell'email di conferma: " + e.getMessage());
        }
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
    @PostMapping("/crea/{organizzatoreId}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Evento> createEvento(@PathVariable Long organizzatoreId, @Valid @RequestBody EventoRequest request) {
        return ResponseEntity.ok(eventoService.createEvento(request, organizzatoreId));
    }
}