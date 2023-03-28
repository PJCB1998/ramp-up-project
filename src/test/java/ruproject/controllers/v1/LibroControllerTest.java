package ruproject.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ruproject.api.v1.model.LibroDTO;
import ruproject.exception.GlobalControllerAdvisor;
import ruproject.exception.LibroControllerAdvisor;
import ruproject.exception.LibroNotFoundException;
import ruproject.services.LibroService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class LibroControllerTest {

    public static final Long ID = 1L;

    @Mock
    LibroService libroService;

    @InjectMocks
    LibroController libroController;

    MockMvc mvc;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(libroController).setControllerAdvice(new LibroControllerAdvisor(),new GlobalControllerAdvisor()).build();
    }

    @Test
    void Add_2_Libros_Return_Number_Of_Libros() throws Exception {

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(ID);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");

        LibroDTO libroDTO2 = new LibroDTO();
        libroDTO2.setId(2L);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");

        List<LibroDTO>  libroDTOList = Arrays.asList(libroDTO,libroDTO2);

        when(libroService.getAllLibros()).thenReturn(libroDTOList);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/libros/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libros",hasSize(2)));
    }

    @Test
    void Add_Libro_Retrun_LibroId() throws Exception {

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(ID);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");


        when(libroService.getLibroById(anyLong())).thenReturn(libroDTO);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/libros/1/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(1)));

    }

    @Test
    void Add_2_Libros_Retrun_Number_Of_Libros_In_Contenido() throws Exception {

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(ID);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");


        LibroDTO libroDTO2 = new LibroDTO();
        libroDTO2.setId(2L);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");

        List<LibroDTO>  libroDTOList = Arrays.asList(libroDTO,libroDTO2);

        when(libroService.getAllLibrosFromContenido(anyLong(),anyString())).thenReturn(libroDTOList);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/materias/materia/contenidos/1/libros/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libros",hasSize(2)));

    }

    @Test
    void Add_1_Libro_Retrun_LibroId_From_Contenido() throws Exception {

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(ID);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");


        when(libroService.getLibroByIdFromContenido(anyLong(),anyLong(),anyString())).thenReturn(libroDTO);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/materias/materia/contenidos/1/libros/1/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(1)));


    }

    @Test
    void Add_And_Create_1_Libro() throws Exception {

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(ID);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");



        when(libroService.saveLibro(any(LibroDTO.class))).thenReturn(libroDTO);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/libros/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(libroDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",equalTo(1)));


    }

    @Test
    void Add_And_Update_Libro() throws Exception {

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(ID);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");



        when(libroService.updateLibro(anyLong(),any(LibroDTO.class))).thenReturn(libroDTO);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/libros/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(libroDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id",equalTo(1)));

    }

    @Test
    void Delete_Libro() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/libros/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(libroService).deleteLibro(anyLong());


    }

    @Test
    void Test_LibroNotFound_Exception() throws Exception{

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(ID);
        libroDTO.setAutor("Autor");
        libroDTO.setTitulo("Titulo");

        when(libroService.getLibroById(anyLong())).thenThrow(new LibroNotFoundException(ID));

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/libros/1/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof LibroNotFoundException))
                .andExpect(jsonPath("$.message").value("Libro with Id: 1 not found"));

    }

    @Test
    void Test_InvalidArgument_Exception_Http_422() throws Exception{

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(ID);
        libroDTO.setAutor("Autor");

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/libros/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(libroDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}