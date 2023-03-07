package ruproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Materia;
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

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        materiaService = new MateriaServiceImpl(MateriaMapper.INSTANCE,materiaRepositroy);
    }

    @Test
    void getAllMaterias() {

        List<Materia> materias = Arrays.asList(new Materia(), new Materia(), new Materia());
        when(materiaRepositroy.findAll()).thenReturn(materias);

        List<MateriaDTO> materiaDTOS = materiaService.getAllMaterias();

        assertEquals(3,materiaDTOS.size());

    }

    @Test
    void getMateriaByName() {

        Materia materia = new Materia();
        materia.setName(NAME);
        materia.setId(ID);

        when(materiaRepositroy.findByName(anyString())).thenReturn(materia);

        MateriaDTO materiaDTO = materiaService.getMateriaByName(NAME);

        assertEquals(ID,materiaDTO.getId());
        assertEquals(NAME,materiaDTO.getName());
    }

    @Test
    void saveMateria() {

        MateriaDTO materia = new MateriaDTO();
        materia.setName(NAME);
        materia.setId(ID);

        when(materiaRepositroy.save(any(Materia.class))).then(returnsFirstArg());

        MateriaDTO materiaDTO = materiaService.saveMateria(materia);

        assertEquals(ID,materiaDTO.getId());
        assertEquals(NAME,materiaDTO.getName());


    }

    @Test
    void updateMateria() {

        MateriaDTO materia = new MateriaDTO();
        materia.setName(NAME);
        materia.setId(ID);

        when(materiaRepositroy.save(any(Materia.class))).then(returnsFirstArg());

        MateriaDTO materiaDTO = materiaService.updateMateria(NAME,materia);

        assertEquals(ID,materiaDTO.getId());
        assertEquals(NAME,materiaDTO.getName());
    }

    @Test
    void existsByName() {

        List<Materia> materias = Arrays.asList(new Materia(), new Materia(), new Materia());
        materias.get(0).setName(NAME);
        Boolean exists = materias.stream().filter(c -> c.getName().equals(NAME)).findFirst().isEmpty();
        when(materiaRepositroy.existsByName(anyString())).thenReturn(exists);

        Boolean test = materiaService.existsByName(NAME);

        assertFalse(test);

    }

    @Test
    void deleteMateria() {

        Materia materia = new Materia();
        materia.setName(NAME);

        materiaService.deleteMateria(NAME);

        verify(materiaRepositroy, times(1)).deleteByName(NAME);
    }
}