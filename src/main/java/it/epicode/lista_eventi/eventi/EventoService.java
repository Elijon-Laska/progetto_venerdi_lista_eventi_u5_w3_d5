package it.epicode.lista_eventi.eventi;

import it.epicode.lista_eventi.auth.AppUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    public Evento createEvento(EventoRequest request, Long organizzatoreId) {
        Evento evento = new Evento();
        evento.setTitolo(request.getTitolo());
        evento.setDescrizione(request.getDescrizione());
        evento.setData(request.getData());
        evento.setLuogo(request.getLuogo());
        evento.setPostiDisponibili(request.getPostiDisponibili());
        evento.setOrganizzatore(new AppUser(organizzatoreId));
        return eventoRepository.save(evento);
    }

    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    public Evento getEventoById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato con ID: " + id));
    }

    public void deleteEvento(Long id) {
        Evento evento = getEventoById(id);
        eventoRepository.delete(evento);
    }

    public Evento salvaEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void updateEventoImage(Long eventoId, String imageUrl) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato con ID: " + eventoId));
        evento.setImmagineUrl(imageUrl);
        eventoRepository.save(evento);

    }
}

