package mecaman.producerconsumer;

public class WordConsumer extends Thread{
    private WordManager wordManager;

    public WordConsumer(WordManager wordManager, String name) {
        super(name);
        this.wordManager = wordManager;
    }

    @Override
    public void run() {
        // Antes de finalizar, el consumidor procesa todas las palabras
        // que quedan en la cola
        while (wordManager.isActive() || wordManager.getOccupancyPercentage() > 0)
            wordManager.typeWord(this);
    }
}
