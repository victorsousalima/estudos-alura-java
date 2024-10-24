package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosAtualizarTutorDto;
import br.com.alura.adopet.api.dto.DadosCadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.tutor.ValidationAtualizacaoDadosTutor;
import br.com.alura.adopet.api.validations.tutor.ValidationTutorJaCadastrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    @Autowired
    private List<ValidationTutorJaCadastrado> validationTutorJaCadastrado;

    @Autowired
    private List<ValidationAtualizacaoDadosTutor> validationAtualizacaoDados;

    public void cadastrar(DadosCadastroTutorDto dados) {
        validationTutorJaCadastrado.forEach(validation -> validation.validar(dados));

        repository.save(new Tutor(dados));
    }

    public void atualizar(DadosAtualizarTutorDto dados) {
        validationAtualizacaoDados.forEach(validation -> validation.validar(dados));

        Tutor tutor = repository.getReferenceById(dados.id());
        tutor.atualizarDados(dados);
    }
}
