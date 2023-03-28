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
import ruproject.api.v1.model.CarreraDTO;
import ruproject.exception.CarreraControllerAdvisor;
import ruproject.exception.CarreraNotFoundException;
import ruproject.exception.GlobalControllerAdvisor;
import ruproject.services.CarreraService;



import java.util.Arrays;
import java.util.List;



import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarreraControllerTest {

    public static final String NAME = "Ingenieria";

    @Mock
    CarreraService carreraService;


    @InjectMocks
    CarreraController carreraController;

    MockMvc mvc;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(carreraController).setControllerAdvice(new CarreraControllerAdvisor(),new GlobalControllerAdvisor()).build();
    }

    @Test // Used to test GET All Carreras
    void Add_2_Carreras_Return_Carrera_Size_Http_200() throws Exception {
        CarreraDTO carrera1 = new CarreraDTO();
        carrera1.setName(NAME);
        carrera1.setId(1L);
        CarreraDTO carrera2 = new CarreraDTO();
        carrera2.setName("Medicina");
        carrera2.setId(2L);

        List<CarreraDTO> carreras = Arrays.asList(carrera1,carrera2);

        when(carreraService.getAllCarreras()).thenReturn(carreras);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/carreras/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carreras",hasSize(2)));

    }

    @Test //Used to test GET Carreras by Name
    void Add_Carrera_Return_Same_Carrera_Name_Http_200() throws Exception {

        CarreraDTO carrera1 = new CarreraDTO();
        carrera1.setName(NAME);
        carrera1.setId(1L);

        when(carreraService.getCarreraByName(anyString())).thenReturn(carrera1);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/carreras/Ingenieria/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(NAME)));


    }

    @Test // Used to test POST Carrera
    void Add_Carrera_Return_Same_Carrera_Name_ID_Http_201() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId(1L);
        carreraDTO.setName(NAME);

        CarreraDTO returnDTO = new CarreraDTO();
        returnDTO.setName(carreraDTO.getName());
        returnDTO.setId((carreraDTO.getId()));

        when(carreraService.saveCarrera(any(CarreraDTO.class))).thenReturn(returnDTO);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/carreras/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carreraDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",equalTo(1)))
                .andExpect(jsonPath("$.name",equalTo(NAME)));


    }

    @Test //Used to test PUT Carrera
    void Add_Carrera_Retrun_Same_Carrera_Http_202() throws Exception {

        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId(1L);
        carreraDTO.setName(NAME);

        CarreraDTO returnDTO = new CarreraDTO();
        returnDTO.setName(carreraDTO.getName());
        returnDTO.setId((carreraDTO.getId()));

        when(carreraService.existsByName(anyString())).thenReturn(true);

        when(carreraService.updateCarrera(anyString(),any(CarreraDTO.class))).thenReturn(returnDTO);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/carreras/Ingenieria/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carreraDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name",equalTo(NAME)))
                .andExpect(jsonPath("$.id",equalTo(1)));
    }

    @Test // Used to test DELETE Carrera
    void Delete_Carrera_Verify_deleteCarrera_Http_204() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/carreras/Ingenieria/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(carreraService).deleteCarrera(anyString());

    }

    @Test
    void Test_CarreraNotFound_Exception_Http_404() throws Exception{

        when(carreraService.getCarreraByName(anyString())).thenThrow(new CarreraNotFoundException(NAME));

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/carreras/Ingenieria/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CarreraNotFoundException))
                .andExpect(jsonPath("$.message").value("Carrera Not Found"));


    }

    @Test
    void Test_InvalidArgument_Exception_Http_422() throws Exception{

        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId(1L);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/carreras/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carreraDTO)))
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