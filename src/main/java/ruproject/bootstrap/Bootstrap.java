package ruproject.bootstrap;

import org.hibernate.annotations.Comment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ruproject.domain.Carrera;
import ruproject.repositories.CarreraRepository;
@Component
public class Bootstrap implements CommandLineRunner {

    private CarreraRepository carreraRepository;

    public Bootstrap(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Carrera ingenieria = new Carrera();
        ingenieria.setName("Ingenieria");

        Carrera politica = new Carrera();
        politica.setName("Politica");

        Carrera derecho = new Carrera();
        derecho.setName("Derecho");

        carreraRepository.save(ingenieria);
        carreraRepository.save(politica);
        carreraRepository.save(derecho);

        System.out.println("Data Test" + carreraRepository.count());
    }
}
