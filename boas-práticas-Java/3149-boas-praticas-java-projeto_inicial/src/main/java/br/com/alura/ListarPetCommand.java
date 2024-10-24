package br.com.alura;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.AbrigoService;
import br.com.alura.service.PetService;

public class ListarPetCommand implements Command{
    @Override
    public void execute() {
        try {
            ClientHttpConfiguration clientHttp = new ClientHttpConfiguration();
            PetService petService = new PetService(clientHttp);

            petService.listarPet();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
