package platformytechnologiczne.laboratoria.lab3.server;

import platformytechnologiczne.laboratoria.lab3.task.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


/**
 * Server waiting for new client,
 * when clients arrived new thread handle client' request
 */
public class Server {

    /**
     * name of the server
     */
    private String name;

    /**
     * list of sockets named clients
     */
    private List<Socket> clients;

    /**
     * list of threads handled clients' requests
     */
    private List<Thread> threads;

    /**
     * boolean information about is server open
     */
    private boolean isOpen;

    /**
     * server port
     */
    private int port;


    /**
     * initialize the Server
     * @param name server name
     */
    public Server( String name, int port ){
        this.name = name;
        this.clients = new LinkedList<>();
        this.threads = new LinkedList<>();
        this.port = port;
    }

    /**
     * start server
     * @throws IOException during using sockets
     * @throws InterruptedException during using threads
     */
    public void start() throws IOException, InterruptedException {

        try( ServerSocket server = new ServerSocket(port)) {
            this.isOpen = true;
            while (this.isOpen) {
                System.out.println("Waiting for new client");
                Socket newSocket = server.accept();
                this.clients.add(newSocket);
                System.out.println("Connected");

                System.out.println("Creating thread for client");
                Thread t = new Thread(new Task(newSocket));
                this.threads.add(t);
                t.start();
            }

            // wait for all threads
            for( Thread t : this.threads )
                t.join();
        }
    }


    /**
     * close server and interrupt all threads and close all sockets
     * @throws IOException during using sockets
     * @throws InterruptedException during using threads
     */
    public void close() throws IOException, InterruptedException {

        this.isOpen = false;

        // interrupt all threads to end the application
        for( Thread c : threads ){
            c.interrupt();
            c.join();
        }

        System.out.println("Threads ended");

        // close stream from clients to server
        for( Socket client : clients ){
            client.close();
        }

        System.out.println("Clients disconnected");

        // inform user
        System.out.println("Server closed");
    }

    // -------------------
    // GETTERS AND SETTERS
    // -------------------

    /**
     * @return is server open
     */
    public boolean isOpen(){
        return this.isOpen;
    }

    /**
     * @return server name
     */
    public String get_name(){
        return this.name;
    }

    public void set_isOpen( boolean newIsOpen ){
        this.isOpen = newIsOpen;
    }
}
