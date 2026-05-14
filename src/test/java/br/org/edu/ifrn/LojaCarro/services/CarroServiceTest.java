package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
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

    // cadastro
    @Test
    void deveSalvarCarro() {
        when(carroRepository.save(carro)).thenReturn(carro);

        Carro resultado = carroService.save(carro);

        assertEquals("Ferrari", resultado.getModelo());
        verify(carroRepository, times(1)).save(carro);
    }

    // busca por id
    @Test
    void deveBuscarCarroPorId() {
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));

        Optional<Carro> resultado = carroService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Civic", resultado.get().getModelo());
    }

    // listar todos
    @Test
    void deveListarTodosCarros() {
        when(carroRepository.findAll())
                .thenReturn(Arrays.asList(carro));

        assertEquals(1, carroService.findAll().size());
    }

    // atualizar
    @Test
    void deveAtualizarCarro() {
        carro.setModelo("Corolla");

        when(carroRepository.save(carro)).thenReturn(carro);

        Carro atualizado = carroService.update(carro);

        assertEquals("Corolla", atualizado.getModelo());
    }

    // remover
    @Test
    void deveRemoverCarro() {
        doNothing().when(carroRepository).deleteById(1L);

        carroService.deleteById(1L);

        verify(carroRepository, times(1)).deleteById(1L);
    }
}
