import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Servidor {

    public static void main(String[] args) throws IOException {
        int inicioPortaSocket = 6001;
        int numeroSockets = 3;

        Relogio relogio = new Relogio(5, "Servidor");
        relogio.start();

        ArrayList<Integer> numerosScket = new ArrayList<Integer>();
        ArrayList<DatagramPacket> packets = new ArrayList<DatagramPacket>();

        // Criando os números de sockets de clientes começando a partir do 6001
        for (int s = inicioPortaSocket; s < (inicioPortaSocket + numeroSockets); s++) {
            numerosScket.add(s);
        }

        int tempo;
        DatagramSocket datagramSocket = new DatagramSocket(6000);

        int dadoRecebido = 0;
        int accumulatedTime = 0;

        while (dadoRecebido < numeroSockets) {
            DatagramPacket pkg = new DatagramPacket(new byte[256], 256);
            datagramSocket.receive(pkg);
            String data = new String(pkg.getData(), 0, pkg.getLength());
            System.out.println("Dados Recebidos: " + data);
            accumulatedTime += Integer.parseInt(data);
            dadoRecebido++;
        }

        tempo = relogio.getTempo();

        int tempoMedio = (accumulatedTime + tempo) / (numeroSockets + 1);
        System.out.println("Tempo Médio: " + tempoMedio);

        Integer tempoSincronizado = tempo + tempoMedio;

        byte[] bytesTempoSincronizado = tempoSincronizado.toString().getBytes();
        System.out.println("Tempo Sincronizado: " + tempoSincronizado);

        InetAddress add = InetAddress.getByName("255.255.255.255");

        // Criando os pacotes
        for (int i = 0; i < numerosScket.size(); i++) {
            packets.add(new DatagramPacket(bytesTempoSincronizado, bytesTempoSincronizado.length, add,
                    numerosScket.get(i)));
        }

        relogio.setTempo(tempoSincronizado);
        // Enviando as mensagens
        for (DatagramPacket datagramPacket : packets) {
            System.out.println(
                    "Enviando mensagem '" + new String(datagramPacket.getData()) + "' para "
                            + datagramPacket.getPort());
            datagramSocket.send(datagramPacket);
        }
    }
}