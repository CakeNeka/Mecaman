package mecaman.wordgeneration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomWordGenerator {
    private static RandomWordGenerator instance;
    private final String FILE_NAME = "resources/WordList.csv";
    private final Path FILE_PATH = Path.of(FILE_NAME).toAbsolutePath();
    private final List<String> wordList;

    private RandomWordGenerator() throws IOException {
        wordList = Files.readAllLines(FILE_PATH);
    }

    public static RandomWordGenerator getInstance() throws IOException {
        if (instance == null) {
            instance = new RandomWordGenerator();
        }
        return instance;
    }

    public String getRandomWord() {
        int random = ThreadLocalRandom.current().nextInt(0, wordList.size());
        return wordList.get(random);
    }
}
