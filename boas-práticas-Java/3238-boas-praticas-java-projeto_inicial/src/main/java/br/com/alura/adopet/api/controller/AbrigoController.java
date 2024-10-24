package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosCadastroAbrigoDto;
import br.com.alura.adopet.api.dto.DadosCadastroPetDto;
import br.com.alura.adopet.api.dto.DadosDetalhesAbrigoDto;
import br.com.alura.adopet.api.dto.DadosDetalhesPetDto;
import br.com.alura.adopet.api.exception.DadosNotFound;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private AbrigoService service;

    @GetMapping
    public ResponseEntity<List<DadosDetalhesAbrigoDto>> listar() {
        return ResponseEntity.ok(repository.findAll().stream().map(a -> new DadosDetalhesAbrigoDto(a)).toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroAbrigoDto dados) {
        try {
            service.cadastrar(dados);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<DadosDetalhesPetDto>> listarPets(@PathVariable String idOuNome) {
        try {
            List<DadosDetalhesPetDto> pets = service.listarPets(idOuNome).stream().map(pet -> new DadosDetalhesPetDto(pet)).toList();
            return ResponseEntity.ok(pets);
        } catch (DadosNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid DadosCadastroPetDto dados) {
        try {
            service.cadastrarPet(idOuNome, dados);
            return ResponseEntity.ok().build();
        } catch (DadosNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

}
