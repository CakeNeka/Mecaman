package mecaman.wordgeneration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomWordGenerator {
    private static RandomWordGenerator instance;
    private final String FILE_NAME = "resources/WordList.csv";
    private final Path FILE_PATH = Path.of(FILE_NAME).toAbsolutePath();
    private List<String> wordList;

    /**
     * Constructor que lee todas las palabras del fichero FILE_NAME
     */
    private RandomWordGenerator() {
        try {
            //Lee todas las lineas
            wordList = Files.readAllLines(FILE_PATH);
        } catch (IOException e) {
            System.err.println("No se ha encontrado el archivo, utilizando PALABRAS DE EMERGENCIA");
            // Si no se encuentra el archivo, se utilizan estas palabras
            wordList = Arrays.asList("Mar", "Amor", "Conciencia", "MECAMAN", "TYPEMAN");
        }
    }

    /**
     * Metodo de patron SingleTone
     * @return Devuelve un objeto RandomWordGenerator
     */
    public static RandomWordGenerator getInstance() {
        //Si la instancia es null, declara un nuevo objeto
        if (instance == null) {
            instance = new RandomWordGenerator();
        }
        return instance;
    }

    /**
     * Metodo para obtener una palabra aleatoria del fichero
     * @return Devuelve un String con la palabra obtenida
     */

    public String getRandomWord() {
        int random = ThreadLocalRandom.current().nextInt(0, wordList.size());
        return wordList.get(random);
    }
}