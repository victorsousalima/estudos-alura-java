package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Abrigo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;

public class AbrigoService {

    private ClientHttpConfiguration clientHttpConfiguration;

    public AbrigoService(ClientHttpConfiguration clientHttpConfiguration) {
        this.clientHttpConfiguration = clientHttpConfiguration;
    }

    public void listarAbrigo() throws Exception{
        String uri = "http://172.28.214.0:8080/abrigos";
        HttpResponse<String> response = clientHttpConfiguration.dispararRequisicaoGet(uri);
        String responseBody = response.body();
        var abrigos = new ObjectMapper().readValue(responseBody, Abrigo[].class);

        if (abrigos.length == 0)
            System.out.println("Não há abrigos cadastrados!");
        else {
            System.out.println("Abrigos cadastrados:");
            Arrays.asList(abrigos).forEach(a -> System.out.println(a.getId() + " - " + a.getNome()));
        }
    }

    public void cadastrarAbrigo() throws Exception{
        System.out.println("Digite o nome do abrigo:");
        String nome = new Scanner(System.in).nextLine();
        System.out.println("Digite o telefone do abrigo:");
        String telefone = new Scanner(System.in).nextLine();
        System.out.println("Digite o email do abrigo:");
        String email = new Scanner(System.in).nextLine();

        Abrigo abrigo = new Abrigo(nome, telefone, email);

        String uri = "http://172.28.214.0:8080/abrigos";
        HttpResponse<String> response = clientHttpConfiguration.dispararRequisicaoPost(uri, abrigo);
        int statusCode = response.statusCode();
        System.out.println("STATUS: " + statusCode);
        String responseBody = response.body();
        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
            System.out.println(responseBody);
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
            System.out.println(responseBody);
        }
    }
}
