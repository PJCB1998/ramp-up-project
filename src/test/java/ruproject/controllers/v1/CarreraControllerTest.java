package ruproject.controllers.v1;




import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RunAs;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ruproject.api.v1.model.CarreraDTO;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.services.CarreraService;


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
        this.mvc = MockMvcBuilders.standaloneSetup(carreraController).build();
    }

    @Test
    void getAllCarreras() throws Exception {
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

    @Test
    void getCarreraByName() throws Exception {

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

    @Test
    void createCarrera() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId(1L);
        carreraDTO.setName(NAME);

        CarreraDTO returnDTO = new CarreraDTO();
        returnDTO.setName(carreraDTO.getName());
        returnDTO.setId((carreraDTO.getId()));

        when(carreraService.saveCarrera(carreraDTO)).thenReturn(returnDTO);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/carreras/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carreraDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo(NAME)))
                .andExpect(jsonPath("$.id",equalTo(1)));


    }

    @Test
    void updateCarrera() throws Exception {

        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId(1L);
        carreraDTO.setName(NAME);

        CarreraDTO returnDTO = new CarreraDTO();
        returnDTO.setName(carreraDTO.getName());
        returnDTO.setId((carreraDTO.getId()));

        when(carreraService.existsByName(anyString())).thenReturn(true);

        when(carreraService.updateCarera(anyString(),any(CarreraDTO.class))).thenReturn(returnDTO);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/carreras/Ingenieria/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carreraDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name",equalTo(NAME)))
                .andExpect(jsonPath("$.id",equalTo(1)));
    }

    @Test
    void deleteCarrera() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/carreras/Ingenieria/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(carreraService).deleteCarrera(anyString());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}