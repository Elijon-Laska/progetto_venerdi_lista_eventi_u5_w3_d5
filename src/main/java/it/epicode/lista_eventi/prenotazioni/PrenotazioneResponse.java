package it.epicode.lista_eventi.prenotazioni;

import lombok.Data;
import it.epicode.lista_eventi.eventi.Evento;

@Data
public class PrenotazioneResponse {
    private Long id;
    private Evento evento;
    private int postiPrenotati;
}