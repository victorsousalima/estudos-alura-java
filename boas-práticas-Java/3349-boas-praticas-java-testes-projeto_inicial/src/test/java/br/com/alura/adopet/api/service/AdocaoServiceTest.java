package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService service;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    ValidacaoSolicitacaoAdocao validador1;

    @Mock
    ValidacaoSolicitacaoAdocao validador2;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto dto;

    private AprovacaoAdocaoDto dto2;

    private ReprovacaoAdocaoDto dto3;

    @Spy
    private Adocao adocao;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;


    @Test
    void deveriaSalvarAdocaoAoSolicitar() {
        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Movito qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        //ACT
        service.solicitar(dto);

        //ASSERT
        then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocao = adocaoCaptor.getValue();
        assertEquals(pet, adocao.getPet());
        assertEquals(tutor, adocao.getTutor());
        assertEquals(dto.motivo(), adocao.getMotivo());
    }

    @Test
    void deveriaVerificarSeAsValidacoesEstaoSendoChamadas() {
        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Movito qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        this.validacoes.add(validador1);
        this.validacoes.add(validador2);

        //ACT
        service.solicitar(dto);

        //ASSERT
        BDDMockito.then(validador1).should().validar(dto);
        BDDMockito.then(validador2).should().validar(dto);
    }

    @Test
    @DisplayName("Deve aprovar solicitação de adoção")
    void cenario3() throws Exception {
        //ARRANGE
        this.dto2 = new AprovacaoAdocaoDto(2l);
        given(adocaoRepository.getReferenceById(dto2.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(adocao.getTutor()).willReturn(tutor);
        given(adocao.getData()).willReturn(LocalDateTime.now());

        //ACT
        service.aprovar(dto2);

        //ASSERT
        verify(adocao).marcarComoAprovada();
        assertEquals(StatusAdocao.APROVADO, adocao.getStatus());
    }

    @Test
    @DisplayName("Deve reprovar solicitação de adoção")
    void cenario4() throws Exception {
        //ARRANGE
        this.dto3 = new ReprovacaoAdocaoDto(2l, "Motivo qualquer");
        given(adocaoRepository.getReferenceById(dto3.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(adocao.getTutor()).willReturn(tutor);
        given(adocao.getData()).willReturn(LocalDateTime.now());

        //ACT
        service.reprovar(dto3);

        //ASSERT
        verify(adocao).marcarComoReprovada(dto3.justificativa());
        assertEquals(StatusAdocao.REPROVADO, adocao.getStatus());
    }
}