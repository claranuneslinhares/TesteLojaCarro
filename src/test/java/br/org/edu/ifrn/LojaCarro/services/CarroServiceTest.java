package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarroServiceTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private CarroService carroService;

    private Carro carro;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        carro = new Carro();
        carro.setId(1L);
        carro.setModelo("Civic");
        carro.setAno(2022);
    }

    @Test
    void deveSalvarCarro() {
        when(carroRepository.save(carro)).thenReturn(carro);

        Carro resultado = carroService.save(carro);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Civic", resultado.getModelo());
        assertEquals(2022, resultado.getAno());

        verify(carroRepository, times(1)).save(carro);
    }

    @Test
    void deveBuscarCarroPorId() {
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));

        Optional<Carro> resultado = carroService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("Civic", resultado.get().getModelo());
        assertEquals(2022, resultado.get().getAno());

        verify(carroRepository, times(1)).findById(1L);
    }

    @Test
    void deveRetornarOptionalVazioQuandoCarroNaoExiste() {
        when(carroRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Carro> resultado = carroService.findById(2L);

        assertFalse(resultado.isPresent());

        verify(carroRepository, times(1)).findById(2L);
    }

    @Test
    void deveListarTodosCarros() {
        List<Carro> carros = List.of(carro);
        when(carroRepository.findAll()).thenReturn(carros);

        List<Carro> resultado = carroService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Civic", resultado.get(0).getModelo());

        verify(carroRepository, times(1)).findAll();
    }

    @Test
    void deveAtualizarCarro() {
        carro.setModelo("Corolla");

        when(carroRepository.save(carro)).thenReturn(carro);

        Carro atualizado = carroService.update(carro);

        assertNotNull(atualizado);
        assertEquals(1L, atualizado.getId());
        assertEquals("Corolla", atualizado.getModelo());
        assertEquals(2022, atualizado.getAno());

        verify(carroRepository, times(1)).save(carro);
    }

    @Test
    void deveRemoverCarro() {
        doNothing().when(carroRepository).deleteById(1L);

        carroService.deleteById(1L);

        verify(carroRepository, times(1)).deleteById(1L);
    }
}