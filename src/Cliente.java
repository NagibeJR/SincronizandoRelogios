import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Cliente {

    public static void main(String[] args) throws IOException {
        Integer tempo = 0;
        int tempoIncremento = 5;
        int inicioPortaSocket = 6001;
        int numeroSockets = 3;

        ArrayList<Integer> sockets = new ArrayList<Integer>();

        for (int s = inicioPortaSocket; s < (inicioPortaSocket + numeroSockets); s++) {
            sockets.add(s);
        }

        for (int i = 0; i < numeroSockets; i++) {
            DatagramSocket socket = new DatagramSocket(sockets.get(i));
            ClienteThread t = new ClienteThread(i, socket, tempo);
            tempo = tempo + tempoIncremento;
            t.start();
        }
    }
}
