package it.epicode.lista_eventi.eventi;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoRequest {
    private String titolo;
    private String descrizione;
    private LocalDateTime data;
    private String luogo;
    private int postiDisponibili;
}