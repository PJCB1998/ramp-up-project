package ruproject.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;
import ruproject.repositories.CarreraRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CarreraServiceTest {


    public static final Long ID = 5L;
    public static final String NAME = "Ingenieria";

    CarreraService carreraService;

    @Mock
    CarreraRepository carreraRepository;
    CarreraMapper carreraMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carreraService = new CarreraServiceImpl(CarreraMapper.INSTANCE,carreraRepository);
    }

    @Test
    void getAllCarreras() {

        List<Carrera> carreras = Arrays.asList(new Carrera(), new Carrera(), new Carrera());
        when(carreraRepository.findAll()).thenReturn(carreras);

        List<CarreraDTO> carreraDTOS = carreraService.getAllCarreras();

        assertEquals(3,carreraDTOS.size());
    }

    @Test
    void getCarreraByName() {

        Carrera carrera = new Carrera();
        carrera.setName(NAME);
        carrera.setId(ID);

        when(carreraRepository.findByName(anyString())).thenReturn(carrera);

        CarreraDTO carreraDTO = carreraService.getCarreraByName(NAME);

        assertEquals(ID,carreraDTO.getId());
        assertEquals(NAME,carreraDTO.getName());

    }

    @Test
    void saveCarrera() {
        Carrera carrera = new Carrera();
        carrera.setName(NAME);
        carrera.setId(ID);
        when(carreraRepository.save(any(Carrera.class))).then(returnsFirstArg());


        CarreraDTO carreraDTO = carreraService.saveCarrera(carrera);

        assertNotNull(carreraDTO.getId());
        assertEquals(NAME,carreraDTO.getName());


    }

    @Test
    void updateCarera() {

        Carrera carrera = new Carrera();
        carrera.setName(NAME);
        carrera.setId(ID);
        when(carreraRepository.save(any(Carrera.class))).then(returnsFirstArg());


        CarreraDTO carreraDTO = carreraService.updateCarera(carrera);

        assertNotNull(carreraDTO.getId());
        assertEquals(NAME,carreraDTO.getName());
    }

    @Test
    void existsByName() {

        List<Carrera> carreras = Arrays.asList(new Carrera(), new Carrera(), new Carrera());
        carreras.get(0).setName(NAME);
        Boolean exists = carreras.stream().filter(c -> c.getName().equals(NAME)).findFirst().isEmpty();
        when(carreraRepository.existsByName(anyString())).thenReturn(exists);

        Boolean test = carreraService.existsByName(NAME);

        assertFalse(test);

    }

    @Test
    void deleteCarrera() {

        Carrera carrera = new Carrera();
        carrera.setName(NAME);

        carreraService.deleteCarrera(NAME);

        verify(carreraRepository, times(1)).deleteByName(NAME);

    }
}