package it.epicode.lista_eventi.prenotazioni;

import it.epicode.lista_eventi.auth.AppUser;
import it.epicode.lista_eventi.eventi.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private AppUser utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private int postiPrenotati;
}