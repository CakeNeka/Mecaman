package mecaman.producerconsumer;

public class WordConsumer extends Thread{
    WordManager wordManager;

    public WordConsumer(WordManager wordManager, String name) {
        super(name);
        this.wordManager = wordManager;
    }

    @Override
    public void run() {
        while (wordManager.isActive())
            wordManager.typeWord(this);
    }
}
