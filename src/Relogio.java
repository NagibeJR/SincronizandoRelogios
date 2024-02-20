public class Relogio extends Thread {
    private int tempo;
    private String proprietario;

    public Relogio(int tempo, String proprietario) {
        this.tempo = tempo;
        this.proprietario = proprietario;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                this.tempo++;
                System.out.println(this.proprietario + " - " + this.tempo + " segundos");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getTempo() {
        return this.tempo;
    }

    public void setTempo(int timestamp) {
        this.tempo = timestamp;
    }
}
