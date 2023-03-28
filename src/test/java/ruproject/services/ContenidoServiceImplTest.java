package ruproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ruproject.api.v1.mapper.ContenidoMapper;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.domain.Contenido;
import ruproject.domain.Materia;
import ruproject.exception.ContenidoNotFoundException;
import ruproject.repositories.ContenidoRepositroy;
import ruproject.repositories.LibroRepository;
import ruproject.repositories.MateriaRepositroy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ContenidoServiceImplTest {

    public static final Long ID = 1L;

    ContenidoService contenidoService;

    @Mock
    ContenidoRepositroy contenidoRepositroy;
    @Mock
    MateriaRepositroy materiaRepositroy;

    @Mock
    LibroRepository libroRepository;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        contenidoService = new ContenidoServiceImpl(ContenidoMapper.INSTANCE, MateriaMapper.INSTANCE, contenidoRepositroy, materiaRepositroy, libroRepository);
    }
    @Test
    void Add_5_Contenidos_Returns_Number_Of_Contenidos() {
        List<Contenido> contenidos = Arrays.asList(new Contenido(),new Contenido(),new Contenido(), new Contenido(), new Contenido());
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setName("Fisica");
        materia.setContenidos(contenidos);

        when(materiaRepositroy.existsByName(anyString())).thenReturn(true);
        when(materiaRepositroy.findByName(anyString())).thenReturn(Optional.of(materia));

        List<ContenidoDTO> contenidoDTOList = contenidoService.getAllContenidosFromMateria("Fisica");

        assertEquals(5,contenidoDTOList.size());

    }

    @Test
    void Add_Contenido_Retrun_Contenido_Contenido_id() {

        Contenido contenido = new Contenido();
        contenido.setId(ID);
        List<Contenido> contenidos = new ArrayList<>();
        contenidos.add(contenido);
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setName("Fisica");
        materia.setContenidos(contenidos);

        when(materiaRepositroy.existsByName(anyString())).thenReturn(true);
        when(materiaRepositroy.findByName(anyString())).thenReturn(Optional.of(materia));
        when(contenidoRepositroy.findContenidoByIdAndMateriaId(anyLong(),anyLong())).thenReturn(Optional.of(contenido));

        ContenidoDTO contenidoDTO = contenidoService.getContenidoById(ID,"Fisica");

        assertEquals(ID,contenidoDTO.getId());
    }

    @Test
    void Add_And_Save_Contenido_Return_Contendio() {

        ContenidoDTO contenidoDTO = new ContenidoDTO();
        contenidoDTO.setId(ID);

        Materia materia = new Materia();
        materia.setName("Fisica");

        when(contenidoRepositroy.save(any(Contenido.class))).then(returnsFirstArg());
        when(materiaRepositroy.findByName(anyString())).thenReturn(Optional.of(materia));

        ContenidoDTO contenidoDTO1 = contenidoService.saveContenido(contenidoDTO,materia.getName());

        assertNotNull(contenidoDTO1.getId());
    }

    @Test
    void Delete_Contenido() {

        Contenido contenido = new Contenido();
        contenido.setId(ID);

        contenidoService.deleteContenido(ID);

        verify(contenidoRepositroy, times(1)).deleteById(ID);

    }

    @Test
    void ContenidoNotFound_Exception_Assert_Throw(){

        Materia materia = new Materia();
        materia.setName("Fisica");
        materia.setId(ID);

        when(materiaRepositroy.findByName(anyString())).thenReturn(Optional.of(materia));

        ContenidoNotFoundException exception = assertThrows(ContenidoNotFoundException.class, () -> {
            contenidoService.getContenidoById(ID, "Fisica");
        });

        assertTrue(exception.getMessage().equals("Contenido with Id: 1 not found"));

    }
}