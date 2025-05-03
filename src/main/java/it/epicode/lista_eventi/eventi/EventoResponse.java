package it.epicode.lista_eventi.eventi;

import lombok.Data;

@Data
public class EventoResponse {
    private Long id;
    private String titolo;
    private String descrizione;
    private String luogo;
    private int postiDisponibili;
}