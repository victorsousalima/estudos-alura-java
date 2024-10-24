package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Tutor tutor;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deve passar pela validação sem retorno de exceções")
    void cenario1() throws Exception {
        //ARRANGE
        Adocao adocao = mock(Adocao.class);
        given(adocaoRepository.findAll()).willReturn(List.of(adocao));
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        //ACT + ASSERT
        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Deve lançar exceção ao passar pela validação")
    void cenario2() throws Exception {
        //ARRANGE
        Adocao adocao = mock(Adocao.class);
        given(adocaoRepository.findAll()).willReturn(List.of(adocao));
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(adocao.getStatus()).willReturn(StatusAdocao.AGUARDANDO_AVALIACAO);
        given(adocao.getTutor()).willReturn(tutor);

        //ACT + ASSERT
        assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}