package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDetalhesPetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository repository;

    @GetMapping
    public ResponseEntity<List<DadosDetalhesPetDto>> listarTodosDisponiveis() {
        List<Pet> pets = repository.findAllByAdotadoFalse();
        List<DadosDetalhesPetDto> disponiveis = pets.stream().map(pet -> new DadosDetalhesPetDto(pet)).toList();

        return ResponseEntity.ok(disponiveis);
    }

}
