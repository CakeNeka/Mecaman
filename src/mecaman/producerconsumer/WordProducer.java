package mecaman.producerconsumer;


public class WordProducer extends Thread{

    WordManager wordManager;

    public WordProducer(WordManager wordManager, String name) {
        super(name);
        this.wordManager = wordManager;
    }

    @Override
    public void run() {
        while (wordManager.isActive())
            wordManager.addWord(this);
    }
}
