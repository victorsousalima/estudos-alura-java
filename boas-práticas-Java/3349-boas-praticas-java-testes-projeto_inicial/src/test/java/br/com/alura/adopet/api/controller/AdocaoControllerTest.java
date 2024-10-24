package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService adocaoService;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDto> jsonSolicitacao;

    @Autowired
    private JacksonTester<AprovacaoAdocaoDto> jsonAprovacao;

    @Autowired
    private JacksonTester<ReprovacaoAdocaoDto> jsonReprovacao;

    @Test
    @DisplayName("Deveria devolver código 400 para solicitação de adoção com erros")
    void cenario1() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver código 200 para solicitação de adoção")
    void cenario2() throws Exception {
        //AARANGE
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l, 1l, "Motivo qualquer");

        //ACT
        var response = mvc.perform(
                post("/adocoes")
                        .content(jsonSolicitacao.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar código 400 para aprovação de adoção")
    void cenario3() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(put("/adocoes/aprovar")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar código 200 para aprovação de adoção")
    void cenario4() throws Exception {
        //ARRANGE
        AprovacaoAdocaoDto dto = new AprovacaoAdocaoDto(1l);

        //ACT
        var response = mvc.perform(put("/adocoes/aprovar")
                .content(jsonAprovacao.write(dto).getJson())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar código 400 para reprovação de adoção")
    void cenario5() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(put("/adocoes/reprovar")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar código 200 para reprovação de adoção")
    void cenario6() throws Exception {
        //ARRANGE
        ReprovacaoAdocaoDto dto = new ReprovacaoAdocaoDto(1l, "Motivo qualquer");

        //ACT
        var response = mvc.perform(put("/adocoes/reprovar")
                .content(jsonReprovacao.write(dto).getJson())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }
}