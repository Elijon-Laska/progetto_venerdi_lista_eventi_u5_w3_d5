package it.epicode.lista_eventi.utenti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utente")

public class utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


}