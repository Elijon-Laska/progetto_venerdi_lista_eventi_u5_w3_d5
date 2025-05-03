package it.epicode.lista_eventi.eventi;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByOrganizzatoreId(Long organizzatoreId);

}