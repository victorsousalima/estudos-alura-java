package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosCadastroAbrigoDto;
import br.com.alura.adopet.api.dto.DadosCadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.validations.abrigo.ValidationCadastroAbrigo;
import br.com.alura.adopet.api.validations.abrigo.ValidationListagemPetDoAbrigo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private List<ValidationCadastroAbrigo> validationsCadastroAbrigo;

    private List<ValidationListagemPetDoAbrigo> validationsListagemPetDoAbrigo;

    public void cadastrar(DadosCadastroAbrigoDto dados) {
        validationsCadastroAbrigo.forEach(validation -> validation.validar(dados));

        repository.save(new Abrigo(dados));
    }

    public List<Pet> listarPets(String idOuNome) {
        validationsListagemPetDoAbrigo.forEach(validation -> validation.validar(idOuNome));
        try {
            Long id = Long.parseLong(idOuNome);
            List<Pet> pets = repository.getReferenceById(id).getPets();
            return pets;
        }
        catch(NumberFormatException e) {
            List<Pet> pets = repository.findByNome(idOuNome).getPets();
            return pets;
        }
    }

    public void cadastrarPet(String idOuNome, DadosCadastroPetDto dados) {
        validationsListagemPetDoAbrigo.forEach(validation -> validation.validar(idOuNome));
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = repository.getReferenceById(id);
            Pet pet = new Pet(dados);
            pet.adicionarAbrigoNoPetRecemCadastrado(abrigo);
            abrigo.getPets().add(pet);
        } catch (NumberFormatException e) {
            Abrigo abrigo = repository.findByNome(idOuNome);
            Pet pet = new Pet(dados);
            pet.adicionarAbrigoNoPetRecemCadastrado(abrigo);
            abrigo.getPets().add(pet);
        }
    }
}
