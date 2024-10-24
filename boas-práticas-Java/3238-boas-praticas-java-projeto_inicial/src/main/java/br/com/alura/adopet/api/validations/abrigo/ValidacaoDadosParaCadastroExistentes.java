package br.com.alura.adopet.api.validations.abrigo;

import br.com.alura.adopet.api.dto.DadosCadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoDadosParaCadastroExistentes implements ValidationCadastroAbrigo{

    @Autowired
    private AbrigoRepository repository;

    @Override
    public void validar(DadosCadastroAbrigoDto dados) {
        if (repository.existsByNomeOrTelefoneOrEmail(dados.nome(), dados.telefone(), dados.email()))
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
    }
}
