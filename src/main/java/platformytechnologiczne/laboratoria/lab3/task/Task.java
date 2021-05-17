package platformytechnologiczne.laboratoria.lab3.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Task implements Runnable {

    /**
     * client which request has to be handled
     */
    private final Socket client;


    /**
     * initialize the Task
     *
     * @param client client which request has to be handled
     */
    public Task(Socket client) {
        this.client = client;
    }


    /**
     * Override run() method from interface Runnable
     */
    @Override
    public void run() {
        try (
                // create output and input client streams
                DataOutputStream outputStream = new DataOutputStream(this.client.getOutputStream());
                DataInputStream inputStream = new DataInputStream(this.client.getInputStream())
        ) {
            // inform client that server can take file - send 'r' character
            outputStream.writeChar('r');
            outputStream.flush();

            // take from client size of file he want to send
            long fileSize = inputStream.readLong();

            // read file contest to buffer
            byte[] buffer = new byte[4 * 1024];
            int readBytes;
            long byteRead = 0;
            while ((readBytes = inputStream.read(buffer, 0, buffer.length)) != -1) {
                /* Do sth with data */
                byteRead += readBytes;
                if (byteRead >= fileSize)
                    break;
            }

            // inform user that server read all data
            System.out.println("Thread read all data");

            // send information to user to close connection - send 'c' character
            outputStream.writeChar('c');
            outputStream.flush();

            // inform user about uploaded file
            System.out.println("Thread read file which size is " + byteRead);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
