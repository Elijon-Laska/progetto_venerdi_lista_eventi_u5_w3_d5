package it.epicode.lista_eventi.prenotazioni;

import lombok.Data;

@Data
public class PrenotazioneRequest {
    private Long eventoId;
    private int postiPrenotati;
}