package ruproject.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Carrera;
import ruproject.domain.Materia;
import ruproject.exception.CarreraNotFoundException;
import ruproject.repositories.CarreraRepository;
import ruproject.repositories.MateriaRepositroy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @Mock
    MateriaRepositroy materiaRepositroy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carreraService = new CarreraServiceImpl(CarreraMapper.INSTANCE,carreraRepository, materiaRepositroy);
    }

    @Test //Used to test getAllCarreras() Mehtod
    void Add_3_Carreras_Returns_Number_Of_Carreras() {

        List<Carrera> carreras = Arrays.asList(new Carrera(), new Carrera(), new Carrera());
        when(carreraRepository.findAll()).thenReturn(carreras);

        List<CarreraDTO> carreraDTOS = carreraService.getAllCarreras();

        assertEquals(3,carreraDTOS.size());
    }

    @Test // Used to test getCarreraByName()
    void Add_Carrera_Returns_Same_Carrera() {

        Carrera carrera = new Carrera();
        carrera.setName(NAME);
        carrera.setId(ID);

        when(carreraRepository.findByName(anyString())).thenReturn(Optional.of(carrera));

        CarreraDTO carreraDTO = carreraService.getCarreraByName(NAME);

        assertEquals(ID,carreraDTO.getId());
        assertEquals(NAME,carreraDTO.getName());

    }

    @Test //Used To test saveCarrera()
    void Add_And_Save_Carrera_Returns_Same_Carrera_And_ID() {
        CarreraDTO carrera = new CarreraDTO();
        carrera.setName(NAME);
        carrera.setId(ID);
        when(carreraRepository.save(any(Carrera.class))).then(returnsFirstArg());


        CarreraDTO carreraDTO = carreraService.saveCarrera(carrera);

        assertNotNull(carreraDTO.getId());
        assertEquals(NAME,carreraDTO.getName());


    }

    @Test //Used to test updateCarrera()
    void Add_And_Update_Carrera_Retrun_Updated_Carrera_And_Same_ID() {

        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setName(NAME);
        carreraDTO.setId(ID);

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(1L);
        materiaDTO.setName("Fisica");

        List<MateriaDTO> materiaDTOS = new ArrayList<>();

        materiaDTOS.add(materiaDTO);

        carreraDTO.setMaterias(materiaDTOS);


        Carrera savedCarrera = new Carrera();
        savedCarrera.setName(NAME);
        savedCarrera.setId(ID);

        Materia materia = new Materia();
        materia.setId(1L);
        materia.setName("Fisica");



        when(carreraRepository.save(any(Carrera.class))).then(returnsFirstArg());
        when(carreraRepository.existsByName(anyString())).thenReturn(true);
        when(carreraRepository.findByName(anyString())).thenReturn(Optional.of(savedCarrera));
        when(materiaRepositroy.findByName(anyString())).thenReturn(Optional.of(materia));
        when(materiaRepositroy.existsByName(anyString())).thenReturn(true);


        CarreraDTO carreraDTO2 = carreraService.updateCarrera(NAME,carreraDTO);

        assertNotNull(carreraDTO2.getMaterias());
        assertEquals(NAME,carreraDTO2.getName());
        assertEquals(ID,carreraDTO.getId());
    }

    @Test //Used to test existsByName()
    void Add_Carrera_Search_By_Name_Returns_True_If_Found() {


        when(carreraRepository.existsByName(anyString())).thenReturn(true);

        Boolean test = carreraService.existsByName(NAME);

        assertTrue(test);

    }

    @Test //Used to Test deleteCarrera()
    void Delete_Carrera_Verrify_Delte_Times() {

        Carrera carrera = new Carrera();
        carrera.setName(NAME);

        carreraService.deleteCarrera(NAME);

        verify(carreraRepository, times(1)).deleteByName(NAME);

    }

    @Test
    public void CarreraNotFound_Exception_Assert_Throw(){

        CarreraNotFoundException exception = assertThrows(CarreraNotFoundException.class, ()-> {
            carreraService.getCarreraByName(NAME);
        });

        assertTrue(exception.getMessage().equals("Carrera with Name: Ingenieria not found"));

    }
}