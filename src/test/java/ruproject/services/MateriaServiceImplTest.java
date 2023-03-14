package ruproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.mapper.ContenidoMapper;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Materia;
import ruproject.repositories.CarreraRepository;
import ruproject.repositories.MateriaRepositroy;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class MateriaServiceImplTest {

    public static final Long ID = 1L;
    public static final String NAME = "Calculo";

    MateriaService materiaService;

    @Mock
    MateriaRepositroy materiaRepositroy;
    @Mock
    CarreraRepository carreraRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        materiaService = new MateriaServiceImpl(MateriaMapper.INSTANCE,materiaRepositroy, CarreraMapper.INSTANCE, carreraRepository, ContenidoMapper.INSTANCE);
    }

    @Test
    void Add_3_Materoas_Returns_Number_Of_Materias() {

        List<Materia> materias = Arrays.asList(new Materia(), new Materia(), new Materia());
        when(materiaRepositroy.findAll()).thenReturn(materias);

        List<MateriaDTO> materiaDTOS = materiaService.getAllMaterias();

        assertEquals(3,materiaDTOS.size());

    }

    @Test
    void Add_Materia_Return_Same_Materia() {

        Materia materia = new Materia();
        materia.setName(NAME);
        materia.setId(ID);

        when(materiaRepositroy.findByName(anyString())).thenReturn(materia);

        MateriaDTO materiaDTO = materiaService.getMateriaByName(NAME);

        assertEquals(ID,materiaDTO.getId());
        assertEquals(NAME,materiaDTO.getName());
    }

    @Test
    void Add_Save_Materia_Return_Same_Materia_ID() {

        MateriaDTO materia = new MateriaDTO();
        materia.setName(NAME);
        materia.setId(ID);

        when(materiaRepositroy.save(any(Materia.class))).then(returnsFirstArg());

        MateriaDTO materiaDTO = materiaService.saveMateria(materia);

        assertEquals(ID,materiaDTO.getId());
        assertEquals(NAME,materiaDTO.getName());


    }

    @Test
    void Add_Update_Materia_Return_Updated_Materia() {

        MateriaDTO materia = new MateriaDTO();
        materia.setName(NAME);
        materia.setId(ID);

        Materia savedMateria = new Materia();
        savedMateria.setName(NAME);
        savedMateria.setId(ID);

        when(materiaRepositroy.save(any(Materia.class))).then(returnsFirstArg());
        when(materiaRepositroy.existsByName(anyString())).thenReturn(true);
        when(materiaRepositroy.findByName(anyString())).thenReturn(savedMateria);
        MateriaDTO materiaDTO = materiaService.updateMateria(NAME,materia);

        assertEquals(ID,materiaDTO.getId());
        assertEquals(NAME,materiaDTO.getName());
    }

    @Test
    void Add_Materia_Retrun_True_If_Found() {

        when(materiaRepositroy.existsByName(anyString())).thenReturn(true);

        Boolean test = materiaService.existsByName(NAME);

        assertTrue(test);

    }

    @Test
    void Delete_Materia_Verify_deleteByName() {

        Materia materia = new Materia();
        materia.setName(NAME);

        materiaService.deleteMateria(NAME);

        verify(materiaRepositroy, times(1)).deleteByName(NAME);
    }
}