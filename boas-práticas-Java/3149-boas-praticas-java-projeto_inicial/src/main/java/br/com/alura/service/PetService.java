package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;

public class PetService {

    private ClientHttpConfiguration clientHttpConfiguration;

    public PetService(ClientHttpConfiguration clientHttpConfiguration) {
        this.clientHttpConfiguration = clientHttpConfiguration;
    }

    public void listarPet() throws Exception {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        String uri = "http://172.28.214.0:8080/abrigos/" +idOuNome +"/pets";
        HttpResponse<String> response = clientHttpConfiguration.dispararRequisicaoGet(uri);
        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
        }
        String responseBody = response.body();
        var pets = new ObjectMapper().readValue(responseBody, Pet[].class);
        System.out.println("Pets cadastrados:");
        Arrays.asList(pets).forEach(p -> System.out.printf("%d - %s - %s - %s - %d ano(s)\n", p.getId(), p.getTipo(), p.getNome(), p.getRaca(), p.getIdade()));
    }

    public void importarPet() throws Exception {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        System.out.println("Digite o nome do arquivo CSV:");
        String nomeArquivo = new Scanner(System.in).nextLine();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " +nomeArquivo);
        }
        String line;
        while ((line = reader.readLine()) != null) {
            String[] campos = line.split(",");
            String tipo = campos[0].toUpperCase();
            String nome = campos[1];
            String raca = campos[2];
            int idade = Integer.parseInt(campos[3]);
            String cor = campos[4];
            Double peso = Double.parseDouble(campos[5]);

            Pet pet = new Pet(tipo, nome, raca, idade, cor, peso);

            String uri = "http://172.28.214.0:8080/abrigos/" + idOuNome + "/pets";
            HttpResponse<String> response = clientHttpConfiguration.dispararRequisicaoPost(uri, pet);
            int statusCode = response.statusCode();
            String responseBody = response.body();
            if (statusCode == 200) {
                System.out.println("Pet cadastrado com sucesso: " + nome);
            } else if (statusCode == 404) {
                System.out.println("Id ou nome do abrigo não encontado!");
                break;
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o pet: " + nome);
                System.out.println(responseBody);
                break;
            }
        }
        reader.close();
    }
}
