package com.maya;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

/**
 * Server listens on a port hardcoded to 60010. This server reads from the client
 * and echoes back the result. When the client enters the string "exit", the
 * server closes the connection.
 */

public class Main {
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.start();
    }

    private void start(){
        ServerSocket socket = null;

        try {
            // set socket to port 60010
            socket = new ServerSocket(60010);

            /*
             * When a client connects to server, a new thread is created that
             * will interact with the client. The server port never closes.
             */
            while (true) {
                // listening for connection
                Socket client = socket.accept();
                String address = client.getInetAddress().getHostAddress();
                System.out.printf("Client connected: %s%n", address);

                // create a new thread then start it
                Threads newThread = new Threads(client);
                newThread.start();
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException ioee) {
                System.err.println(ioee);
            }
        }
    }

    public class Threads extends Thread {
        private Threads(Socket c) {
            client = c;
        }

        public void run() {
            BufferedReader networkInput = null;
            OutputStreamWriter networkOutput = null;

            try {
                networkInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
                networkOutput = new OutputStreamWriter(client.getOutputStream());

                //read from input and echo same words back to output
                boolean exitThread = false;
                while (!exitThread) {
                    String line = networkInput.readLine();
                    if ( (line == null) || line.equalsIgnoreCase("exit") ) {
                        exitThread = true;
                    }
                    else
                        networkOutput.write("Server> "+line+"\n");

                    networkOutput.flush();
                }
            } catch (IOException ioe) {
                System.err.println(ioe);
            } finally {
                try {
                    if (networkInput != null)
                        networkInput.close();
                    if (networkOutput != null)
                        networkOutput.close();
                    if (client != null)
                        client.close();

                    String address = client.getInetAddress().getHostAddress();
                    System.out.printf("Client disconnected: %s%n", address);
                } catch (IOException ioee) {
                    System.err.println(ioee);
                }
            }
        }
        private Socket client;
    }

}



