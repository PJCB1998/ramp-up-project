package ruproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ruproject.api.v1.mapper.LibroMapper;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.api.v1.model.LibroDTO;
import ruproject.domain.Contenido;
import ruproject.domain.Libro;
import ruproject.domain.Materia;
import ruproject.exception.LibroNotFoundException;
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

class LibroServiceImplTest {


    public static final Long ID = 1L;

    public static final String TITULO = "Libito";

    public static final String AUTOR = "William";

    LibroService libroService;

    @Mock
    ContenidoRepositroy contenidoRepositroy;

    @Mock
    MateriaRepositroy materiaRepositroy;

    @Mock
    LibroRepository libroRepository;




    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        libroService = new LibroServiceImpl(contenidoRepositroy,libroRepository,materiaRepositroy, LibroMapper.INSTANCE);


    }

    @Test
    void Add_3_Libros_To_A_Contenido_Return_Number_Of_Libros() {

        List<Libro> libros = Arrays.asList(new Libro(),new Libro(),new Libro());

        Contenido contenido = new Contenido();
        contenido.setId(1L);
        contenido.setLibros(libros);

        Materia materia = new Materia();
        materia.setName("Fisica");
        materia.setId(1L);

        when(contenidoRepositroy.existsById(anyLong())).thenReturn(true);
        when(contenidoRepositroy.findContenidoByIdAndMateriaId(anyLong(),anyLong())).thenReturn(Optional.of(contenido));
        when(materiaRepositroy.findByName(anyString())).thenReturn(Optional.of(materia));

        List<LibroDTO> libroDTOS = libroService.getAllLibrosFromContenido(contenido.getId(),materia.getName());

        assertEquals(3,libroDTOS.size());




    }

    @Test
    void Add_1_Libro_To_A_Contenido_Return_LibroId_And_Titulo() {

        Libro libro = new Libro();
        libro.setAutor(AUTOR);
        libro.setTitulo(TITULO);
        libro.setId(ID);

        List<Libro> libros = new ArrayList<>();

        libros.add(libro);

        Contenido contenido = new Contenido();
        contenido.setId(1L);
        contenido.setLibros(libros);

        Materia materia = new Materia();
        materia.setName("Fisica");
        materia.setId(1L);

        when(materiaRepositroy.existsByName(anyString())).thenReturn(true);
        when(materiaRepositroy.findByName(anyString())).thenReturn(Optional.of(materia));
        when(libroRepository.findLibroByIdAndContenidoIdAndMateriaId(anyLong(),anyLong(),anyLong())).thenReturn(Optional.of(libro));

        LibroDTO libroDTO = libroService.getLibroByIdFromContenido(libro.getId(), contenido.getId(),materia.getName());

        assertEquals(ID, libroDTO.getId());
        assertEquals(TITULO, libroDTO.getTitulo());

    }

    @Test
    void Add_5_Libros_Retrun_Number_Of_Libros() {

        List<Libro> libros = Arrays.asList(new Libro(),new Libro(),new Libro());

        when(libroRepository.findAll()).thenReturn(libros);

        List<LibroDTO> libroDTOS = libroService.getAllLibros();

        assertEquals(3,libroDTOS.size());


    }

    @Test
    void Add_1_Libro_Retrun_LibroId_And_Title() {

        Libro libro = new Libro();
        libro.setAutor(AUTOR);
        libro.setTitulo(TITULO);
        libro.setId(ID);

        when(libroRepository.findById(anyLong())).thenReturn(Optional.of(libro));

        LibroDTO libroDTO = libroService.getLibroById(libro.getId());

        assertEquals(ID,libroDTO.getId());
        assertEquals(TITULO,libroDTO.getTitulo());



    }

    @Test
    void Add_And_Save_1_Libro() {

        LibroDTO libro = new LibroDTO();
        libro.setAutor(AUTOR);
        libro.setTitulo(TITULO);
        libro.setId(ID);

        when(libroRepository.save(any(Libro.class))).then(returnsFirstArg());

        LibroDTO libroDTO = libroService.saveLibro(libro);

        assertEquals(ID,libroDTO.getId());
        assertEquals(TITULO,libroDTO.getTitulo());


    }

    @Test
    void Add_And_Update_Libro_Return_Updated_Libro_SameId() {


        List<ContenidoDTO> contenidoDTOS = new ArrayList<>();
        ContenidoDTO contenidoDTO = new ContenidoDTO();
        contenidoDTO.setId(1L);
        contenidoDTOS.add(contenidoDTO);



        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setTitulo(TITULO);
        libroDTO.setAutor(AUTOR);
        libroDTO.setId(ID);
        libroDTO.setContenidos(contenidoDTOS);


        Libro libro = new Libro();
        libro.setTitulo(TITULO);
        libro.setAutor(AUTOR);
        libro.setId(ID);


        Contenido contenido = new Contenido();
        contenido.setId(1L);


        when(libroRepository.findById(anyLong())).thenReturn(Optional.of(libro));
        when(libroRepository.existsById(anyLong())).thenReturn(true);
        when(libroRepository.save(any(Libro.class))).then(returnsFirstArg());
        when(contenidoRepositroy.existsById(anyLong())).thenReturn(true);
        when(contenidoRepositroy.findById(anyLong())).thenReturn(Optional.of(contenido));

        LibroDTO libroDTO1 = libroService.updateLibro(ID,libroDTO);

        assertEquals(ID,libroDTO1.getId());
        assertNotNull(libroDTO1.getContenidos());


    }


    @Test
    void Delete_Libro() {

        Libro libro = new Libro();
        libro.setTitulo(TITULO);
        libro.setAutor(AUTOR);
        libro.setId(ID);

        libroService.deleteLibro(ID);

        verify(libroRepository,times(1)).deleteById(ID);



    }

    @Test
    void LibroNotFound_Exception_AssertThrow(){


        LibroNotFoundException exception = assertThrows(LibroNotFoundException.class, () -> {
            libroService.getLibroById(ID);
        });

        assertEquals(exception.getMessage(),"Libro with Id: 1 not found");

    }
}