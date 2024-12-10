
package org.uv.dsweb.practica01.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 15-dy2xxLapDeLuis
 */
public class ClienteSocket extends Thread {

    private Socket cliente;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private boolean run = true;
    
    public synchronized void parar() {
        run = false;
    }

    public ClienteSocket(Socket cliente) {
        this.cliente = cliente;
        InputStreamReader isr = null;

        try {
            isr = new InputStreamReader(this.cliente.getInputStream());
            reader = new BufferedReader(isr);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

        OutputStreamWriter osw = null;

        try {
            osw = new OutputStreamWriter(cliente.getOutputStream());
            writer = new BufferedWriter(osw);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (run) {
            try {
                String operacion = reader.readLine();
                System.out.println("Operación-->" + operacion);
                String resultado = "";

                int indexOperador = getIndexOperador(operacion);

                if (indexOperador != -1) {

                    resultado = operacion(indexOperador, operacion);

                } else {
                    resultado = "Error...";
                }

                writer.write(resultado);
                writer.write("\n");
                writer.flush();
            } catch (IOException ex) {
                run = false;
                Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getIndexOperador(String operacion) {
        int index = -1;
        String regex = "[+\\-*/]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(operacion);
        if (matcher.find()) {
            index = matcher.start();
        }
        return index;
    }

    public String operacion(int index, String operacion) {
        String a = operacion.substring(0, index);
        String b = operacion.substring(index + 1, operacion.length());
        int c = 0;

        switch (operacion.charAt(index)) {
            case '+' ->
                c = Integer.parseInt(a) + Integer.parseInt(b);
            case '-' ->
                c = Integer.parseInt(a) - Integer.parseInt(b);
            case '/' ->
                c = Integer.parseInt(a) / Integer.parseInt(b);
            case '*' ->
                c = Integer.parseInt(a) * Integer.parseInt(b);
            default -> {
                return "Operación desconocida";
            }
        }
        return "a: " + a + ", b: " + b + ",c: " + String.valueOf(c);
    }
}
