import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WordGenerator {
    private static WordGenerator wordGenerator;
    private final String FILE_NAME = "Files/WordList.csv";
    private final Path FILE_PATH = Path.of(FILE_NAME).toAbsolutePath();
    private final List<String> wordList;

    private WordGenerator() throws IOException {
        wordList = Files.readAllLines(FILE_PATH);
        Runtime.getRuntime().addShutdownHook(new ThreadOff());
    }

    public static WordGenerator getInstance() throws IOException {
        if (wordGenerator == null) {
            wordGenerator = new WordGenerator();
        }
        return wordGenerator;
    }

    public String getRandomWord() {
        int random = ThreadLocalRandom.current().nextInt(0, wordList.size());
        return wordList.get(random);
    }

    private void close() {
        wordGenerator = null;
    }

    private static class ThreadOff extends Thread {
        @Override
        public void run() {
            if (wordGenerator != null) {
                wordGenerator.close();
            }
        }
    }


}
