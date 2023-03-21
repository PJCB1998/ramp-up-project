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
import ruproject.api.v1.model.MateriaDTO;
import ruproject.services.MateriaService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MateriaControllerTest {

    public static final String NAME = "Calculo";

    @Mock
    MateriaService materiaService;

    @InjectMocks
    MateriaController materiaController;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mvc= MockMvcBuilders.standaloneSetup(materiaController).build();
    }

    @Test //Test GET Materias
    void Add_2_Materias_Return_Number_Of_Materias_Http_200() throws Exception {
        MateriaDTO materia1 = new MateriaDTO();
        materia1.setId(1L);
        materia1.setName(NAME);
        MateriaDTO materia2 = new MateriaDTO();
        materia2.setId(2L);
        materia2.setName("Fisica");

        List<MateriaDTO> materias = Arrays.asList(materia1,materia2);

        when(materiaService.getAllMaterias()).thenReturn(materias);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/materias/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.materias",hasSize(2)));
    }

    @Test // Test GET Materia Name
    void Add_Materia_Retrun_Materia_Name_Http_200() throws Exception {

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setName(NAME);
        materiaDTO.setId(1L);

        when(materiaService.getMateriaByName(anyString())).thenReturn(materiaDTO);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/materias/Calculo/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(NAME)));
    }

    @Test // Test POST Materia
    void Add_Save_Materia_Return_Materia_ID_Http_201() throws Exception {

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(1L);
        materiaDTO.setName(NAME);

        MateriaDTO returnDTO = new MateriaDTO();
        returnDTO.setName(materiaDTO.getName());
        returnDTO.setId(materiaDTO.getId());

        when(materiaService.saveMateria(any(MateriaDTO.class))).thenReturn(returnDTO);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/materias/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(materiaDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",equalTo(1)))
                .andExpect(jsonPath("$.name",equalTo(NAME)));

    }

    @Test // Test PUT Materia
    void Add_Update_Materia_Return_Materia_Http_202() throws Exception {

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(1L);
        materiaDTO.setName(NAME);

        MateriaDTO returnDTO = new MateriaDTO();
        returnDTO.setName(materiaDTO.getName());
        returnDTO.setId(materiaDTO.getId());

        when(materiaService.existsByName(anyString())).thenReturn(true);

        when(materiaService.updateMateria(anyString(),any(MateriaDTO.class))).thenReturn(returnDTO);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/materias/Calculo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(materiaDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name",equalTo(NAME)))
                .andExpect(jsonPath("$.id",equalTo(1)));

    }

    @Test // Test DELETE Materia
    void Delete_Materia_Verify_deleteMateria_Http_204() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/materias/Calculo/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(materiaService).deleteMateria(anyString());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}