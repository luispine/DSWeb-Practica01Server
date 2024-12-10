package org.uv.dsweb.practica01.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 15-dy2xxLapDeLuis
 */
public class DSWebPractica01Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server iniciado...");

            while (true) {
                Socket cliente = server.accept();
                ClienteSocket clienteSocket = new ClienteSocket(cliente);
                clienteSocket.start();
                System.out.println("Cliente Aceptado....");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(DSWebPractica01Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
