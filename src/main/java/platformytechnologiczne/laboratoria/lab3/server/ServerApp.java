package platformytechnologiczne.laboratoria.lab3.server;

import java.io.IOException;

public class ServerApp {

    /**
     * main application named Server.
     *
     * @param args arguments for main
     * @throws IOException          during using Socket
     * @throws InterruptedException during using Threads
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        Server newServer = new Server("first server", 8080);
        newServer.start();
        System.out.println("Server stoped, I want to close him");
        newServer.close();
    }
}

