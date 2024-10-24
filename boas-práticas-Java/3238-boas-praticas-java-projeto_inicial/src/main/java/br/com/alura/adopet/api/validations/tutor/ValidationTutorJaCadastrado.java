package br.com.alura.adopet.api.validations.tutor;

import br.com.alura.adopet.api.dto.DadosCadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationTutorJaCadastrado implements ValidationCadastroTutor{

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public void validar(DadosCadastroTutorDto dados) {
        if (tutorRepository.existsByEmailOrTelefone(dados.email(), dados.telefone()))
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
    }
}
