package platformytechnologiczne.laboratoria.lab3.client;

import java.io.*;

public class ClientApp {

    /**
     * main application using Client class.
     * @param args arguments for main
     * @throws IOException during using Socket
     * @throws ClassNotFoundException during using String class
     */
    public static void main(String[] args) throws IOException{

        Client newClient = new Client("first client", 8080);
        newClient.start();
    }
}

