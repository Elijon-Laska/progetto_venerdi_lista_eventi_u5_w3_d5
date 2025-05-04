package it.epicode.lista_eventi.prenotazioni;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrenotazioneRequest {
    @NotNull(message = "L'evento è obbligatorio")
    private Long eventoId;

    @Min(value = 1, message = "Devi prenotare almeno un posto")
    private int postiPrenotati;
}