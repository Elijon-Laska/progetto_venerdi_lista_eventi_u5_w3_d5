package it.epicode.lista_eventi.prenotazioni;

import it.epicode.lista_eventi.auth.AppUser;
import it.epicode.lista_eventi.eventi.Evento;
import it.epicode.lista_eventi.eventi.EventoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoService eventoService;

    public Prenotazione creaPrenotazione(PrenotazioneRequest request, Long utenteId) {
        Evento evento = eventoService.getEventoById(request.getEventoId());

        if (evento.getPostiDisponibili() < request.getPostiPrenotati()) {
            throw new IllegalArgumentException("Posti non disponibili per questo evento.");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(new AppUser(utenteId));
        prenotazione.setEvento(evento);
        prenotazione.setPostiPrenotati(request.getPostiPrenotati());

        evento.setPostiDisponibili(evento.getPostiDisponibili() - request.getPostiPrenotati());
        eventoService.salvaEvento(evento);

        return prenotazioneRepository.save(prenotazione);
    }

    public List<Prenotazione> getPrenotazioniUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }

    public void cancellaPrenotazione(Long prenotazioneId) {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata."));

        Evento evento = prenotazione.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + prenotazione.getPostiPrenotati());
        eventoService.salvaEvento(evento);

        prenotazioneRepository.delete(prenotazione);
    }
}