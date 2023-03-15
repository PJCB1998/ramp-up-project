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
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.services.ContenidoService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContenidoControllerTest {

    public static final Long ID = 1L;

    @Mock
    ContenidoService contenidoService;

    @InjectMocks
    ContenidoController contenidoController;

    MockMvc mvc;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(contenidoController).build();
    }

    @Test
    void Add_2_Contenidos_Return_Contenido_Size_Http_200() throws Exception {
        ContenidoDTO contenidoDTO1 = new ContenidoDTO();
        contenidoDTO1.setId(ID);

        ContenidoDTO contenidoDTO2 = new ContenidoDTO();
        contenidoDTO2.setId(2L);

        List<ContenidoDTO> contenidoDTOList = Arrays.asList(contenidoDTO1,contenidoDTO2);

        when(contenidoService.getAllContenidosFromMateria(anyString())).thenReturn(contenidoDTOList);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/materias/materia/contenidos/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenidos",hasSize(2)));


    }

    @Test
    void Add_1_Contenido_Return_Same_ContenidoId_Http_200() throws Exception {

        ContenidoDTO contenidoDTO1 = new ContenidoDTO();
        contenidoDTO1.setId(ID);

        when(contenidoService.getContenidoById(anyLong(),anyString())).thenReturn(contenidoDTO1);


        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/materias/materia/contenidos/1/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(1)));

    }

    @Test
    void Add_Contenido_Return_Same_ContenidoId_Http_201() throws Exception {

        ContenidoDTO contenidoDTO = new ContenidoDTO();
        contenidoDTO.setId(ID);


        when(contenidoService.saveContenido(any(ContenidoDTO.class),anyString())).then(returnsFirstArg());

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/materias/materia/contenidos/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contenidoDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",equalTo(1)));


    }

    @Test
    void Add_And_Update_Contenido_Http_202() throws Exception {

        ContenidoDTO contenidoDTO = new ContenidoDTO();
        contenidoDTO.setId(ID);

        when(contenidoService.updateContenido(anyLong(),anyString(),any(ContenidoDTO.class))).thenReturn(contenidoDTO);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/materias/materia/contenidos/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contenidoDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id",equalTo(1)));

    }

    @Test
    void Delete_Contenido_Http_204() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/materias/materia/contenidos/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(contenidoService).deleteContenido(anyLong());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}