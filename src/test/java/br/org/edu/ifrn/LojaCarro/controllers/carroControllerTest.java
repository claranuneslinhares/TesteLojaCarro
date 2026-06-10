package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.services.CarroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarroController.class)
class CarroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarroService carroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveSalvarCarro() throws Exception {
        Carro carro = new Carro();
        carro.setId(1L);
        carro.setModelo("Civic");
        carro.setAno(2022);

        when(carroService.save(any(Carro.class))).thenReturn(carro);

        mockMvc.perform(post("/carro/salvar")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Civic"))
                .andExpect(jsonPath("$.ano").value(2022));

        verify(carroService, times(1)).save(any(Carro.class));
    }

    @Test
    void devePesquisarCarroPorId() throws Exception {
        Carro carro = new Carro();
        carro.setId(1L);
        carro.setModelo("Civic");
        carro.setAno(2022);

        when(carroService.findById(1L))
                .thenReturn(Optional.of(carro));

        mockMvc.perform(get("/carro/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Civic"));

        verify(carroService, times(1)).findById(1L);
    }

    @Test
    void deveRetornarNotFoundAoPesquisarCarroInexistente() throws Exception {
        when(carroService.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/carro/1"))
                .andExpect(status().isNotFound());

        verify(carroService, times(1)).findById(1L);
    }

    @Test
    void deveListarTodosCarros() throws Exception {
        Carro carro = new Carro();
        carro.setId(1L);
        carro.setModelo("Civic");
        carro.setAno(2022);

        when(carroService.findAll())
                .thenReturn(Arrays.asList(carro));

        mockMvc.perform(get("/carro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Civic"));

        verify(carroService, times(1)).findAll();
    }

    @Test
    void deveAtualizarCarro() throws Exception {
        Carro carro = new Carro();
        carro.setId(1L);
        carro.setModelo("Corolla");
        carro.setAno(2023);

        when(carroService.update(any(Carro.class)))
                .thenReturn(carro);

        mockMvc.perform(put("/carro/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Corolla"));

        verify(carroService, times(1)).update(any(Carro.class));
    }

    @Test
    void deveDeletarCarro() throws Exception {
        doNothing().when(carroService).deleteById(1L);

        mockMvc.perform(delete("/carro/1"))
                .andExpect(status().isNoContent());

        verify(carroService, times(1)).deleteById(1L);
    }
}