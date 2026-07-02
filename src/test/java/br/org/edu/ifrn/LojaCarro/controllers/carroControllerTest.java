package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.security.CustomUserDetailsService;
import br.org.edu.ifrn.LojaCarro.security.JwtFilter;
import br.org.edu.ifrn.LojaCarro.security.JwtService;
import br.org.edu.ifrn.LojaCarro.services.CarroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarroController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarroService carroService;

    // ADICIONE ESTES MOCKS
    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveSalvarCarro() throws Exception {

        Carro carro = new Carro();
        carro.setId(1L);
        carro.setModelo("Civic");
        carro.setAno(2022);

        when(carroService.save(any(Carro.class)))
                .thenReturn(carro);

        mockMvc.perform(post("/carro/salvar")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Civic"))
                .andExpect(jsonPath("$.ano").value(2022));
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
    }

    @Test
    void deveRetornarNotFoundAoPesquisarCarroInexistente() throws Exception {

        when(carroService.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/carro/1"))
                .andExpect(status().isNotFound());
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
    }

    @Test
    void deveDeletarCarro() throws Exception {

        doNothing().when(carroService).deleteById(1L);

        mockMvc.perform(delete("/carro/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void deveRetornarBadRequestQuandoModeloVazio() throws Exception {

        Carro carro = new Carro();
        carro.setModelo("");
        carro.setAno(2024);

        mockMvc.perform(post("/carro/salvar")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carro)))
                .andExpect(status().isBadRequest());
    }
}