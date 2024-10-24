package br.com.alura;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.AbrigoService;

public class ListarAbrigoCommand implements Command{

    @Override
    public void execute() {
        try {
            ClientHttpConfiguration clientHttp = new ClientHttpConfiguration();
            AbrigoService abrigoService = new AbrigoService(clientHttp);

            abrigoService.listarAbrigo();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
