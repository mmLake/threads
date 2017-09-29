package com.maya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Client listens on a port hardcoded to 60010. Client inputs a String that will be
 * sent to the server and echoed back. When the client enters the string "exit", the
 * server closes the connection.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedReader userInput = null;
        Socket sock = null;

        try {
            sock = new Socket("localhost", 60010);

            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(sock.getOutputStream(),true);

            while (true) {
                System.out.print("Client> ");
                String line = userInput.readLine();
                if(line == null)
                    break;
                if (line.equals(""))
                    break;
                if (line.equalsIgnoreCase("Exit"))
                    break;
                out.println(line);
                System.out.println(in.readLine());
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        } finally {
            if (in != null)
                in.close();
            if (userInput != null)
                userInput.close();
            if (out != null)
                out.close();
            if (sock != null)
                sock.close();
        }
    }

}















