package br.com.alura.adopet.api.validations.tutor;

import br.com.alura.adopet.api.dto.DadosAtualizarTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidationDadosJaExistenteTutor implements ValidationAtualizacaoDadosTutor{

    private TutorRepository repository;

    @Override
    public void validar(DadosAtualizarTutorDto dados) {
        if (repository.existsByEmailOrTelefone(dados.email(), dados.telefone()))
            throw new ValidacaoException("Esses dados já estão cadastrados, por favor, coloque outros dados!");
    }
}
