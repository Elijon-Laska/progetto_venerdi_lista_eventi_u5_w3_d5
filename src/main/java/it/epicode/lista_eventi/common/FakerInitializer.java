package it.epicode.lista_eventi.common;

import com.github.javafaker.Faker;
import it.epicode.lista_eventi.auth.AppUser;
import it.epicode.lista_eventi.auth.AppUserRepository;
import it.epicode.lista_eventi.auth.Role;
import it.epicode.lista_eventi.eventi.Evento;
import it.epicode.lista_eventi.eventi.EventoRepository;
import it.epicode.lista_eventi.prenotazioni.Prenotazione;
import it.epicode.lista_eventi.prenotazioni.PrenotazioneRepository;
import it.epicode.lista_eventi.email.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class FakerInitializer implements CommandLineRunner {

    private final EventoRepository eventoRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final AppUserRepository appUserRepository;
    private final EmailSenderService emailSenderService;
    private final Faker faker;

    @Override
    public void run(String... args) {
        System.out.println("🔄 Generazione eventi e prenotazioni casuali...");

        // Creiamo 10 organizzatori (AppUser con ruolo SELLER)
        List<AppUser> organizzatori = IntStream.range(0, 10)
                .mapToObj(i -> {
                    AppUser user = new AppUser();
                    user.setUsername(faker.name().username());
                    user.setPassword("password123");
                    user.setRoles(Set.of(Role.ROLE_SELLER)); // Ruolo corretto
                    return user;
                })

                .toList();

        appUserRepository.saveAll(organizzatori);

        // Creiamo 10 eventi casuali
        List<Evento> eventi = IntStream.range(0, 10)
                .mapToObj(i -> new Evento(
                        null, // ID verrà generato automaticamente
                        faker.book().title(),
                        faker.lorem().sentence(10),
                        LocalDateTime.now().plusDays(faker.number().numberBetween(5, 30)), // Data futura
                        faker.address().city(),
                        faker.number().numberBetween(50, 200), // Posti disponibili
                        organizzatori.get(i) // Organizzatore casuale
                ))
                .toList();

        eventoRepository.saveAll(eventi);

        // Creiamo 10 prenotazioni casuali per gli eventi
        List<Prenotazione> prenotazioni = IntStream.range(0, 10)
                .mapToObj(i -> {
                    Evento evento = eventi.get(faker.number().numberBetween(0, 9)); // Evento casuale
                    AppUser utente = organizzatori.get(faker.number().numberBetween(0, 9)); // Utente casuale
                    int postiPrenotati = faker.number().numberBetween(1, 5);

                    // Creiamo la prenotazione
                    Prenotazione prenotazione = new Prenotazione(null, utente, evento, postiPrenotati);

                    // Aggiorniamo i posti disponibili dell'evento
                    evento.setPostiDisponibili(evento.getPostiDisponibili() - postiPrenotati);
                    eventoRepository.save(evento);

                    // INVIO EMAIL DI CONFERMA
                    try {
                        emailSenderService.sendEmail(
                                "elijonlaska95@gmail.com",
                                "Conferma Prenotazione",
                                "✅ Hai prenotato " + postiPrenotati + " posti per l'evento '" + evento.getTitolo() + "'"
                        );
                    } catch (MessagingException e) {
                        System.err.println("Errore nell'invio dell'email: " + e.getMessage());
                    }

                    return prenotazione;
                })
                .toList();

        prenotazioneRepository.saveAll(prenotazioni);

        System.out.println("✅ 10 eventi e 10 prenotazioni generati con successo!");
    }
}
