import java.io.IOException;
import java.net.*;

public class ClienteThread extends Thread {
    private int id;
    private Integer tempo;
    private DatagramSocket bcs;
    private DatagramPacket pkg;
    private Relogio relogio;

    public ClienteThread(int id, DatagramSocket bcs, Integer tempo) throws SocketException {
        this.id = id;
        this.bcs = bcs;
        this.relogio = new Relogio(tempo, "Cliente " + id);
        this.relogio.start();
        this.tempo = this.relogio.getTempo();
    }

    public void run() {
        EnviarTempo();
        ObterTempoSincronizado();
    }

    private void ObterTempoSincronizado() {
        while (true) {
            try {
                this.pkg = new DatagramPacket(new byte[256], 256);
                bcs.receive(this.pkg);
                String data = new String(this.pkg.getData(), 0, this.pkg.getLength());
                this.relogio.setTempo(Integer.parseInt(data));
                this.tempo = this.relogio.getTempo();
                System.out.println("Tempo atualizado: " + this.tempo + " pela thread " + id);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void EnviarTempo() {
        byte[] tempoBytes = this.tempo.toString().getBytes();

        System.out.println("Enviando tempo " + this.tempo + " pela thread " + id);
        try {
            this.pkg = new DatagramPacket(tempoBytes, tempoBytes.length, InetAddress.getLocalHost(), 6000);
        } catch (UnknownHostException e) {
            System.err.println("Não foi possível encontrar o host");
        }

        try {
            this.bcs.send(this.pkg);
        } catch (IOException e) {
            System.err.println("Não foi possível enviar o pacote");
        }
    }
}