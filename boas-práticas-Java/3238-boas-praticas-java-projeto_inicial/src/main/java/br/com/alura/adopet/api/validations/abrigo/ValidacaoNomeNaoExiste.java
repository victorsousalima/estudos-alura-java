package br.com.alura.adopet.api.validations.abrigo;

import br.com.alura.adopet.api.exception.DadosNotFound;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidacaoNomeNaoExiste implements ValidationListagemPetDoAbrigo{

    @Autowired
    private AbrigoRepository repository;

    @Override
    public void validar(String idOuNome) {

        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(idOuNome);

        if (!matcher.find()) {
            if (!repository.existsByNome(idOuNome))
                throw new DadosNotFound("Nome não encontrado!");
        }
    }
}
