package it.epicode.lista_eventi.eventi;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoRequest {
    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;
    @NotBlank(message = "La descrizione è obbligatoria")
    @Size(max = 500, message = "La descrizione non può superare 500 caratteri")
    private String descrizione;
    @NotBlank(message = "La data è obbligatoria")
    private LocalDateTime data;
    @NotBlank(message = "Il luogo è obbligatorio")
    private String luogo;
    @Min(value = 1, message = "Devi inserire almeno 1 posto disponibile")
    private int postiDisponibili;

}