package platformytechnologiczne.laboratoria.lab3.client;

import java.io.*;
import java.net.Socket;


/**
 * Client want to connect with Server, when server
 * send "ready" client send message n times
 */
public class Client {

    /**
     * socket name
     */
    private String name;

    /**
     * port which user connect
     */
    private int port;

    /**
     * server to which client connect
     */
    private Socket server;

    /**
     * initialize the Client
     * @param name client name
     */
    public Client( String name, int port ){
        this.name = name;
        this.port = port;
    }


    /**
     * start client class
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void start() throws IOException {
        this.server = new Socket("localhost", this.port);

        try (
                // create input and output server's stream
                DataOutputStream outputStream = new DataOutputStream(this.server.getOutputStream());
                DataInputStream inputStream = new DataInputStream(this.server.getInputStream())
        ) {
            // get server socket and get input stream
            char readyInfo = inputStream.readChar();
            if( readyInfo != 'r' )
                throw new IOException("server is not ready");

            // get file to read and create buffer input strem to file
            String filePath = "src/main/resources/jeden.jpg";
            File fileToSend = new File(filePath);
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(filePath));

            outputStream.writeLong( fileToSend.length() );

            // creating string progress bar
            StringBuilder progressBar = new StringBuilder("0%: ");
            for( int i = 0; i <= fileToSend.length()/4096; i++ )
                progressBar.append(" ");
            progressBar.append(" :100%");

            // reading from file and writing to server
            byte[] buffer = new byte[4*1024];
            int readSize, counter = 0;
            while( (readSize = fileInputStream.read(buffer)) != -1 ){
                outputStream.write(buffer, 0, readSize);

                counter++;
                progressBar.setCharAt(counter+3, '#');
                System.out.println(progressBar);

                Thread.sleep( 100 );
            }

            // sending what was left
            outputStream.flush();

            // waiting for information from server to close
            while( true ){
                if( inputStream.readChar() == 'c' ){
                    break;
                }
            }

        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }
    }
}

