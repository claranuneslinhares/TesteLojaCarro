package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.services.CarroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    // Apenas vendedor e gerente podem cadastrar carros
    @PreAuthorize("hasAnyRole('VENDEDOR', 'GERENTE')")
    @PostMapping("/salvar")
    public ResponseEntity<Carro> salvarCarro(@Valid @RequestBody Carro c) {
        Carro savedCarro = carroService.save(c);
        return ResponseEntity.ok(savedCarro);
    }

    // Apenas vendedor e gerente podem atualizar
    @PreAuthorize("hasAnyRole('VENDEDOR', 'GERENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable Long id,
                                                @Valid @RequestBody Carro c) {
        c.setId(id);
        Carro updatedCarro = carroService.update(c);
        return ResponseEntity.ok(updatedCarro);
    }

    // Apenas gerente pode deletar
    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarro(@PathVariable Long id) {
        carroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Todos os usuários autenticados podem pesquisar por ID
    @PreAuthorize("hasAnyRole('CLIENTE', 'VENDEDOR', 'GERENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Carro> pesquisarCarroPorId(@PathVariable Long id) {
        Optional<Carro> carro = carroService.findById(id);
        return carro.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Todos os usuários autenticados podem listar
    @PreAuthorize("hasAnyRole('CLIENTE', 'VENDEDOR', 'GERENTE')")
    @GetMapping
    public ResponseEntity<List<Carro>> pesquisarTodosCarros() {
        List<Carro> carros = carroService.findAll();
        return ResponseEntity.ok(carros);
    }
}