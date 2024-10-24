package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> jsonCadastro;

    @Autowired
    private JacksonTester<CadastroPetDto> jsonCadastroPet;

    @Test
    @DisplayName("Deve retornar código 200 para listagem de abrigo")
    void cenario1() throws Exception {
        //ARRANGE

        //ACT
        var response = mvc.perform(get("/abrigos")).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }


    @Test
    @DisplayName("Deve retornar código 400 para cadastro de abrigo.")
    void cenario2() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(post("/abrigos").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 para cadastro de abrigo.")
    void cenario3() throws Exception {
        //ARRANGE
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Teste", "114002-8922", "teste@gmail.com");

        //ACT
        var response = mvc.perform(post("/abrigos")
                .content(jsonCadastro.write(dto).getJson())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 para listagem de pets.")
    void cenario4() throws Exception {
        //ARRANGE
        String idOuNome = "";

        //ACT
        var response = mvc.perform(get("/abrigos/{idOuNome}/pets", idOuNome)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 para listagem de pets.")
    void cenario5() throws Exception {
        //ARRANGE
        String idOuNome = "1";

        //ACT
        var response = mvc.perform(get("/abrigos/{idOuNome}/pets", idOuNome)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 para cadastro de pet")
    void cenario6() throws Exception {
        //ARRANGE
        String idOuNome = "";
        String json = "{}";

        //ACT
        var response = mvc.perform(post("/abrigos/{idOuNome}/pets", idOuNome)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 200 para cadastro de pet")
    void cenario7() throws Exception {
        //ARRANGE
        String idOuNome = "1";
        CadastroPetDto cadastroPetDto = new CadastroPetDto(TipoPet.GATO, "Teste", "Teste", 2, "Teste", 5.0f);

        //ACT
        var response = mvc.perform(post("/abrigos/{idOuNome}/pets", idOuNome)
                .content(jsonCadastroPet.write(cadastroPetDto).getJson())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }
}