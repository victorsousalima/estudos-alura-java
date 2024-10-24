package br.com.alura;

public class CommandExecutor {

    public void executeCommand(Command command) throws Exception{
        command.execute();
    }
}
