package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService tutorService;

    @Autowired
    private JacksonTester<CadastroTutorDto> jsonCadastro;

    @Autowired
    private JacksonTester<AtualizacaoTutorDto> jsonAtualizacao;

    @Test
    @DisplayName("Deve retornar código 400 para cadastro de Tutor")
    void cenario1() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(post("/tutores").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 para cadastro de Tutor")
    void cenario2() throws Exception {
        //ARRANGE
        CadastroTutorDto dto = new CadastroTutorDto("Teste", "(11)94002-8922", "test@gmail.com");

        //ACT
        var response = mvc.perform(post("/tutores").content(jsonCadastro.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 para atualizar tutor")
    void cenario3() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(put("/tutores").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 para atualizar tutor")
    void cenario4() throws Exception {
        //ARRANGE
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1l, "Teste", "(11)94002-8922", "test@gmail.com");

        //ACT
        var response = mvc.perform(put("/tutores").content(jsonAtualizacao.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

}