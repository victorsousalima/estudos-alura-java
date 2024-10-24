package br.com.alura.adopet.api.validations.adocao;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationPetDisponivel implements ValidationSolicitacaoAdocao{

    @Autowired
    private PetRepository petRepository;

    @Override
    public void validar(SolicitacaoAdocaoDto dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());

        if (pet.getAdotado())
            throw new ValidacaoException("Pet já foi adotado!");
    }
}
